package com.iqbalproject.pj_pos.adapter

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.util.Log
import java.io.*
import java.lang.Exception

class PdfDocumentAdapter(context: Context, path: String, defaultFileName: String) : PrintDocumentAdapter() {

    internal var context: Context? = null
    internal var path = ""
    private var defaultFileName = ""

    init {
        this.context = context
        this.path = path
        this.defaultFileName = defaultFileName
    }

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        if (cancellationSignal!!.isCanceled) {
            callback?.onLayoutCancelled()
        } else {
            val builder = PrintDocumentInfo.Builder(defaultFileName)
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            callback?.onLayoutFinished(builder.build(), newAttributes != oldAttributes)
        }
    }

    override fun onWrite(
        pagesRanges: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        var `in`: InputStream? = null
        var out: OutputStream? = null

        try {
            var file = File(path)
            `in` = FileInputStream(file)
            out = FileOutputStream(destination?.fileDescriptor)

            if (!cancellationSignal!!.isCanceled) {
                `in`.copyTo(out)
                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            } else {
                callback?.onWriteCancelled()
            }
        } catch (e: Exception) {
            callback?.onWriteFailed(e.message)
            Log.e("IqbalsProject", e.message)
        } finally {
            try {
                `in`?.close()
                out?.close()
            } catch (e: IOException) {
                Log.e("IqbalsProject", e.message)
            }
        }
    }
}