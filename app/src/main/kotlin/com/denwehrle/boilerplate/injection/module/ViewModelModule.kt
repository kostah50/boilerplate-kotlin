package com.denwehrle.boilerplate.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.denwehrle.boilerplate.viewModel.*

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Used for Dagger 2 Dependency Injection
 *
 * @author Miguel Costa
 */
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoadingViewModel::class)
    internal abstract fun bindLoadingViewModel(loadingViewModel: LoadingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    internal abstract fun bindContactsViewModel(contactsViewModel: ContactsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActiveContactViewModel::class)
    internal abstract fun bindActiveContactViewModel(activeContactViewModel: ActiveContactViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    internal abstract fun bindWelcomeViewModel(welcomeViewModel: WelcomeViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ExxetaViewModelFactory): ViewModelProvider.Factory
}