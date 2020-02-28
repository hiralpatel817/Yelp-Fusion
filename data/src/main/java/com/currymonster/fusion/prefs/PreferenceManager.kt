package com.currymonster.fusion.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.currymonster.fusion.data.BuildConfig
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences

@SuppressLint("CommitPrefEdits", "ApplySharedPref")
class PreferenceManager(prefsName: String, context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(String.format(PREF_NAME, prefsName), Context.MODE_PRIVATE)

    private val rxPreferences = RxSharedPreferences.create(preferences)

    /**
     * Access Methods
     */

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean =
        preferences.getBoolean(key, defaultValue)

    fun getString(
        key: String,
        defaultValue: String = ""
    ): String =
        preferences.getString(key, defaultValue)!!


    fun getRxBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Preference<Boolean> {
        return rxPreferences.getBoolean(key, defaultValue)
    }

    fun getRxString(
        key: String, defaultValue: String = ""
    ): Preference<String> {
        return rxPreferences.getString(key, defaultValue)
    }

    /**
     * Set Methods
     */

    fun putString(
        key: String,
        value: String
    ) {
        preferences.edit().putString(key, value).commit()
    }

    fun putBoolean(
        key: String,
        value: Boolean
    ) {
        preferences.edit().putBoolean(key, value).commit()
    }

    fun remove(
        key: String
    ) = preferences.edit().remove(key).commit()

    fun contains(
        key: String
    ): Boolean {
        return preferences.contains(key)
    }

    /**
     * Default Access
     */

    val selectedEnvironment =
        getString(KEY_SELECTED_ENV, BuildConfig.BUILD_TYPE)

    companion object {

        const val PREF_NAME = "%s.Preferences"

        /**
         * Stored Values
         */

        const val KEY_SELECTED_ENV = "key_selected_env"

    }
}