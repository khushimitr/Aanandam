package com.example.aanandam.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SessionManager(val context : Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun updateSession(token : String,premiumUser : Boolean, email : String, sessionCnt : Int){
        val jwtTokenKey = stringPreferencesKey(Constants.JWT_TOKEN_KEY)
        val premiumUserKey = stringPreferencesKey(Constants.PREMIUM_USER_KEY)
        val emailKey = stringPreferencesKey(Constants.EMAIL_KEY)
        val sessionAvailedKey = stringPreferencesKey(Constants.SERVICES_AVAILED)
        context.dataStore.edit {preferences->
            preferences[jwtTokenKey] = token
            preferences[premiumUserKey] = premiumUser.toString()
            preferences[emailKey] = email
            preferences[sessionAvailedKey] = sessionCnt.toString()
        }
    }

    suspend fun getJWTToken() : String? {
        val jwtTokenKey = stringPreferencesKey(Constants.JWT_TOKEN_KEY)
        val preferences = context.dataStore.data.first()

        return preferences[jwtTokenKey]
    }

    suspend fun getCurrentUserType() : String? {
        val premiumUserKey = stringPreferencesKey(Constants.PREMIUM_USER_KEY)
        val preferences = context.dataStore.data.first()

        return preferences[premiumUserKey]
    }

    suspend fun getCurrentUserEmail() : String? {
        val emailKey = stringPreferencesKey(Constants.EMAIL_KEY)
        val preferences = context.dataStore.data.first()

        return preferences[emailKey]
    }

    suspend fun getAvailedServices() : String? {
        val servicesAvailedKey = stringPreferencesKey(Constants.SERVICES_AVAILED)
        val preferences = context.dataStore.data.first()

        return preferences[servicesAvailedKey]
    }

    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }
}