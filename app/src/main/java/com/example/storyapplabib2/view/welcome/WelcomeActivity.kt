package com.example.storyapplabib2.view.welcome

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.storyapplabib2.data.model.UserPreference
import com.example.storyapplabib2.databinding.ActivityWelcomeBinding
import com.example.storyapplabib2.utils.Constant
import com.example.storyapplabib2.utils.dataStore
import com.example.storyapplabib2.view.login.LoginActivity
import com.example.storyapplabib2.view.main.MainActivity
import kotlinx.coroutines.*

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    val welcomeViewModel by viewModels<WelcomeViewModel> {
        com.example.storyapplabib2.view.ViewModelFactory(
            UserPreference.getInstance(dataStore)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



        var isLogin = false

        welcomeViewModel.getUser().observe(this) { model ->
            isLogin = if(model.isLogin) {
                UserPreference.setToken(model.tokenAuth)
                true
            } else {
                false
            }
        }

        activityScope.launch {
            delay(Constant.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                if(isLogin) {
                    MainActivity.start(this@WelcomeActivity)
                } else {
                    LoginActivity.start(this@WelcomeActivity)
                }
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.coroutineContext.cancelChildren()
    }
}