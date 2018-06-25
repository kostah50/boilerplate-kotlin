package com.denwehrle.boilerplate.redux.middleware

import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.actions.LoadContactsAction
import com.denwehrle.boilerplate.redux.actions.LoadedContactsSuccessfulAction
import com.denwehrle.boilerplate.redux.state.AppState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class NetworkMiddleware(private val dataManager: ContactDataManager) : Middleware<AppState> {
    override fun invoke(dispatch: DispatchFunction, state: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                    is LoadContactsAction -> {
                        getContacts(dispatch)
                    }
                }
            }
        }
    }

    private fun getContacts(dispatch: DispatchFunction) {
        Timber.d("NetworkMiddleware - getContacts")
        dataManager.syncContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            dispatch(LoadedContactsSuccessfulAction(it))
                        },
                        onError = {
                            Timber.e(it)
                            dispatch(LoadedContactsSuccessfulAction(listOf()))
                        }
                )
    }
}