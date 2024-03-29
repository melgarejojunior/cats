package com.juniormelgarejo.cats.presentation

import android.app.Application
import com.juniormelgarejo.cats.presentation.graph.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CatsApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .context(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}