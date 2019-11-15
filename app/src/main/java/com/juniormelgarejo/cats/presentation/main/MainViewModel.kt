package com.juniormelgarejo.cats.presentation.main

import com.juniormelgarejo.cats.domain.GetImages
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getImages: GetImages
) {
}