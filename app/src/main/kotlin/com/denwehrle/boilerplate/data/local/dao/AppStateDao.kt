package com.denwehrle.boilerplate.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.denwehrle.boilerplate.redux.state.AppState
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author Dennis Wehrle
 */
@Dao
interface AppStateDao {

    @Query("SELECT * FROM AppState")
    fun getAll(): Single<AppState>

//    @Query("SELECT * FROM contact " +
//            "WHERE firstName = :firstName")
//    fun findByFirstName(firstName: String): Single<Contact>
//
//    @Query("SELECT * FROM contact " +
//            "WHERE email = :email")
//    fun findByEmail(email: String): Single<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg data: AppState): List<Long>

//    @Update
//    fun updateUsers(vararg data: Contact): Int

    @Query("DELETE FROM AppState")
    fun clearTable()
}