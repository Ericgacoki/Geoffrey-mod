package com.ericg.androidw3

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ericg.androidw3.databinding.ActivityNewYearBinding
import com.ericg.androidw3.databinding.DialogExitBinding

class NewYearActivity : AppCompatActivity() {
    /** explanation is similar to the one in ChristmasActivity.kt*/

    private var _newYearBinding: ActivityNewYearBinding? = null
    private val newYearBinding get() = _newYearBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // change title
        supportActionBar?.title = "New Year"

        _newYearBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_year)

        newYearBinding?.button2?.setOnClickListener {
            // call show dialog function
            showDialog()

        }
    }

    /** create and show a dialog*/

    private fun showDialog() {
        val dialogBinding: DialogExitBinding? =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_exit, null, false)

        val dialogBuilder = AlertDialog.Builder(this).apply {
            setView(dialogBinding?.root)
        }
        dialogBuilder.create().show()

        dialogBinding?.btnNo?.setOnClickListener {
            Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
            dialogBuilder.create().dismiss()
        }
        dialogBinding?.btnYes?.setOnClickListener {
            finish()
        }
    }

   /* uncomment if you want this feature*/
    /** confirm before exiting the app*/
    /*

    var confirm: Boolean = true

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (confirm) {

            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show()
            confirm = false
            Handler().postDelayed({
                confirm = true
            }, 2000)
        } else super.onBackPressed()
    } */

    override fun onDestroy() {
        super.onDestroy()
        _newYearBinding = null
    }
}