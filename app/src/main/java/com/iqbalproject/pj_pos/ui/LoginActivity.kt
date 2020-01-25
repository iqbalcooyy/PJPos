package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.ui.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var userId: Editable
    private lateinit var password: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        userId = etUsername.editableText
        password = etPassword.editableText

        btnLogin.setOnClickListener {
            viewModel.loadData(userId.toString(), password.toString())
            viewModel.getStatus().observe(this, Observer {
                when (it) {
                    true -> {
                        startActivity<MainActivity>()
                    }
                    else -> {
                        viewModel.getData().observe(this, Observer {
                            toast(it.message.toString()).show()
                        })
                    }
                }
            })
        }
    }
}
