package com.iqbalproject.pj_pos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.iqbalproject.pj_pos.R
import org.jetbrains.anko.startActivity

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            startActivity<LoginActivity>()
        }, 2000)
    }
}
