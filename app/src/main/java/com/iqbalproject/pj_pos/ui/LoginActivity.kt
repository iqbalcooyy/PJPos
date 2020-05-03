package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.ui.viewModel.LoginViewModel
import com.iqbalproject.pj_pos.utils.SessionManager
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var session: SessionManager
    private lateinit var viewModel: LoginViewModel
    private lateinit var userId: Editable
    private lateinit var password: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //==== Login Session
        session = SessionManager(applicationContext)
        if (session.isLoggedIn()) {
            goToMainActivity()
        }
        //======

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        userId = etUsername.editableText
        password = etPassword.editableText

        etPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginProcess()
                return@setOnEditorActionListener true
            }

            false
        }

        btnLogin.setOnClickListener {
            loginProcess()
        }
    }

    private fun loginProcess() {
        progressLogin.visibility = View.VISIBLE
        viewModel.loadData(userId.toString(), password.toString()).observe(this, Observer {
            when (it.status) {
                "true" -> {
                    progressLogin.visibility = View.GONE
                    session.createLoginSession(
                        it.result?.first()?.username.toString(),
                        it.result?.first()?.fullname.toString(),
                        it.result?.first()?.position.toString()
                    )
                    goToMainActivity()
                }
                else -> {
                    progressLogin.visibility = View.GONE
                    Tools.alertFailed(this, "Failed", it.message.toString())
                }
            }
        })
    }

    private fun goToMainActivity() {
        startActivity(
            intentFor<MainActivity>()
                .clearTop()
                .newTask()
        )
        finish()
    }
}
