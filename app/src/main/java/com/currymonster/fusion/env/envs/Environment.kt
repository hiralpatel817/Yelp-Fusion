package com.currymonster.fusion.env.envs

import com.currymonster.fusion.BuildConfig
import com.currymonster.fusion.env.BuildEnv
import com.currymonster.fusion.extensions.deobfuscate

abstract class Environment {

    val displayName: String = this.javaClass.simpleName

    abstract val name: String

    abstract val apiBaseUrl: String

    /**
     * Keys
     *
     * Ex)
     * open val testKey: String
     *      get() = "OBF:key".deobfuscate()
     */

    open val apiKey: String
        get() = "OBF:key".deobfuscate()

    /**
     * Env Switcher
     */

    open val availableEnvironments: Array<Class<out Environment>>
        get() = arrayOf()

    companion object {
        @JvmStatic
        val default: Environment
            get() = get(BuildConfig.BUILD_TYPE)

        @JvmStatic
        fun get(env: String): Environment = get(BuildEnv.valueOf(env))

        @JvmStatic
        fun get(buildEnv: BuildEnv) =
            when (buildEnv) {
                BuildEnv.debug -> DevEnv()
                BuildEnv.qa -> QAEnv()
                BuildEnv.staging -> StgEnv()
                BuildEnv.release -> ProdEnv()
            }
    }
}