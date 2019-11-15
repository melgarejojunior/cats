package com.juniormelgarejo.cats.presentation.main

import androidx.lifecycle.*
import com.juniormelgarejo.cats.domain.GetImages
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getImages: GetImages
) : ViewModel(), LifecycleObserver {

    val items: LiveData<List<String>> get() = _items
    private val _items = MutableLiveData<List<String>>()

    private var disposable: Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun retriveImages() {
        disposable?.dispose()
        disposable = getImages.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(::onError) {
                _items.postValue(it.images)
            }

    }

    private fun onError(throwable: Throwable) {
        val t = throwable
    }
}