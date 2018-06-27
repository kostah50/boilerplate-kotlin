package com.denwehrle.boilerplate.data.local.helper

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.denwehrle.boilerplate.data.local.dao.AppStateDao
import com.denwehrle.boilerplate.data.local.dao.ContactDao
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.state.AppState

/**
 * @author Dennis Wehrle
 */
@Database(entities = [Contact::class, AppState::class], version = 2)
@TypeConverters(Converters::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun contactDao(): ContactDao
    abstract fun appStateDao(): AppStateDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): DatabaseHelper {
            return Room.databaseBuilder(
                    context,
                    DatabaseHelper::class.java, "boilerplate.db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}