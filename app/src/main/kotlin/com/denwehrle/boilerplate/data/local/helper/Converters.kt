package com.denwehrle.boilerplate.data.local.helper

import android.arch.persistence.room.TypeConverter
import com.denwehrle.boilerplate.redux.state.ContactsState
import java.util.*

/**
 * @author Dennis Wehrle
 */
class Converters {

    @TypeConverter
    fun longToDate(time: Long?) = if (time == null) Date() else Date(time)

    @TypeConverter
    fun dateToLong(date: Date?) = date?.time

    @TypeConverter
    fun contactsStateToString(value: ContactsState) = ""

    @TypeConverter
    fun StringToContactsState(value: String) = ContactsState()

//    @TypeConverter
//    fun fromCountryLangList(value: List<CountryLang>): String {
//        val gson = Gson()
//        val type = object : TypeToken<List<CountryLang>>() {}.type
//        return gson.toJson(value, type)
//    }
//
//    @TypeConverter
//    fun toCountryLangList(value: String): List<CountryLang> {
//        val gson = Gson()
//        val type = object : TypeToken<List<CountryLang>>() {}.type
//        return gson.fromJson(value, type)
//    }
}