package com.denwehrle.boilerplate.redux.state

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.denwehrle.boilerplate.App
import org.rekotlin.StateType
import com.denwehrle.boilerplate.data.local.model.Contact

/**
 * @author Miguel Costa
 */
@Entity
data class AppState(
        @PrimaryKey
        val index: String,

        val isLoading: Boolean,
        val contactsState: ContactsState
) : StateType {
    companion object {
        fun notLoading(): AppState {
            return AppState(
                    index = "1",
                    isLoading = false,
                    contactsState = ContactsState()
            )
        }
    }
}