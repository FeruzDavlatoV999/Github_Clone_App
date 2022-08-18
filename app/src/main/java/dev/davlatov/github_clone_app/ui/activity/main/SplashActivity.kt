package dev.davlatov.github_clone_app.ui.activity.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.data.local.prefs.SharedPrefs
import dev.davlatov.github_clone_app.databinding.ActivitySplashBinding
import dev.davlatov.github_clone_app.ui.activity.login.LoginActivity
import javax.inject.Inject


@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var timer: CountDownTimer? = null

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        sharedPrefs = SharedPrefs.getInstance(this)
        setSplashTimer()
    }

    private fun setSplashTimer() {
        timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if (sharedPrefs.accessToken.isNullOrBlank()) callLoginActivity() else callMainActivity()
            }
        }
        timer?.start()
    }

    private fun callLoginActivity() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun callMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}