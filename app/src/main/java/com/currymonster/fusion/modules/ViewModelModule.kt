package com.currymonster.fusion.modules

import androidx.lifecycle.ViewModelProvider
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}