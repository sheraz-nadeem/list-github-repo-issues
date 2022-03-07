package com.sheraz.core.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.sheraz.core.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class TextViewNormal @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @ApplicationContext
    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var logger: Logger

    private var fontNormal: Typeface? = null

    init {
        fontNormal = Typeface.createFromAsset(context.assets, "fonts/Geogtq-Lg.otf")
        setCustomFont()
    }

    private fun setCustomFont() {
        logger.d(TAG, "setCustomFont: context = $context, appContext = $appContext")
        val typeface = typeface
        setTypeface(fontNormal)

        if (typeface.isItalic)
            setTypeface(getTypeface(), Typeface.ITALIC)
    }
}
private const val TAG = "TextViewNormal"
