package com.denwehrle.boilerplate.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.denwehrle.boilerplate.BuildConfig
import com.denwehrle.boilerplate.data.local.helper.DatabaseHelper
import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.data.remote.endpoints.ContactService
import com.denwehrle.boilerplate.data.remote.factory.ContactServiceFactory
import com.denwehrle.boilerplate.redux.middleware.NetworkMiddleware
import com.denwehrle.boilerplate.redux.reducers.appReducer
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.redux.state.AppStore
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import org.rekotlin.Store
import javax.inject.Singleton

/**
 * @author Dennis Wehrle
 */
@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(context: Context): DatabaseHelper {
        return Room.inMemoryDatabaseBuilder(context, DatabaseHelper::class.java).build()
    }

    @Provides
    @Singleton
    fun provideContactDataManager(): ContactDataManager {
        return mock()
    }

    @Provides
    @Singleton
    internal fun provideContactService(): ContactService {
        return ContactServiceFactory.makeContactService(BuildConfig.DEBUG)
    }

    /********* Redux *********/

    @Provides
    @Singleton
    fun provideStore(dataManager : ContactDataManager): AppStore = Store(
            reducer = ::appReducer,
            state = AppState(),
            middleware = listOf(NetworkMiddleware(dataManager))
    )
}