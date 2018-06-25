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

class ContactsViewModel @Inject constructor(private val store: AppStore) : ViewModel(), StoreSubscriber<List<Contact>> {
    private var contacts: MutableLiveData<List<Contact>> = MutableLiveData()

    init {
        store.subscribe(this){
            it.select {
                it.contacts
            }.skipRepeats()
        }
    }

    override fun newState(state: List<Contact>) {
        contacts.value = state
    }

    fun getContacts(): LiveData<List<Contact>> {
        return contacts
    }

    override fun onCleared() {
        super.onCleared()
        store.unsubscribe(this)
    }
}