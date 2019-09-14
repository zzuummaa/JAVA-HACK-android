package com.jhapp.mc.presentation.splashcreen_activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jhapp.mc.presentation.main_activity.MainActivity

class SplashScreenActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(baseContext, MainActivity::class.java))
        overridePendingTransition(0,0 )
    }
}