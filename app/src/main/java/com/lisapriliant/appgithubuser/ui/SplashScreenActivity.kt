package com.lisapriliant.appgithubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lisapriliant.appgithubuser.R
import com.lisapriliant.appgithubuser.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTimeOut)
    }
}