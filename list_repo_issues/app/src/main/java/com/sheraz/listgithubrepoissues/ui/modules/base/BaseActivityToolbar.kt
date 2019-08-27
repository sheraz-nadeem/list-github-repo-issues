package com.sheraz.listgithubrepoissues.ui.modules.base

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sheraz.listgithubrepoissues.R
import kotlinx.android.synthetic.main.app_toolbar.*


abstract class BaseActivityToolbar<T : ViewDataBinding, VM : BaseViewModel>: BaseActivity<T, VM>() {

    init {

        logger.d(TAG, "init(): ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)

        val toolbarBinding = DataBindingUtil.setContentView<T>(this, R.layout.app_toolbar)
        val contentBinding = DataBindingUtil.inflate<T>(layoutInflater, getLayoutResId(), flMainContainer, false)

        flMainContainer.addView(contentBinding.root)
        setViewDataBinding(contentBinding)
    }

    protected fun setUpActionBar() {

        logger.d(TAG, "setUpActionBar(): ")

        setSupportActionBar(toolbar)

        // For the immersive-window behavior, we do the following
        // toolbar gets cut in half due to "windowTranslucentStatus = true" &
        // "windowTranslucentNavigation = true" set in Activity Theme,
        // so we have to handle insets ourselves
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {

            toolbar.setOnApplyWindowInsetsListener { v, insets ->

                // inset the toolbar down by the status bar height
                val lpToolbar = v.layoutParams as ViewGroup.MarginLayoutParams
                lpToolbar.topMargin += insets.systemWindowInsetTop
                lpToolbar.leftMargin += insets.systemWindowInsetLeft
                lpToolbar.rightMargin += insets.systemWindowInsetRight
                v.layoutParams = lpToolbar

                // inset the flMainContainer down by the status bar height
                val lpFlMainContainer = flMainContainer.layoutParams as ViewGroup.MarginLayoutParams
                lpFlMainContainer.topMargin += insets.systemWindowInsetTop
                lpFlMainContainer.leftMargin += insets.systemWindowInsetLeft
                lpFlMainContainer.rightMargin += insets.systemWindowInsetRight
                flMainContainer.layoutParams = lpFlMainContainer

                // clear this listener so insets aren't re-applied
                v.setOnApplyWindowInsetsListener(null)

                insets.consumeSystemWindowInsets()
            }
        }

    }

    companion object {
        private val TAG = BaseActivityToolbar::class.java.simpleName
    }

}