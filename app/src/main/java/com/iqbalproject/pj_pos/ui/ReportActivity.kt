package com.iqbalproject.pj_pos.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.fonts.FontFamily
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.BuildConfig
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.PdfDocumentAdapter
import com.iqbalproject.pj_pos.model.GetReportResponse
import com.iqbalproject.pj_pos.ui.viewModel.ReportViewModel
import com.iqbalproject.pj_pos.utils.Common
import com.iqbalproject.pj_pos.utils.Constants
import com.iqbalproject.pj_pos.utils.DatePickerFragment
import com.iqbalproject.pj_pos.utils.Tools
import com.itextpdf.text.*
import com.itextpdf.text.Font
import com.itextpdf.text.pdf.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_report.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ReportActivity : AppCompatActivity() {

    //private val file_name: String = "report_pj.pdf"
    private var file_name: String? = null
    private lateinit var viewModel: ReportViewModel
    private lateinit var dateFragment: DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.menu_6)

        viewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)

        etStartDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showDatePicker(v)
            }
        }

        etEndDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showDatePicker(v)
            }
        }

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    btnGenerate.setOnClickListener {
                        tvReportSaleDate.text = "${etStartDate.text} s/d ${etEndDate.text}"
                        tvReportPurcDate.text = "${etStartDate.text} s/d ${etEndDate.text}"
                        progressReport.visibility = View.VISIBLE
                        viewModel.getReport(etStartDate.text.toString(), etEndDate.text.toString())
                            .observe(this@ReportActivity, Observer { report ->
                                progressReport.visibility = View.GONE
                                when (report.status) {
                                    true -> {
                                        if (report.rownum_purchase!! > 0 && report.rownum_sales!! > 0) {
                                            cvPurchaseReport.visibility = View.VISIBLE
                                            cvSalesReport.visibility = View.VISIBLE
                                        } else if (report.rownum_purchase > 0) {
                                            cvPurchaseReport.visibility = View.VISIBLE
                                            cvSalesReport.visibility = View.GONE
                                        } else if (report.rownum_sales!! > 0) {
                                            cvPurchaseReport.visibility = View.GONE
                                            cvSalesReport.visibility = View.VISIBLE
                                        } else {
                                            Tools.alertInfo(
                                                this@ReportActivity,
                                                "Perhatian",
                                                "Laporan tidak tersedia."
                                            )
                                            cvPurchaseReport.visibility = View.GONE
                                            cvSalesReport.visibility = View.GONE
                                        }

                                        cvSalesReport.setOnClickListener {
                                            file_name =
                                                getString(R.string.report_sales_name) + etStartDate.text + "_" + etEndDate.text + ".xls"
                                            writeExcelReport(
                                                Common.getAppPath(this@ReportActivity) + file_name,
                                                report,
                                                Constants.SALES
                                            )
                                            /*generateReport(
                                                Common.getAppPath(this@ReportActivity) + file_name,
                                                report,
                                                Constants.SALES
                                            )*/
                                        }

                                        cvPurchaseReport.setOnClickListener {
                                            file_name =
                                                getString(R.string.report_purchase_name) + etStartDate.text + "_" + etEndDate.text + ".xls"
                                            writeExcelReport(
                                                Common.getAppPath(this@ReportActivity) + file_name,
                                                report,
                                                Constants.PURCHASE
                                            )
                                            /*generateReport(
                                                Common.getAppPath(this@ReportActivity) + file_name,
                                                report,
                                                Constants.PURCHASE
                                            )*/
                                        }
                                    }
                                    else -> {
                                        Tools.alertFailed(
                                            this@ReportActivity,
                                            "Gagal",
                                            report.message.toString()
                                        )
                                    }
                                }
                            })
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                }
            })
            .check()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /*private fun generateReport(
        path: String,
        report: GetReportResponse,
        type: String
    ) {
        val document = Document()
        document.pageSize = PageSize.A4
        if (File(path).exists())
            File(path).delete()

        try {
            val docWriter: PdfWriter = PdfWriter.getInstance(document, FileOutputStream(path))
            document.open()

            val cb: PdfContentByte = docWriter.directContent

            val fontName = BaseFont.createFont("res/font/acme.ttf", "UTF-8", BaseFont.EMBEDDED)
            val titleStyle = Font(fontName, 17.0f, Font.BOLD, BaseColor.BLACK)
            val markStyle1 = Font(fontName, 20.0f, Font.BOLD, BaseColor.BLUE)
            val markStyle2 = Font(fontName, 17.0f, Font.NORMAL, BaseColor.BLACK)

            //Header
            addNewItem(document, "TB. Putra Jaya", Element.ALIGN_LEFT, markStyle1)
            addNewItem(
                document,
                "Jl. Al-Husna Jatikramat, Jatiasih, Kota Bekasi",
                Element.ALIGN_LEFT,
                markStyle2
            )

            if (type == Constants.SALES) {
                addLineSpace(document, 30.0f)
                addNewItem(document, "Laporan Penjualan", Element.ALIGN_CENTER, titleStyle)
                //list all the products sold to the customer
                val columnWidths = floatArrayOf(0.5f, 1f, 1f, 2f, 1f, 1f, 1f)
                //create PDF table with the given widths
                val table = PdfPTable(columnWidths)
                // set table width a percentage of the page width
                table.totalWidth = PageSize.A4.width

                var cell = PdfPCell(Phrase("No."))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Tanggal"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Sale ID"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Customer"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Total Penjualan"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Terbayar"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Status"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                table.headerRows = 1

                for (i in report.result_sales!!.indices) {
                    table.addCell("${i + 1}") // No.
                    table.addCell("${report.result_sales[i].sale_date}") //Tanggal
                    table.addCell("${report.result_sales[i].sale_id}") //Sale ID
                    table.addCell("(${report.result_sales[i].cust_id}) ${report.result_sales[i].cust_name}") // Customer
                    table.addCell("${report.result_sales[i].to_be_paid}") //Total Penjualan
                    table.addCell("${report.result_sales[i].paid}") //Terbayar
                    table.addCell("${report.result_sales[i].status}") //Status
                }

                //absolute location to print the PDF table
                table.writeSelectedRows(0, -1, document.leftMargin(), 650f, cb)

            } else if (type == Constants.PURCHASE) {
                addLineSpace(document, 30.0f)
                addNewItem(document, "Laporan Pembelian", Element.ALIGN_CENTER, titleStyle)
                //list all the products sold to the customer
                val columnWidths = floatArrayOf(0.5f, 1f, 1f, 2f, 1f)
                //create PDF table with the given widths
                val table = PdfPTable(columnWidths)
                // set table width a percentage of the page width
                table.totalWidth = PageSize.A4.width

                var cell = PdfPCell(Phrase("No."))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Tanggal"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Purchase ID"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Supplier"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                cell = PdfPCell(Phrase("Total Pembayaran"))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(cell)

                table.headerRows = 1

                for (i in report.result_purchase!!.indices) {
                    table.addCell("${i + 1}") // No.
                    table.addCell("${report.result_purchase[i].purchase_date}") //Tanggal
                    table.addCell("${report.result_purchase[i].purchase_id}") //Purchase ID
                    table.addCell("(${report.result_purchase[i].supplier_id}) ${report.result_purchase[i].supplier_name}") // Supplier ID
                    table.addCell("${report.result_purchase[i].purchase_price_total}") //Total Pembayaran
                }

                //absolute location to print the PDF table
                table.writeSelectedRows(0, -1, document.leftMargin(), 650f, cb)
            }

            document.close()

            Toast.makeText(
                this, "Sukses\n" +
                        "Laporan Tersimpan di $path", Toast.LENGTH_LONG
            ).show()

            printPDF()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ReportActivity", e.message.toString())
        }
    }

    @Throws(DocumentException::class)
    private fun addNewItem(document: Document, text: String, align: Int, titleStyle: Font) {
        val chunk = Chunk(text, titleStyle)
        val p = Paragraph(chunk)
        p.alignment = align
        document.add(p)
    }

    @Throws(DocumentException::class)
    private fun addLineSpace(document: Document, spacing: Float) {
        val chunk = Chunk("")
        val p = Paragraph(chunk)
        p.spacingAfter = spacing
        document.add(p)
    }

    private fun printPDF() {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printAdapter = PdfDocumentAdapter(
                this@ReportActivity,
                Common.getAppPath(this@ReportActivity) + file_name,
                "$file_name"
            )
            printManager.print("Document", printAdapter, PrintAttributes.Builder().build())
        } catch (e: Exception) {
            Log.e("ReportActivity", e.message.toString())
        }
    }*/

    fun showDatePicker(v: View?) {
        UIUtil.hideKeyboard(this@ReportActivity)
        dateFragment = DatePickerFragment(v as EditText)
        dateFragment.show(supportFragmentManager, "datePicker")
    }

    private fun writeExcelReport(
        path: String,
        report: GetReportResponse,
        type: String
    ) {
        if (File(path).exists())
            File(path).delete()

        var t_sales_to_be_paid = 0
        var t_sales_paid = 0
        var t_purchase_price = 0
        val workBook: Workbook = HSSFWorkbook()
        var cell: Cell
        val sheet: Sheet //create sheet
        var row: Row
        val cellStyle: CellStyle = workBook.createCellStyle()
        val cellFont: org.apache.poi.ss.usermodel.Font = workBook.createFont()
        cellStyle.fillForegroundColor = HSSFColor.GREY_25_PERCENT.index
        cellStyle.fillPattern = HSSFCellStyle.SOLID_FOREGROUND

        cellFont.bold = true
        cellStyle.setFont(cellFont)

        if (type == Constants.SALES) {
            sheet = workBook.createSheet("Sales Report")

            //header
            row = sheet.createRow(0)
            cell = row.createCell(0)
            cell.setCellValue("No.")
            cell.cellStyle = cellStyle
            cell = row.createCell(1)
            cell.setCellValue("Tanggal")
            cell.cellStyle = cellStyle
            cell = row.createCell(2)
            cell.setCellValue("Sale ID")
            cell.cellStyle = cellStyle
            cell = row.createCell(3)
            cell.setCellValue("Customer ID")
            cell.cellStyle = cellStyle
            cell = row.createCell(4)
            cell.setCellValue("Customer Name")
            cell.cellStyle = cellStyle
            cell = row.createCell(5)
            cell.setCellValue("Total Penjualan")
            cell.cellStyle = cellStyle
            cell = row.createCell(6)
            cell.setCellValue("Terbayar")
            cell.cellStyle = cellStyle
            cell = row.createCell(7)
            cell.setCellValue("Status")
            cell.cellStyle = cellStyle

            //lines
            for (i in report.result_sales!!.indices) {
                var cellIndex = 0
                row = sheet.createRow(i + 1)

                cell = row.createCell(cellIndex)
                cell.setCellValue("${i + 1}") //No
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].sale_date}") //Tanggal
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].sale_id}") //Sale ID
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].cust_id}") //Customer ID
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].cust_name}") //Customer Name
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].to_be_paid}") //Total Penjualan
                t_sales_to_be_paid += report.result_sales[i].to_be_paid?.toInt() ?: 0
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].paid}") //Terbayar
                t_sales_paid += report.result_sales[i].paid?.toInt() ?: 0
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_sales[i].status}") //Status
            }

            //footer
            row = sheet.createRow(row.rowNum+1)
            sheet.addMergedRegion(CellRangeAddress(row.rowNum,row.rowNum,0,4))
            cell = row.createCell(0)
            cell.setCellValue("TOTAL")
            cell.cellStyle = cellStyle
            cell = row.createCell(5)
            cell.setCellValue("$t_sales_to_be_paid")
            cell.cellStyle = cellStyle
            sheet.addMergedRegion(CellRangeAddress(row.rowNum,row.rowNum,6,7))
            cell = row.createCell(6)
            cell.setCellValue("$t_sales_paid")
            cell.cellStyle = cellStyle

            sheet.setColumnWidth(0, (10 * 200))
            sheet.setColumnWidth(1, (10 * 200))
        } else if (type == Constants.PURCHASE) {
            sheet = workBook.createSheet("Purchase Report")

            //header
            row = sheet.createRow(0)
            cell = row.createCell(0)
            cell.setCellValue("No.")
            cell.cellStyle = cellStyle
            cell = row.createCell(1)
            cell.setCellValue("Tanggal")
            cell.cellStyle = cellStyle
            cell = row.createCell(2)
            cell.setCellValue("Purchase ID")
            cell.cellStyle = cellStyle
            cell = row.createCell(3)
            cell.setCellValue("Supplier ID")
            cell.cellStyle = cellStyle
            cell = row.createCell(4)
            cell.setCellValue("Supplier Name")
            cell.cellStyle = cellStyle
            cell = row.createCell(5)
            cell.setCellValue("Total Pembayaran")
            cell.cellStyle = cellStyle

            //lines
            for (i in report.result_purchase!!.indices) {
                var cellIndex = 0
                row = sheet.createRow(i + 1)

                cell = row.createCell(cellIndex)
                cell.setCellValue("${i + 1}") //No
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_purchase[i].purchase_date}") //Tanggal
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_purchase[i].purchase_id}") //Purchase ID
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_purchase[i].supplier_id}") //Supplier ID
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_purchase[i].supplier_name}") //Supplier Name
                cell = row.createCell(++cellIndex)
                cell.setCellValue("${report.result_purchase[i].purchase_price_total}") //Total Pembayaran
                t_purchase_price += report.result_purchase[i].purchase_price_total?.toInt() ?: 0
            }

            //footer
            row = sheet.createRow(row.rowNum+1)
            sheet.addMergedRegion(CellRangeAddress(row.rowNum,row.rowNum,0,4))
            cell = row.createCell(0)
            cell.setCellValue("TOTAL")
            cell.cellStyle = cellStyle
            cell = row.createCell(5)
            cell.setCellValue("$t_purchase_price")
            cell.cellStyle = cellStyle

            sheet.setColumnWidth(0, (10 * 200))
            sheet.setColumnWidth(1, (10 * 200))
        }

        //val file = File(Common.getAppPath(this@ReportActivity) + "excel_report.xls")
        var outputStream: FileOutputStream? = null

        try {
            outputStream = FileOutputStream(path)
            workBook.write(outputStream)
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()

            Toast.makeText(this, "Not OK", Toast.LENGTH_LONG).show()

            try {
                outputStream?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }

        val intent = Intent(Intent.ACTION_VIEW)
        val uri: Uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".fileprovider", File(path))
        intent.setDataAndType(uri, "application/vnd.ms-excel")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
