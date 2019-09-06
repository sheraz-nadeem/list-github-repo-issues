package com.sheraz.listgithubrepoissues.ui.modules.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sheraz.core.utils.Logger

/**
 * An abstract base class for all the activities in our app
 */

abstract class BaseActivity<VIEW_DATA_BINDING : ViewDataBinding, VIEW_MODEL : BaseViewModel> : AppCompatActivity() {

    private lateinit var progressDialog: Dialog
    private var baseViewDataBinding: VIEW_DATA_BINDING? = null
    private lateinit var baseViewModel: VIEW_MODEL

    protected val logger = Logger()

    init {

        logger.d(TAG, "init(): ")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
    }

    protected fun getViewDataBinding(): VIEW_DATA_BINDING? {
        logger.d(TAG, "getViewDataBinding(): ")
        return baseViewDataBinding
    }

    protected fun setViewDataBinding(binding: VIEW_DATA_BINDING) {
        logger.d(TAG, "setViewDataBinding(): ")
        baseViewDataBinding = binding
    }

    protected fun performDataBinding() {
        logger.d(TAG, "performDataBinding(): ")
        baseViewDataBinding = baseViewDataBinding ?: DataBindingUtil.setContentView(this, getLayoutResId())
        baseViewModel = getViewModel()
        baseViewDataBinding?.setVariable(getBindingVariable(), baseViewModel)
        baseViewDataBinding?.executePendingBindings()
    }


    /**********
     * Abstract Methods
     **********/

    /**
     * Child classes will use this method to
     * initialize their data in this method
     */
    abstract fun initData()

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
     * Override for subscribing observers to get
     * updates from reactive streams of data
     */
    abstract fun subscribeUi()

    /**
     * Override to unsubscribe any observers,
     * when needed
     */
    abstract fun unsubscribeUi()


    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }
}