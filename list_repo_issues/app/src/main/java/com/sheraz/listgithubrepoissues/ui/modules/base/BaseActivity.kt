package com.sheraz.listgithubrepoissues.ui.modules.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sheraz.core.utils.Logger

abstract class BaseActivity<VIEW_DATA_BINDING : ViewDataBinding, VIEW_MODEL : BaseViewModel> : AppCompatActivity() {

    private lateinit var progressDialog: Dialog
    private lateinit var baseViewDataBinding: VIEW_DATA_BINDING
    private lateinit var baseViewModel: VIEW_MODEL

    protected val logger = Logger()

    init {

        logger.d(TAG, "init(): ")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
    }

    protected fun getViewDataBinding(): VIEW_DATA_BINDING {
        logger.d(TAG, "getViewDataBinding(): ")
        return baseViewDataBinding
    }

    protected fun setViewDataBinding(binding: VIEW_DATA_BINDING) {
        logger.d(TAG, "setViewDataBinding(): ")
        baseViewDataBinding = binding
    }

    protected fun performDataBinding() {
        logger.d(TAG, "performDataBinding(): ")
        baseViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        baseViewModel = getViewModel()
        baseViewDataBinding.setVariable(getBindingVariable(), baseViewModel)
        baseViewDataBinding.executePendingBindings()
    }


    /**********
     * Abstract Methods
     **********/


    /**
     * Child classes will use this method to
     * initialize their UI elements in this method
     */
    abstract fun initUI()


    /**
     * @return layout resource id
     */
    @LayoutRes abstract fun getLayoutResId(): Int

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * Override for set view model
     * @return view model instance
     */
    abstract fun getViewModel(): VIEW_MODEL

    /**
     * Override for subscribing to live data
     */
    abstract fun subscribeUi()


    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }
}