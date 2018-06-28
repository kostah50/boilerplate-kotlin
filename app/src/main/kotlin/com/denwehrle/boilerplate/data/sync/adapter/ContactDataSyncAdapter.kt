package com.denwehrle.boilerplate.data.sync.adapter

import android.accounts.Account
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.state.AppStore
import com.denwehrle.boilerplate.util.notification.NotificationUtils
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author Dennis Wehrle
 */
class ContactDataSyncAdapter(context: Context,
                             autoInitialize: Boolean,
                             private val contactDataManager: ContactDataManager,
                             private val store: AppStore) : AbstractSyncAdapter(context, autoInitialize) {

    override fun onPerformSync(account: Account, extras: Bundle, authority: String, provider: ContentProviderClient, syncResult: SyncResult) {
        syncContacts()
    }

    private fun syncContacts() {
        disposables.add(contactDataManager.syncContacts(store)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            val notification = NotificationUtils(context).buildNotification(it)
                            if (notification != null) {
                                val notificationManager = NotificationManagerCompat.from(context)
                                notificationManager.notify(1, notification)
                            }
                        },
                        onError = {
                            Timber.e(it)
                        }
                )
        )
    }
}