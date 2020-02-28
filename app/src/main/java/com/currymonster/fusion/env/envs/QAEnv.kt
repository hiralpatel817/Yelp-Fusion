package com.currymonster.fusion.env.envs

import com.currymonster.fusion.env.BuildEnv

open class QAEnv : Environment() {

    override val name: String
        get() = BuildEnv.qa.name

    override val apiBaseUrl: String
        get() = "https://qa.currymonster.com"

    override val availableEnvironments: Array<Class<out Environment>>
        get() = arrayOf(
            DevEnv::class.java,
            QAEnv::class.java,
            StgEnv::class.java
        )
}
