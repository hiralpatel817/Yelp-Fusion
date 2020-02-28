package com.currymonster.fusion.modules

import android.app.Application
import android.content.Context
import com.currymonster.fusion.App
import com.currymonster.fusion.BuildConfig
import com.currymonster.fusion.prefs.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(val app: App) {

    @Provides
    fun provideApp() = app

    @Provides
    fun providesContext(): Context = app

    @Provides
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun providePreferenceManager(app: App): PreferenceManager =
        PreferenceManager(BuildConfig.APPLICATION_ID, app).apply {
            /**
             *  Default values here
             *
             *  Ex) putBoolean(PreferenceManager.key_feature)flag, BuildConfig.ENABLE_FEATURE)
             */
        }
}