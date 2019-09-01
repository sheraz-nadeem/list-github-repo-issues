package com.sheraz.listgithubrepoissues.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.os.Build
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