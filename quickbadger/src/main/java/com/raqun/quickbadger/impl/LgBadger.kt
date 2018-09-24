package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.Context
import com.raqun.easybadger.Badger

class LgBadger(compName: ComponentName, con: Context) : DefaultBadger(compName, con) {

    override fun getSupportedLaunchers(): List<String> = supportedLaunchers

    companion object {
        private val supportedLaunchers = listOf("com.lge.launcher", "com.lge.launcher2")
    }
}