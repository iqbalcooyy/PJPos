package com.iqbalproject.pj_pos.utils

import android.content.Context
import com.iqbalproject.pj_pos.R
import java.io.File

object Common {
    fun getAppPath(context: Context): String {
        val dir = File(android.os.Environment.getExternalStorageDirectory().toString()
        +File.separator
        +context.resources.getString(R.string.app_name)
        +File.separator)
        if (!dir.exists())
            dir.mkdir()
        return dir.path+File.separator
    }
}