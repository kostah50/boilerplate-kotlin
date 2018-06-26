package com.denwehrle.boilerplate.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.state.AppStore
import org.rekotlin.StoreSubscriber
import timber.log.Timber
import javax.inject.Inject

/**
 * Loading View Model
 * This View Model listens to changes in the [AppStore] isLoading and
 * provides them to observing Views
 *
 * @author Miguel Costa
 */
class LoadingViewModel @Inject constructor(private val store: AppStore) : ViewModel(), StoreSubscriber<Boolean> {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        store.subscribe(this){
            it.select {
                it.isLoading
            }.skipRepeats()
        }
    }

    override fun newState(state: Boolean) {
        isLoading.value = state
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun onCleared() {
        super.onCleared()
        store.unsubscribe(this)
    }
}