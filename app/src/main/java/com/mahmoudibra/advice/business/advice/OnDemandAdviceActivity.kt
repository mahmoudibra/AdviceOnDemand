package com.mahmoudibra.advice.business.advice

import androidx.lifecycle.ViewModelProviders
import com.mahmoudibra.advice.R
import com.mahmoudibra.advice.base.BaseActivity
import com.mahmoudibra.advice.databinding.ActivityAdviceOnDemandBinding
import com.mahmoudibra.advice.source.remote.repoimpl.AdviceRepositoryImpl
import kotlinx.android.synthetic.main.activity_advice_on_demand.*

class OnDemandAdviceActivity :
    BaseActivity<OnDemandAdviceVM, ActivityAdviceOnDemandBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_advice_on_demand
    }

    override fun initialize() {
        val viewModelFactory = ViewModelFactory(
            trendingGithubReposRepository = AdviceRepositoryImpl()
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OnDemandAdviceVM::class.java)
        dataBindingView.viewmodel = viewModel
        dataBindingView.lifecycleOwner = this

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.on_demand_advice)
    }
}
