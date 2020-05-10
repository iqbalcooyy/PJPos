package com.iqbalproject.pj_pos.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.iqbalproject.pj_pos.R
import java.util.*

class DatePickerFragment(private val text: EditText) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dateDialog = DatePickerDialog(
            activity!!,
            R.style.DialogTheme,
            this,
            year,
            month,
            day
        )

        dateDialog.datePicker.maxDate = System.currentTimeMillis()

        return dateDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        text.setText("$year-${month + 1}-$dayOfMonth")
    }
}