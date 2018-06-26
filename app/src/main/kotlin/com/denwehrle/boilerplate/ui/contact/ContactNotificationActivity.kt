package com.denwehrle.boilerplate.ui.contact

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.actions.LoadContactsAction
import com.denwehrle.boilerplate.redux.actions.SelectedContactAction
import com.denwehrle.boilerplate.redux.actions.SyncAction
import com.denwehrle.boilerplate.redux.state.AppStore
import com.denwehrle.boilerplate.ui.base.BaseActivity
import com.denwehrle.boilerplate.util.extension.isNetworkConnected
import com.denwehrle.boilerplate.viewModel.ActiveContactViewModel
import com.denwehrle.boilerplate.viewModel.ContactsViewModel
import com.denwehrle.boilerplate.viewModel.LoadingViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.content_contact.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Miguel Costa
 */
class ContactNotificationActivity : AppCompatActivity() {
    /**
     * To make classed injectable make sure they have a constructor
     * with the @Inject annotation.
     */

    /** Store to be able to dispatch actions */
    @Inject
    lateinit var store: AppStore

    /** View Model Factory for the MVVM model and Dagger 2 injections */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        /** Set Up all the UI components that need so*/
        setUpUIComponents()
    }

    private fun setUpViewModels() {
        val contactsViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        contactsViewModel.getContacts().observe(this, Observer {
            if(intent.hasExtra("contactEmail") && it != null){
                for(contact in it){
                    if(contact.email == intent.getStringExtra("contactEmail")){
                        store.dispatch(SelectedContactAction(contact))
                    }
                }
            }
            val openContactActivityIntent = Intent(this, ContactActivity::class.java)
            openContactActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(openContactActivityIntent)
            finish()
        })
    }

    private fun setUpUIComponents() {
        setUpViewModels()
    }
}