package com.juniormelgarejo.cats.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.juniormelgarejo.cats.R
import com.juniormelgarejo.cats.databinding.ActivityMainBinding
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
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(viewModel)
        setupView()
        subscribeUi()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun subscribeUi() {
        viewModel.items.observe(this, Observer {
            mainAdapter?.setItems(it)
        })
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
