package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.ui.viewModel.LoginViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

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
            progressLogin.visibility = View.VISIBLE
            viewModel.loadData(userId.toString(), password.toString())
            viewModel.getData().observe(this, Observer { login ->
                viewModel.getStatus().observe(this, Observer { status ->
                    when (status) {
                        true -> {
                            progressLogin.visibility = View.GONE
                            startActivity<MainActivity>()
                        }
                        else -> {
                            progressLogin.visibility = View.GONE
                            Tools.alertFailed(this, "Failed", login.message.toString())
                        }
                    }
                })
            })
        }
    }
}
