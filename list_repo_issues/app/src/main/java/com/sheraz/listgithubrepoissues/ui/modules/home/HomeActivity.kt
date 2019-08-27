package com.sheraz.listgithubrepoissues.ui.modules.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sheraz.listgithubrepoissues.BR
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.ActivityHomeBinding
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseActivityToolbar
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivityToolbar<ActivityHomeBinding, HomeViewModel>() {

    private lateinit var activityHomeBinding: ActivityHomeBinding

    private val homeViewModelFactory = HomeViewModelFactory()
    private lateinit var homeViewModel: HomeViewModel

    init {

        logger.d(TAG, "init(): ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
        activityHomeBinding = getViewDataBinding()
        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
        initUI()
    }

    override fun getLayoutResId() = R.layout.activity_home

    override fun getBindingVariable() = BR.homeViewModel

    override fun getViewModel() = homeViewModel

    override fun initUI() {

        logger.d(TAG, "initUI(): ")
        setUpActionBar()
        fab.hide()

    }

    override fun subscribeUi() {

        logger.d(TAG, "subscribeUi(): ")

    }



    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}
