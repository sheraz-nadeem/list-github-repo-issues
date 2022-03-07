package com.sheraz.core.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.sheraz.core.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditTextGeogroFont @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    @Inject
    lateinit var logger: Logger

    private var fontGeogro: Typeface? = null

    init {
        fontGeogro = Typeface.createFromAsset(context.assets, "fonts/Geogtq-Md.otf")
        setCustomFontGeogro()
    }

    private fun setCustomFontGeogro() {
        logger.d(TAG, "setCustomFont: context = $context")
        this.typeface = fontGeogro
    }
}
private const val TAG = "EditTextGeogroFont"
