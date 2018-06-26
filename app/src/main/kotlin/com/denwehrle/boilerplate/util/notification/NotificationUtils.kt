package com.denwehrle.boilerplate.util.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.actions.SelectedContactAction
import com.denwehrle.boilerplate.redux.state.AppStore
import com.denwehrle.boilerplate.ui.contact.ContactActivity
import com.denwehrle.boilerplate.ui.contact.detail.ContactDetailActivity
import java.util.*
import javax.inject.Inject

/**
 * @author Dennis Wehrle
 */
@TargetApi(26)
class NotificationUtils(context: Context) : ContextWrapper(context) {

    /** Store to be able to dispatch actions */
    @Inject
    lateinit var store: AppStore


    private var notificationManager: NotificationManager? = null

    private val contactChannelId = "channelId"
    private val contactChannelName = "channelName"

    fun createChannels() {
        val contactChannel = NotificationChannel(contactChannelId, contactChannelName, NotificationManager.IMPORTANCE_DEFAULT)
        contactChannel.enableLights(true)
        contactChannel.enableVibration(true)
        contactChannel.lightColor = Color.RED
        contactChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager().createNotificationChannel(contactChannel)
    }

    private fun getManager(): NotificationManager {
        if (notificationManager == null) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager as NotificationManager
    }

    fun buildNotification(contacts: List<Contact>): Notification? {
        if (contacts.isEmpty()) return null

        val contact = contacts.first()
        val creationTime = Date().time
        val contentTitle = contact.toString()
        val contentText = contact.email

        val openContactEntryIntent = Intent(this, ContactActivity::class.java)
        store.dispatch(SelectedContactAction(contact))


        val pIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), openContactEntryIntent, 0)

        val channelId = "channelId"
        return NotificationCompat.Builder(this, channelId)
                .setWhen(creationTime)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_cake)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
                .build()
    }
}