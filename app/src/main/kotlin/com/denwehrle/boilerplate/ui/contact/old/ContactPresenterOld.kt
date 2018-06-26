package com.denwehrle.boilerplate.ui.contact.old

import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * The Presenter with its [ContactDataManager] are used for all the interaction with the data layer.
 * We use reactive streams as connection, so we can observe the database and get notified
 * by changes automatically.
 *
 * @author Dennis Wehrle
 */
class ContactPresenterOld @Inject constructor(private val dataManager: ContactDataManager) : BasePresenter<ContactMvpViewOld>() {

    /**
     * If we attach the Presenter there are tasks we can start regardless
     * the specific data, so let's do this here.
     */
    override fun attachView(mvpView: ContactMvpViewOld) {
        super.attachView(mvpView)

        this.mvpView.setUpToolbar()
        this.mvpView.setupRecyclerAdapter()
        this.mvpView.setupSwipeRefresh()
    }

    /**
     * We subscribe to updates from the [ContactDataManager] and call the
     * associated methods inside our view.
     */
    fun loadData() {
        disposables.add(dataManager.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            mvpView.showData(it)
                        },
                        onError = {
                            Timber.e(it)
                            mvpView.showError()
                        }
                )
        )
    }
}