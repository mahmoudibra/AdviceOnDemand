package com.mahmoudibra.advice.base
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber




abstract class BaseActivity<P : BaseVM, V : ViewDataBinding> : AppCompatActivity(),
    CoroutineScope {

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext get() = mJob + Dispatchers.Main

    lateinit var viewModel: P

    val dataBindingView by lazy {
        DataBindingUtil.setContentView(this, getLayoutId()) as V

    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        observeLiveData()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initialize()

    private fun observeLiveData() {
        viewModel.inProgress.observe(this, Observer {
            when (it) {
                true -> Timber.e("Progress On")
                false -> Timber.e("Progress Off")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.hydrate()
    }
}
