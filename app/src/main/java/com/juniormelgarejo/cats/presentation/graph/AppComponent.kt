package com.juniormelgarejo.cats.presentation.graph

import android.content.Context
import com.juniormelgarejo.cats.presentation.CatsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApiProviderModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent {

    fun inject(appTemplateApplication: CatsApplication): CatsApplication

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
