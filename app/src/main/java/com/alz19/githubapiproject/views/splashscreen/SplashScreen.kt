package com.alz19.githubapiproject.views.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alz19.githubapiproject.R
import com.alz19.githubapiproject.views.activity.MainActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation
    private lateinit var imageView: ImageView
    private lateinit var titleTxt: TextView
    private lateinit var descriptionTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        initializeAnimations()
        initializeViews()

        Handler().postDelayed({
            navigateToMainActivity()
            finish()
        }, SPLASH_SCREEN_DELAY)
    }

    private fun initializeAnimations() {
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
    }

    private fun initializeViews() {
        imageView = findViewById(R.id.hr_image)
        titleTxt = findViewById(R.id.title_text)
        descriptionTxt = findViewById(R.id.description_text)

        with(imageView) {
            animation = topAnimation
        }

        with(titleTxt) {
            animation = bottomAnimation
        }

        with(descriptionTxt) {
            animation = bottomAnimation
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 5000L
    }
}
