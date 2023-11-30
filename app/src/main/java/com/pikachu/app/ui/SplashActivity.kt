package com.pikachu.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pikachu.app.application.ActivityLauncher
import com.pikachu.app.databinding.ActivitySplashBinding
import com.pikachu.app.utils.isUserLoggedIn


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (isUserLoggedIn() /*&& Utils.allPermissionsGranted(this@SplashActivity)*/) {
            //ActivityLauncher.launchHomeActivity(this)
            finish()
        } else {
            ActivityLauncher.launchLoginActivity(this)
            finish()
        }
    }
}