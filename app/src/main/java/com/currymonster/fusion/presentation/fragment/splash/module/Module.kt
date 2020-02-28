package com.currymonster.fusion.presentation.fragment.splash.module

import androidx.lifecycle.ViewModel
import com.currymonster.fusion.presentation.activity.intro.module.Scope
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelKey
import com.currymonster.fusion.presentation.fragment.splash.SplashFragment
import com.currymonster.fusion.presentation.fragment.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class Module {
    @Scope
    @ContributesAndroidInjector
    abstract fun provideInjector(): SplashFragment

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindViewModel(viewModel: SplashViewModel): ViewModel
}