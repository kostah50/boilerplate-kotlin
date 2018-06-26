package com.denwehrle.boilerplate.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.state.AppStore
import org.rekotlin.StoreSubscriber
import timber.log.Timber
import javax.inject.Inject

/**
 * Contacts View Model
 * This View Model listens to changes in the [AppStore] contacts and
 * provides them to observing Views
 *
 * @author Miguel Costa
 */
class ActiveContactViewModel @Inject constructor(private val store: AppStore) : ViewModel(), StoreSubscriber<Contact?> {
    private var activeContact: MutableLiveData<Contact?> = MutableLiveData()

    init {
        store.subscribe(this){
            it.select {
                it.contactsState.activeContact
            }.skipRepeats()
        }
    }

    override fun newState(state: Contact?) {
        activeContact.value = state
    }

    fun getActiveContact(): LiveData<Contact?> {
        return activeContact
    }

    override fun onCleared() {
        super.onCleared()
        store.unsubscribe(this)
    }
}