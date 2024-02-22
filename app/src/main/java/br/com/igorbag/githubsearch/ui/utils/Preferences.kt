package br.com.igorbag.githubsearch.ui.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {



    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var PRIVATE : Int = 0

    init {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, PRIVATE)
        editor = preferences.edit()
    }

    companion object {
        const val PREFERENCES_NAME = "Preferences"
        const val IS_LOGIN = "is_login"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_ROLE = "role"
        const val KEY_ID = "user_id"
        const val KEY_NAME = "user_name"
        const val KEY_EMAIL = "user_email"
        const val KEY_BIRTHDATE = "user_birthdate"
        const val KEY_PHONE = "user_phone"
        const val KEY_GENDER = "user_gender"
        const val KEY_COUNTRY = "user_country"
        const val KEY_CITY = "user_city"
        const val KEY_LANGUAGE = "user_language"
        const val KEY_UNITS = "user_units"
        const val KEY_IMAGE = "user_image"
    }

    fun saveUser(nomeUser: String) {
        editor.putString(KEY_NAME, nomeUser)
        editor.commit()
        editor.apply()
    }

    fun getName() : String {
        return preferences.getString(KEY_NAME, "").toString()
    }



}