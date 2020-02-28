package com.currymonster.fusion.modules

import com.currymonster.fusion.App
import com.currymonster.fusion.env.EnvManager
import com.currymonster.fusion.env.envs.Environment
import com.currymonster.fusion.module.EnvProvider
import com.currymonster.fusion.prefs.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class EnvModule {

    @Provides
    @Singleton
    fun provideEnvManager(app: App, preferenceManager: PreferenceManager): EnvManager =
        EnvManager(app, preferenceManager)

    @Provides
    @Singleton
    open fun provideEnv(envManager: EnvManager): Environment =
        envManager.getEnvironment()

    @Provides
    @Singleton
    fun provideEnvironmentForData(env: Environment): EnvProvider =
        object : EnvProvider {
            override val apiKey: String
                get() = env.apiKey
            override val apiBaseUrl: String
                get() = env.apiBaseUrl
        }
}