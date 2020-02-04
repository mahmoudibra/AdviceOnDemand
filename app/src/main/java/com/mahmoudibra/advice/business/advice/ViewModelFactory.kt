package com.mahmoudibra.advice.business.advice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class ViewModelFactory(
    private val trendingGithubReposRepository: AdvicesRepository
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnDemandAdviceVM::class.java)) {
            return OnDemandAdviceVM(
                trendingGithubReposRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
