package com.denwehrle.boilerplate.test

import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.factory.ContactFactory
import com.denwehrle.boilerplate.ui.contact.old.ContactMvpViewOld
import com.denwehrle.boilerplate.ui.contact.old.ContactPresenterOld
import com.denwehrle.boilerplate.util.helper.RxSchedulersOverrideRule
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Dennis Wehrle
 */
@RunWith(MockitoJUnitRunner::class)
class ContactPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    lateinit var presenter: ContactPresenterOld

    @Mock
    private lateinit var mockMvpView: ContactMvpViewOld

    @Mock
    private lateinit var mockContactDataManager: ContactDataManager

    @Before
    fun setUp() {
        presenter = ContactPresenterOld(mockContactDataManager)
        presenter.attachView(mockMvpView)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun loadDataReturnsData() {
        val data = ContactFactory.makeContactList(10)
        whenever(mockContactDataManager.getContacts()).thenReturn(Flowable.just(data))

        presenter.loadData()
        verify<ContactMvpViewOld>(mockMvpView).showData(data)
        verify<ContactMvpViewOld>(mockMvpView, never()).showError()
    }

    /*@Test
    fun loadDataReturnsEmptyList() {
        whenever(mockContactDataManager.getContacts()).thenReturn(Flowable.just(emptyList()))

        presenter.loadData()
        verify<ContactMvpViewOld>(mockMvpView, never()).showData(ArgumentMatchers.anyList<Contact>())
        verify<ContactMvpViewOld>(mockMvpView, never()).showError()
    }*/

    @Test
    fun loadDataFails() {
        whenever(mockContactDataManager.getContacts()).thenReturn(Flowable.error<List<Contact>>(RuntimeException()))

        presenter.loadData()
        verify<ContactMvpViewOld>(mockMvpView).showError()
        verify<ContactMvpViewOld>(mockMvpView, never()).showData(ArgumentMatchers.anyList<Contact>())
    }
}