package com.currymonster.fusion.modules

import com.currymonster.fusion.App
import com.currymonster.fusion.module.NetModule
import com.currymonster.fusion.module.RepositoryModule
import com.currymonster.fusion.module.SessionScope
import com.currymonster.fusion.presentation.base.BaseViewModel
import dagger.Subcomponent

@SessionScope
@Subcomponent(
    modules = [
        RepositoryModule::class,
        NetModule::class,
        com.currymonster.fusion.presentation.activity.home.module.Module::class,
        com.currymonster.fusion.presentation.fragment.businesslist.module.Module::class
    ]
)
interface SessionComponent {

    fun inject(application: App)

    fun inject(viewModel: BaseViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun netModule(module: NetModule): Builder
        fun repositoryModule(module: RepositoryModule): Builder
        fun build(): SessionComponent
    }
}