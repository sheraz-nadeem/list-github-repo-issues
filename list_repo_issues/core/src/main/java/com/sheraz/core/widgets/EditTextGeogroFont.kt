package com.sheraz.core.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EditTextGeogroFont : AppCompatEditText {

    private var fontGeogro: Typeface? = null

    constructor(context: Context) : super(context) {
        fontGeogro = Typeface.createFromAsset(context.assets, "fonts/Geogtq-Md.otf")
        setCustomFontGeogro()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        fontGeogro = Typeface.createFromAsset(context.assets, "fonts/Geogtq-Md.otf")
        setCustomFontGeogro()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        fontGeogro = Typeface.createFromAsset(context.assets, "fonts/Geogtq-Md.otf")
        setCustomFontGeogro()
    }

    private fun setCustomFontGeogro() {
        this.typeface = fontGeogro
    }
}
