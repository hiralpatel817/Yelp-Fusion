package com.currymonster.fusion.env.envs

import com.currymonster.fusion.env.BuildEnv

open class ProdEnv : Environment() {

    override val name: String
        get() = BuildEnv.release.name

    override val apiBaseUrl: String
        get() = "https://currymonster.com"
}
