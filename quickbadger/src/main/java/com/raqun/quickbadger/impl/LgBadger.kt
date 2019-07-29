package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.Context

class LgBadger(compName: ComponentName? = null,
               con: Context? = null) : DefaultBadger(compName, con) {

    override fun getSupportedLaunchers(): List<String> = supportedLaunchers

    companion object {
        private val supportedLaunchers = listOf("com.lge.launcher", "com.lge.launcher2")
    }
}