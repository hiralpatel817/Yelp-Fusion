package com.currymonster.fusion

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.currymonster.fusion.modules.AppComponent
import com.currymonster.fusion.modules.AppModule
import com.currymonster.fusion.modules.DaggerAppComponent
import com.currymonster.fusion.modules.SessionComponent
import com.currymonster.fusion.presentation.activity.intro.IntroActivity
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class App : Application(), HasActivityInjector, HasSupportFragmentInjector,
    HasServiceInjector, HasBroadcastReceiverInjector {

    @Inject
    internal lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    internal lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    internal lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    @Inject
    internal lateinit var dispatchingReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    private lateinit var component: AppComponent

    override fun onCreate() {
        setUpDependencyInjection()

        context = applicationContext

        super.onCreate()
    }

    private fun setUpDependencyInjection() {
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        setSessionComponent()
    }

    private fun setSessionComponent() {
        sessionComponent = component.sessionComponent().build()
        sessionComponent.inject(this)
    }

    fun resetApp() {
        setSessionComponent()

        startActivity(
            Intent(this, IntroActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragmentInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> {
        return dispatchingReceiverInjector
    }

    companion object {

        lateinit var sessionComponent: SessionComponent
            internal set

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            internal set
    }
}