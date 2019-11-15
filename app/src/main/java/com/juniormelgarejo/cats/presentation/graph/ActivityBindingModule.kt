package com.juniormelgarejo.cats.presentation.graph

import com.juniormelgarejo.cats.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeMainActivity(): MainActivity
}