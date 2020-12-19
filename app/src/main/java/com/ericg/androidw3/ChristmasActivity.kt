package com.ericg.androidw3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ericg.androidw3.databinding.ActivityChristmasBinding

class ChristmasActivity : AppCompatActivity() {
    /**
     * create a mutable variable that holds the dataBinding object.
     * get its value and init it to an immutable variable.
     * initialize the mutable var in onCreate() method.
     * */

    private var _christmasBinding: ActivityChristmasBinding? = null
    private val christmasBinding get() = _christmasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _christmasBinding = DataBindingUtil.setContentView(this, R.layout.activity_christmas)

        /** safely set clickListener and navigate using an Intent*/

        val intentToNewYearActivity = Intent(this, NewYearActivity::class.java)
        christmasBinding?.button1?.setOnClickListener {
            startActivity(intentToNewYearActivity)
            /* show toast message */
            Toast.makeText(this, "welcome to new year", Toast.LENGTH_SHORT).show()
            finish()

            /** finish() clears activity from the back stack so that the user can not navigate
             *back by pressing the device back button*/
        }
    }

    /**
     *set binding object variable to null to avoid memory leaks */

    override fun onDestroy() {
        super.onDestroy()
        _christmasBinding = null
    }
}