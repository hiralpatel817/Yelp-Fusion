package com.currymonster.fusion.presentation.fragment.home.module

import androidx.lifecycle.ViewModel
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelKey
import com.currymonster.fusion.presentation.fragment.home.HomeFragment
import com.currymonster.fusion.presentation.fragment.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class Module {
    @Scope
    @ContributesAndroidInjector
    abstract fun provideInjector(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}