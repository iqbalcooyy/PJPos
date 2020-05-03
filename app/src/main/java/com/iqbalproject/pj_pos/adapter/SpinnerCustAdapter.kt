package com.iqbalproject.pj_pos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.CustomerResult

class SpinnerCustAdapter(val context: Context, private val customers: List<CustomerResult>) : BaseAdapter() {

    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder

        if (convertView == null) {
            view = mInflater.inflate(R.layout.view_dropdown_menu, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        val params = view.layoutParams
        params.height = 60
        view.layoutParams = params

        vh.custName.text = customers[position].cust_name
        return view
    }

    override fun getItem(position: Int): Any? = null

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = customers.size

    private class ItemRowHolder(row: View?) {
        val custName: TextView = row?.findViewById(R.id.tvDropdown) as TextView
    }
}