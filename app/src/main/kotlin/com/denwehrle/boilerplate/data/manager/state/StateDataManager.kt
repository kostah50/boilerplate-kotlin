package com.denwehrle.boilerplate.data.manager.state

import com.denwehrle.boilerplate.data.local.helper.DatabaseHelper
import com.denwehrle.boilerplate.data.local.helper.PreferenceHelper
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.data.manager.base.BaseDataManager
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.redux.state.ContactsState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The [StateDataManager] is the single point of communication between the different layers.
 *
 * @author Dennis Wehrle
 */
@Singleton
class StateDataManager @Inject constructor(preferenceHelper: PreferenceHelper,
                                           private val databaseHelper: DatabaseHelper
) : BaseDataManager(preferenceHelper) {

    fun saveStatePersistently(appState: AppState) {
        databaseHelper.appStateDao().insertAll(appState)
        appState.contactsState.contacts.map {
            databaseHelper.contactDao().insertAll(it)
        }
    }

    fun loadAppStateFromPersistentStorage(): Single<AppState> {
        return databaseHelper.appStateDao().getAll()
    }

    fun loadContactsFromPersistentStorage(): Single<List<Contact>> {
        return databaseHelper.contactDao().getAllAsSingle()
    }

    fun somethingNew(): Flowable<Any> {
        return Single.concat(databaseHelper.appStateDao().getAll(), databaseHelper.contactDao().getAllAsSingle().flatMap {
            Single.just(ContactsState(contacts = it))
        })
    }
}