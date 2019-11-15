package com.juniormelgarejo.cats.presentation.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.juniormelgarejo.cats.domain.GetImages
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getImages: GetImages
) : ViewModel(), LifecycleObserver {
}