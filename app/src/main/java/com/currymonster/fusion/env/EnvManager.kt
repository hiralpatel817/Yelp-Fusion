package com.currymonster.fusion.env

import com.currymonster.fusion.App
import com.currymonster.fusion.BuildConfig
import com.currymonster.fusion.env.envs.Environment
import com.currymonster.fusion.prefs.PreferenceManager

class EnvManager(
    private val app: App,
    private val preferenceManager: PreferenceManager
) {

    @Suppress("TooGenericExceptionCaught")
    fun getEnvironment(): Environment {
        return try {
            val envName = preferenceManager.selectedEnvironment
            Environment.get(envName)
        } catch (ex: Exception) {
            Environment.default
        }
    }

    fun getEnviorments() =
        getEnvironment().availableEnvironments.map {
            it.name
        }

    fun setEnviorment(env: String) {
        if (BuildConfig.DEBUG) {
            preferenceManager.putString(PreferenceManager.KEY_SELECTED_ENV, env)
            app.resetApp()
        }
    }
}