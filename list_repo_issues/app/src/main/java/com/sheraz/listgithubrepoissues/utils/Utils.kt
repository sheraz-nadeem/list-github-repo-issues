package com.sheraz.listgithubrepoissues.utils

import android.app.Dialog
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi


@RequiresApi(api = Build.VERSION_CODES.M)
fun setWhiteNavigationBar(dialog: Dialog) {

    val window = dialog.window
    if (window != null) {
        val metrics = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(metrics)

        val dimDrawable = GradientDrawable()
        // ...customize your dim effect here

        val navigationBarDrawable = GradientDrawable()
        navigationBarDrawable.shape = GradientDrawable.RECTANGLE
        navigationBarDrawable.setColor(Color.WHITE)

        val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)

        val windowBackground = LayerDrawable(layers)
        windowBackground.setLayerInsetTop(1, metrics.heightPixels)

        window.setBackgroundDrawable(windowBackground)
    }

}

fun hideKeyboard(context: Context, et: EditText) {
    try {
        et.clearFocus()
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
    } catch (e: Exception) {
    }

}

fun showKeyboard(context: Context, et: EditText) {
    try {
        et.requestFocus()
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, 0)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    } catch (e: Exception) {
    }

}