package com.iqbalproject.pj_pos.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.iqbalproject.pj_pos.R
import com.squareup.picasso.Picasso

class MainMenuAdapter(private val context: Context, private val itemMenu: List<String>, private val iconMenu: List<Int>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mView = convertView
        val menuIcon: ImageView
        val tvIconTitle: TextView

        if (mView == null){
            val inflater = (context as Activity).layoutInflater
            mView = inflater.inflate(R.layout.item_main_menu, parent, false)
        }

        menuIcon = mView!!.findViewById(R.id.mainIcon)
        tvIconTitle = mView.findViewById(R.id.tvTitleIconMain)

        tvIconTitle.text = itemMenu[position]
        iconMenu[position].let {
            Picasso.get().load(it).into(menuIcon)
        }

        return mView
    }

    override fun getItem(position: Int): Any = itemMenu[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = itemMenu.size
}