package com.currymonster.fusion.env.envs

import com.currymonster.fusion.env.BuildEnv

open class StgEnv : Environment() {

    override val name: String
        get() = BuildEnv.staging.name

    override val apiBaseUrl: String
        get() = "https://stg.currymonster.com"
}
