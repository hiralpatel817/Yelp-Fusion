package com.currymonster.fusion.presentation.activity.home.module

import androidx.lifecycle.ViewModel
import com.currymonster.fusion.presentation.activity.home.HomeActivity
import com.currymonster.fusion.presentation.activity.home.HomeViewModel
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class Module {
    @Scope
    @ContributesAndroidInjector
    abstract fun provideInjector(): HomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}