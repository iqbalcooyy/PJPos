package com.iqbalproject.pj_pos.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.PdfDocumentAdapter
import com.iqbalproject.pj_pos.adapter.SalesConfirmationAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.AccReceivViewModel
import com.iqbalproject.pj_pos.ui.viewModel.SalesConfirmViewModel
import com.iqbalproject.pj_pos.utils.Common
import com.iqbalproject.pj_pos.utils.Constants
import com.iqbalproject.pj_pos.utils.Tools
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sales_confirmation.*
import kotlinx.android.synthetic.main.item_adjust_pay.view.*
import java.io.File
import java.io.FileOutputStream

class SalesConfirmationActivity : AppCompatActivity() {

    private lateinit var viewModel: SalesConfirmViewModel
    private lateinit var viewModelAr: AccReceivViewModel
    private lateinit var dialogForm: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var etBayar: EditText
    private val sales: MutableList<StockDetail> = mutableListOf()
    private var itemId: MutableList<String> = mutableListOf()
    private var saleQty: MutableList<Int> = mutableListOf()
    private lateinit var trxId: String
    private lateinit var statusPay: String
    private var total_payment: Int = 0
    private var toBePaid: Int = 0
    private var paid: Int = 0
    private lateinit var discount: String

    //private val file_name: String = "test_pdf.pdf"
    private var file_name: String? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_confirmation)

        viewModel = ViewModelProviders.of(this).get(SalesConfirmViewModel::class.java)
        viewModelAr = ViewModelProviders.of(this).get(AccReceivViewModel::class.java)
        tvTransDateConfirm.text = Tools.getCurrentDate()

        initData()

        btnPay.setOnClickListener {
            dialogForm = AlertDialog.Builder(this)
            inflater = layoutInflater
            dialogView = inflater.inflate(R.layout.item_adjust_pay, null)
            etBayar = dialogView.etBayar

            dialogForm.setView(dialogView)
            dialogForm.setTitle("Total: $toBePaid")
            dialogForm.setCancelable(false)
            dialogForm.setPositiveButton("Process", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if (!etBayar.text.isNullOrEmpty()) {
                        if (etBayar.text.toString().toInt() > toBePaid) {
                            Tools.alertFailed(
                                this@SalesConfirmationActivity,
                                "Perhatian",
                                "Jumlah pembayaran tidak boleh melebihi total harga."
                            )
                        } else {
                            p0?.dismiss()
                            progressConfirmSale.visibility = View.VISIBLE
                            paid = etBayar.text.toString().toInt()
                            viewModel.loadData(
                                sales.last().id_dummy.toString(),
                                itemId,
                                0,
                                toBePaid,
                                saleQty,
                                paid
                            )

                            viewModel.getData()
                                .observe(this@SalesConfirmationActivity, Observer { saleRes ->
                                    viewModel.getStatus()
                                        .observe(
                                            this@SalesConfirmationActivity,
                                            Observer { status ->
                                                when (status) {
                                                    true -> {
                                                        progressConfirmSale.visibility = View.GONE
                                                        Tools.alertSuccess(
                                                            this@SalesConfirmationActivity,
                                                            "Success",
                                                            saleRes.message.toString(),
                                                            false
                                                        )
                                                        trxId = saleRes.id.toString()
                                                        statusPay = saleRes.status_sale.toString()
                                                        tvCodeTrxSale.text = trxId
                                                        tvTrxSaleStat.text = statusPay
                                                        btnPay.visibility = View.GONE
                                                        fabPrint.visibility = View.VISIBLE
                                                        file_name = getString(R.string.nota_name) + trxId + ".pdf"

                                                        if (saleRes.status_sale == Constants.STAT_HUTANG) {
                                                            viewModelAr.pushData(
                                                                trxId,
                                                                sales.first().id_dummy.toString(),
                                                                total_payment - paid
                                                            )
                                                                .observe(
                                                                    this@SalesConfirmationActivity,
                                                                    Observer {
                                                                        Tools.toastSuccess(
                                                                            this@SalesConfirmationActivity,
                                                                            it.message.toString()
                                                                        )
                                                                    })
                                                        }
                                                    }
                                                    else -> {
                                                        progressConfirmSale.visibility = View.GONE
                                                        Tools.alertFailed(
                                                            this@SalesConfirmationActivity,
                                                            "Failed",
                                                            saleRes.message.toString()
                                                        )
                                                    }
                                                }
                                            })
                                })
                        }

                    } else {
                        Tools.toastWarning(
                            this@SalesConfirmationActivity,
                            "Mohon masukan jumlah pembayaran!"
                        )
                    }
                }
            })
            dialogForm.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                }
            })
            dialogForm.show()
        }

        printNota()
    }

    private fun initData() {
        sales.addAll(intent.getParcelableArrayListExtra("saleConfirm"))
        intent.getStringExtra("discount").let {
            discount = if (it.isNullOrEmpty())
                "0"
            else
                it
        }
        total_payment = 0

        for (i in sales.indices) {
            total_payment += sales[i].amount_dummy

            itemId.add(i, sales[i].item_id)
            saleQty.add(i, sales[i].qty_dummy)
        }

        tvCustNameConf.text = sales.last().name_dummy
        tvCustAddressConf.text = sales.last().address_dummy
        tvCustTelpConf.text = sales.last().telp_dummy

        toBePaid = total_payment - discount.toInt()

        tvTotalPayConfirm.text = Tools.convertRupiahsFormat(toBePaid.toDouble())
        tvSubTot.text = Tools.convertRupiahsFormat(total_payment.toDouble())
        tvDiscount.text = Tools.convertRupiahsFormat(discount.toDouble())
        tvTotPayConf.text = Tools.convertRupiahsFormat(toBePaid.toDouble())

        rvItemsSaleConf.adapter = SalesConfirmationAdapter(sales)
    }

    private fun printNota() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    fabPrint.setOnClickListener {
                        createPDFFile(Common.getAppPath(this@SalesConfirmationActivity) + file_name)
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

    private fun createPDFFile(
        path: String
    ) {
        if (File(path).exists())
            File(path).delete()

        try {
            val document = Document()
            //Save
            PdfWriter.getInstance(document, FileOutputStream(path))
            //Open to Write
            document.open()

            //Setting
            document.pageSize = PageSize.A4
            document.addCreationDate()
            document.addAuthor("Iqbal's Project")
            document.addCreator("Muhammad Iqbal")

            //Font Setting
            val colorAccent = BaseColor(0, 153, 204, 255)
            val headingFontSize = 12.0f
            val valueFontSize = 14.0f

            //Custom Font
            val fontName =
                BaseFont.createFont("res/font/acme.ttf", "UTF-8", BaseFont.EMBEDDED)

            val titleStyle = Font(fontName, 17.0f, Font.NORMAL, BaseColor.BLACK)
            val headingStyle = Font(fontName, headingFontSize, Font.NORMAL, colorAccent)
            val valueStyle = Font(fontName, valueFontSize, Font.NORMAL, BaseColor.BLACK)
            val markStyle1 = Font(fontName, 20.0f, Font.BOLD, BaseColor.BLUE)
            val markStyle2 = Font(fontName, 17.0f, Font.NORMAL, BaseColor.BLACK)

            //------ Header -------------
            addNewItem(document, "TB. Putra Jaya", Element.ALIGN_LEFT, markStyle1)
            addNewItem(
                document,
                "Jl. Al-Husna Jatikramat, Jatiasih, Kota Bekasi",
                Element.ALIGN_LEFT,
                markStyle2
            )

            addLineSpace(document, 50.0f)

            addNewItem(document, "Order Details", Element.ALIGN_CENTER, titleStyle) // Title
            addNewItem(document, "Order No:", Element.ALIGN_LEFT, headingStyle)
            addNewItem(document, trxId, Element.ALIGN_LEFT, valueStyle)

            addLineSeparator(document)

            addNewItem(document, "Order Date:", Element.ALIGN_LEFT, headingStyle)
            addNewItem(document, Tools.getCurrentDate(), Element.ALIGN_LEFT, valueStyle)

            addLineSeparator(document)

            addNewItem(document, "Account Name:", Element.ALIGN_LEFT, headingStyle)
            addNewItem(document, sales.last().name_dummy.toString(), Element.ALIGN_LEFT, valueStyle)

            addLineSeparator(document)

            addNewItem(document, "Status:", Element.ALIGN_LEFT, headingStyle)
            addNewItem(document, statusPay, Element.ALIGN_LEFT, valueStyle)

            addLineSeparator(document)
            //------- End Header --------

            //---- Product Details ------
            addLineSpace(document, 10.0f)
            addNewItem(document, "Product Details", Element.ALIGN_CENTER, titleStyle) //Title
            for (i in sales.indices) {
                addLineSeparator(document)

                //Item-item
                addNewItemWithLeftAndRight(
                    document,
                    sales[i].item_name,
                    "(0.0%)",
                    valueStyle,
                    valueStyle
                )
                addNewItemWithLeftAndRight(
                    document,
                    "${sales[i].qty_dummy} x ${Tools.convertRupiahsFormat(sales[i].selling_price.toDouble())}",
                    "${Tools.convertRupiahsFormat(sales[i].amount_dummy.toDouble())}",
                    valueStyle,
                    valueStyle
                )
            }

            addLineSeparator(document)

            //Total
            addLineSpace(document, 5.0f)
            addNewItemWithLeftAndRight(
                document,
                "Subtotal",
                "${Tools.convertRupiahsFormat(total_payment.toDouble())}",
                titleStyle,
                valueStyle
            )
            addNewItemWithLeftAndRight(
                document,
                "Discount",
                "${Tools.convertRupiahsFormat(discount.toDouble())}",
                titleStyle,
                valueStyle
            )
            addNewItemWithLeftAndRight(
                document,
                "Total",
                "${Tools.convertRupiahsFormat(toBePaid.toDouble())}",
                titleStyle,
                valueStyle
            )
            addLineSeparator(document)

            //Footer
            addLineSpace(document, 50.0f)
            addNewItemWithLeftAndRight(
                document,
                "Penerima / Pembeli,",
                "TB. Putra Jaya,",
                headingStyle,
                headingStyle
            )
            addLineSpace(document, 70.0f)
            addNewItemWithLeftAndRight(
                document,
                "(__________________)",
                "(_________________)",
                valueStyle,
                valueStyle
            )
            //===End Footer====

            //close
            document.close()

            Toast.makeText(this, "Sukses\n" +
                    "Nota Tersimpan di $path", Toast.LENGTH_LONG).show()

            printPDF()

        } catch (e: Exception) {
            Log.e("IqbalsProject", "" + e.message)
        }
    }

    private fun printPDF() {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printAdapter = PdfDocumentAdapter(
                this@SalesConfirmationActivity,
                Common.getAppPath(this@SalesConfirmationActivity) + file_name,
                "$file_name"
            )
            printManager.print("Document", printAdapter, PrintAttributes.Builder().build())
        } catch (e: Exception) {
            Log.e("IqbalsProject", "" + e.message)
        }
    }

    @Throws(DocumentException::class)
    private fun addNewItemWithLeftAndRight(
        document: Document,
        textLeft: String,
        textRight: String,
        leftStyle: Font,
        rightStyle: Font
    ) {
        val chunkTextLeft = Chunk(textLeft, leftStyle)
        val chunkTextRight = Chunk(textRight, rightStyle)
        val p = Paragraph(chunkTextLeft)
        p.add(Chunk(VerticalPositionMark()))
        p.add(chunkTextRight)
        document.add(p)
    }

    @Throws(DocumentException::class)
    private fun addLineSeparator(document: Document) {
        val lineSeparator = LineSeparator()
        lineSeparator.lineColor = BaseColor(0, 0, 0, 68)
        document.add(Chunk(lineSeparator))
        addLineSpace(document, 0f)
    }

    @Throws(DocumentException::class)
    private fun addLineSpace(document: Document, spacing: Float) {
        val chunk = Chunk("")
        val p = Paragraph(chunk)
        p.spacingAfter = spacing
        document.add(p)
    }

    @Throws(DocumentException::class)
    private fun addNewItem(document: Document, text: String, align: Int, titleStyle: Font) {
        val chunk = Chunk(text, titleStyle)
        val p = Paragraph(chunk)
        p.alignment = align
        document.add(p)
    }
}