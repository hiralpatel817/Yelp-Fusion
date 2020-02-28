package com.currymonster.fusion.module

/*
 * Required env variables for network setup
 */

interface EnvProvider {
    val apiBaseUrl: String
    val apiKey: String
}
