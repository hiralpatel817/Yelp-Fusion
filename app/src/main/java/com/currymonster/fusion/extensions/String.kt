package com.currymonster.fusion.extensions

import java.nio.charset.StandardCharsets

fun String.deobfuscate() =
    if (this.startsWith("OBF:")) {
        val s = this.substring(4)
        val b = ByteArray(s.length / 2)
        var l = 0
        var i = 0
        while (i < s.length) {
            if (s[i] == 'U') {
                i++
                val x = s.substring(i, i + 4)
                val i0 = Integer.parseInt(x, 36)
                val bx = (i0 shr 8).toByte()
                b[l++] = bx
            } else {
                val x = s.substring(i, i + 4)
                val i0 = Integer.parseInt(x, 36)
                val i1 = i0 / 256
                val i2 = i0 % 256
                val bx = ((i1 + i2 - 254) / 2).toByte()
                b[l++] = bx
            }
            i += 4
        }

        String(b, 0, l, StandardCharsets.UTF_8)
    } else {
        this
    }