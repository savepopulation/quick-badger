package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.Context
import com.raqun.easybadger.Badger

class LgBadger(compName: ComponentName) : DefaultBadger(compName) {

    override fun getSupportedLaunchers(): List<String> = supportedLaunchers

    companion object {
        private val supportedLaunchers = listOf("com.lge.launcher", "com.lge.launcher2")
    }
}