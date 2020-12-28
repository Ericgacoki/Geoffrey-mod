package com.ericg.androidw3

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map

class Theme(context: Context) {
    private val dataStore = context.createDataStore(name = "theme")

    companion object {
        val DARK_MODE = preferencesKey<Boolean>("dark_mode")
    }

    suspend fun enableDarkMode(value: Boolean) {
        dataStore.edit {
            it[DARK_MODE] = value
        }
    }

    fun readPrefs(): LiveData<Boolean> {
        return dataStore.data.map {
            it[DARK_MODE] ?: false
        }.asLiveData()
    }
}