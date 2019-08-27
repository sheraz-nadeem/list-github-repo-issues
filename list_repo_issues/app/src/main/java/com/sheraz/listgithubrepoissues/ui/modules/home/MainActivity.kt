package com.sheraz.listgithubrepoissues.ui.modules.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sheraz.listgithubrepoissues.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
