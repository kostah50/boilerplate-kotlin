package com.denwehrle.boilerplate.injection.module

import android.app.Application
import android.content.Context
import com.denwehrle.boilerplate.BuildConfig
import com.denwehrle.boilerplate.data.local.helper.DatabaseHelper
import com.denwehrle.boilerplate.data.local.helper.PreferenceHelper
import com.denwehrle.boilerplate.data.remote.endpoints.ContactService
import com.denwehrle.boilerplate.data.remote.factory.ContactServiceFactory
import com.denwehrle.boilerplate.redux.reducers.appReducer
import com.denwehrle.boilerplate.redux.state.AppStore
import dagger.Module
import dagger.Provides
import org.rekotlin.Store
import javax.inject.Singleton

/**
 * Module used to provide dependencies at an application-level.
 *
 * @author Dennis Wehrle
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(context: Context): PreferenceHelper {
        return PreferenceHelper(context)
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(application: Application): DatabaseHelper {
        return DatabaseHelper.getInstance(application)
    }


    /********* Service *********/

    @Provides
    @Singleton
    fun provideContactService(): ContactService {
        return ContactServiceFactory.makeContactService(BuildConfig.DEBUG)
    }


    /********* Redux *********/

    @Provides
    @Singleton
    fun provideStore(): AppStore = Store(
            reducer = ::appReducer,
            state = null,
            middleware = listOf()
    )
}