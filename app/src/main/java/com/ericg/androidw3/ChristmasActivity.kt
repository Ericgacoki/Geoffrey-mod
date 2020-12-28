package com.ericg.androidw3

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.ericg.androidw3.databinding.ActivityChristmasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChristmasActivity : AppCompatActivity() {

    private var _christmasBinding: ActivityChristmasBinding? = null
    private val christmasBinding get() = _christmasBinding
    private var darkModeEnabled: Boolean = false

    override fun onStart() {
        Theme(this@ChristmasActivity).readPrefs().observe(this@ChristmasActivity, {
            if (it) {
                enableDarkMode(true)
            }
            darkModeEnabled = it
        })
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        enableDarkMode(darkModeEnabled)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // change title
        supportActionBar?.title = "Hello Esther"

        // enableDarkMode(darkModeEnabled)
        _christmasBinding = DataBindingUtil.setContentView(this, R.layout.activity_christmas)

        // theme to follow system in android 10+
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            enableDarkMode(null)
        }

        val intentToNewYearActivity = Intent(this, NewYearActivity::class.java)
        christmasBinding?.button1?.setOnClickListener {
            startActivity(intentToNewYearActivity)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mode_night) {
            if (!darkModeEnabled) {
                enableDarkMode(true)
                Toast.makeText(this, "dark mode enabled", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ChristmasActivity::class.java))
                finish()
            } else Toast.makeText(this, "night mode already enabled", Toast.LENGTH_SHORT).show()

        } else if (item.itemId == R.id.mode_light) {
            if (darkModeEnabled) {
                enableDarkMode(false)
                Toast.makeText(this, "light mode enabled", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ChristmasActivity::class.java))
                finish()
            } else Toast.makeText(this, "light mode already enabled", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enableDarkMode(value: Boolean?) {
        when (value) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
                darkModeEnabled = true

                GlobalScope.launch(Dispatchers.IO) {
                    Theme(this@ChristmasActivity).enableDarkMode(true)
                }
            }

            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
                darkModeEnabled = false
                GlobalScope.launch(Dispatchers.IO) {
                    Theme(this@ChristmasActivity).enableDarkMode(false)
                }
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    private var confirm: Boolean = true

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (confirm) {
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show()
            confirm = false
            Handler().postDelayed({
                confirm = true
            }, 2000)
        } else super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _christmasBinding = null
    }
}