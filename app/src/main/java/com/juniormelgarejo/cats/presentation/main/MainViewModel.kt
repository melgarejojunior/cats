package com.juniormelgarejo.cats.presentation.main

import androidx.lifecycle.*
import com.juniormelgarejo.cats.data.entity.RequestException
import com.juniormelgarejo.cats.domain.GetImages
import com.juniormelgarejo.cats.domain.entity.Error
import com.juniormelgarejo.cats.presentation.util.Event
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

    val snackText: LiveData<Event<Error>> get() = _snackText
    private val _snackText = MutableLiveData<Event<Error>>()

    private var disposable: Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun retrieveImages() {
        disposable?.dispose()
        disposable = getImages.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(::onError) {
                _items.postValue(it.images)
            }
    }

    private fun onError(throwable: Throwable) {
        _snackText.postValue(
            if (throwable is RequestException) {
                Event(Error(throwable, ::retrieveImages))
            } else {
                Event(Error(RequestException.GenericError(throwable.localizedMessage ?: "")))
            }
        )
    }
}