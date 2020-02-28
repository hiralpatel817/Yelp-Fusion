package com.currymonster.fusion.presentation.activity.intro.module

import androidx.lifecycle.ViewModel
import com.currymonster.fusion.presentation.activity.intro.IntroActivity
import com.currymonster.fusion.presentation.activity.intro.IntroViewModel
import com.currymonster.fusion.presentation.base.viewmodelfactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class Module {
    @Scope
    @ContributesAndroidInjector
    abstract fun provideInjector(): IntroActivity

    @Binds
    @IntoMap
    @ViewModelKey(IntroViewModel::class)
    internal abstract fun bindViewModel(viewModel: IntroViewModel): ViewModel
}