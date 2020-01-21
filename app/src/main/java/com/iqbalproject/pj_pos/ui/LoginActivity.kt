package com.iqbalproject.pj_pos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iqbalproject.pj_pos.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
