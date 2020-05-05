package com.currymonster.fusion.presentation.fragment.businesslist.module

import androidx.lifecycle.ViewModel
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelKey
import com.currymonster.fusion.presentation.fragment.businesslist.BusinessListFragment
import com.currymonster.fusion.presentation.fragment.businesslist.BusinessListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class Module {
    @Scope
    @ContributesAndroidInjector
    abstract fun provideInjector(): BusinessListFragment

    @Binds
    @IntoMap
    @ViewModelKey(BusinessListViewModel::class)
    internal abstract fun bindViewModel(viewModel: BusinessListViewModel): ViewModel
}