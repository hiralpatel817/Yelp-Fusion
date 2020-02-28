package com.currymonster.fusion

import android.content.Context
import android.content.res.Resources
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.io.File

open class ContextStubProvider {

    val context = newInstance()

    companion object {

        private val stringsById: Map<Int, String> by lazy {
            val stringsById = mutableMapOf<Int, String>()
            val regex = "^.*<string name=\"(.*)\">(.*)</string>.*$".toRegex()
            File("../app/src/main/res/values/strings.xml").bufferedReader().use {
                it.forEachLine { line ->
                    val matcher = regex.matchEntire(line)
                    if (matcher != null) {
                        val name = matcher.groups[1]!!.value
                        if (!name.contains('.')) {
                            val id = R.string::class.java.getField(name).getInt(null)
                            val value = matcher.groups[2]!!.value
                            stringsById[id] = value
                        }
                    }
                }
            }
            stringsById
        }

        private val resources = mock<Resources>(stubOnly = true)
            .also { r ->
                Mockito.doAnswer {
                    stringsById[it.arguments[0] as Int]
                }.whenever(r).getString(ArgumentMatchers.anyInt())
            }
            .also { r ->
                Mockito.doAnswer {
                    stringsById[it.arguments[0] as Int]?.format(*it.arguments.drop(1).toTypedArray())
                }.whenever(r).getString(ArgumentMatchers.anyInt(), ArgumentMatchers.anyVararg())
            }

        fun newInstance(): Context {
            return mock<Context>(stubOnly = true)
                .also { c ->
                    whenever(c.resources).thenReturn(resources)
                    Mockito.doAnswer {
                        resources.getString(it.arguments[0] as Int)
                    }.whenever(c).getString(ArgumentMatchers.anyInt())
                }
                .also { c ->
                    whenever(c.resources).thenReturn(resources)
                    Mockito.doAnswer {
                        resources.getString(
                            it.arguments[0] as Int,
                            *it.arguments.drop(1).toTypedArray()
                        )
                    }.whenever(c).getString(ArgumentMatchers.anyInt(), ArgumentMatchers.anyVararg())
                }
        }
    }
}
