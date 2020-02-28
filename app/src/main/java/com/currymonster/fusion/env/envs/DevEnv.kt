package com.currymonster.fusion.env.envs

import com.currymonster.fusion.env.BuildEnv
import com.currymonster.fusion.extensions.deobfuscate

open class DevEnv : Environment() {

    override val name: String
        get() = BuildEnv.debug.name

    override val apiBaseUrl: String
        get() = "https://api.yelp.com/v3/"

    override val apiKey: String
        get() = "OBF:1tul1kdn1vuj1jl51gf11rhl1rwv1q321vgf1l851jzz1i7o1pbk1lkt1f1e1pib1n5j1rip1i131rhr1dnr1mww1f1m1e351u3k1ked1vnk1hzn1m6k1tgx1thl1z7q1guz1xmq1i801gny1tgf19q11xtd18jl1v2d1abm1pjx1qvi1n551r591r5b1lmj1qc71jrg1oy01f8d1uvo1lj11fgg1p4j1qr11pdg1uvy1jd81r2x1rh11mrh1mkm1mk81mrl1rjd1r511jg21uu81pai1qoh1p571fge1lmp1uui1fa91oxi1jua1qav1lj71r2n1r2p1n6d1qy81pi91abg1v2118jh1xu519qd1ti71gm81i7y1xmk1gtf1z7c1th11thp1m5u1i231vni1ke11u1q1e251f2s1n0e1dp31rin1i0n1rhp1n5z1pjv1f301lkx1pce1i8a1jzz1l551vgf1q5s1rvz1rit1ggx1jmd1vur1ker1twh".deobfuscate()

    override val availableEnvironments: Array<Class<out Environment>>
        get() = arrayOf(
            DevEnv::class.java,
            QAEnv::class.java,
            StgEnv::class.java
        )
}
