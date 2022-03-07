package com.sheraz.core.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.sheraz.core.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class TextViewArcaHeavy @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @ActivityContext
    @Inject
    lateinit var activityContext: Context

    @Inject
    lateinit var logger: Logger

    private var fontHeavy: Typeface? = null

    init {
        fontHeavy = Typeface.createFromAsset(context.assets, "fonts/Arca-Heavy.ttf")
        setCustomFontHeavy()
    }

    private fun setCustomFontHeavy() {
        logger.d(TAG, "setCustomFont: context = $context, activityContext = $activityContext")
        this.typeface = fontHeavy
    }
}
private const val TAG = "TextViewArcaHeavy"
