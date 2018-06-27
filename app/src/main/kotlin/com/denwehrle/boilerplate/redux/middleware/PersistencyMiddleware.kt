package com.denwehrle.boilerplate.redux.middleware

import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.actions.*
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.util.sync.SyncUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import timber.log.Timber

/**
 * Middleware to intercept Network Related Actions
 *
 * @author Miguel Costa
 */
class PersistencyMiddleware(val contactDataManager: ContactDataManager) : Middleware<AppState> {
    override fun invoke(dispatch: DispatchFunction, state: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                    is SaveState -> {

                    }
                    is LoadState -> {
                        getContacts(dispatch)
                    }
                }
            }
        }
    }

    private fun getContacts(dispatch: DispatchFunction) {
        contactDataManager.getContactsAsSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            dispatch(LoadContactsSuccessfulAction(it))
                        },
                        onError = {
                            Timber.e(it)
                            dispatch(LoadContactsFailedAction())
                        }
                )
    }
}