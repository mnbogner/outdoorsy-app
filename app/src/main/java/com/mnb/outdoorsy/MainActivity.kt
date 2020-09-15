package com.mnb.outdoorsy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = resources.getColor(R.color.darkerGreen, theme)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
    }

}