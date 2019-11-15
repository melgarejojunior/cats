package com.juniormelgarejo.cats.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import com.juniormelgarejo.cats.R
import com.juniormelgarejo.cats.data.entity.RequestException
import com.juniormelgarejo.cats.databinding.ActivityMainBinding
import com.juniormelgarejo.cats.domain.entity.Error
import com.juniormelgarejo.cats.presentation.util.EventObserver
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private var mainAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVars()
        setupView()
        subscribeUi()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun initVars() {
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(viewModel)
    }

    private fun subscribeUi() {
        viewModel.items.observe(this, Observer {
            mainAdapter?.setItems(it)
        })
        viewModel.snackText.observe(this, EventObserver {
            createErrorSnackbar(it)
        })
    }

    private fun createErrorSnackbar(error: Error) {
        Snackbar.make(binding.root, resolveError(error.exception), Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) { error.action?.invoke() }
            .show()
    }

    private fun resolveError(exception: RequestException): String {
        return when (exception) {
            is RequestException.TimeoutError -> getString(R.string.timeout_error)
            is RequestException.UnexpectedError -> getString(R.string.unexpected_error)
            is RequestException.NetworkError -> getString(R.string.network_error)
            else -> exception.errorMessage
        }
    }

    private fun setupView() {
        if (mainAdapter == null) mainAdapter = MainAdapter()
        with(binding.recyclerView) {
            adapter = mainAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
            }
        }
    }
}
