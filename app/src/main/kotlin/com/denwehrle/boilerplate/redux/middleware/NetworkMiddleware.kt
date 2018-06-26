package com.denwehrle.boilerplate.redux.middleware

import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.actions.LoadContactsAction
import com.denwehrle.boilerplate.redux.actions.LoadContactsFailedAction
import com.denwehrle.boilerplate.redux.actions.LoadContactsSuccessfulAction
import com.denwehrle.boilerplate.redux.state.AppState
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
class NetworkMiddleware(private val dataManager: ContactDataManager) : Middleware<AppState> {
    override fun invoke(dispatch: DispatchFunction, state: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                    /**
                     * When we have a [LoadContactsAction] we want to intercept it
                     * and do the Rest Request */
                    is LoadContactsAction -> {
                        getContacts(dispatch)
                    }
                }
            }
        }
    }

    private fun getContacts(dispatch: DispatchFunction) {
        Timber.d("NetworkMiddleware - getContacts")
//        dataManager.getContacts()
        dataManager.syncContacts()
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