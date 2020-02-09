package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iqbalproject.pj_pos.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSales.setOnClickListener {
            startActivity<SalesActivity>()
        }
    }
}
