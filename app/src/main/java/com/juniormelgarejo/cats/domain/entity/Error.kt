package com.juniormelgarejo.cats.domain.entity

import com.juniormelgarejo.cats.data.entity.RequestException

data class Error(val exception: RequestException, val action: (() -> Unit)? = null)