package com.raqun.easybadger

import android.content.ComponentName
import android.content.Context

interface Badger {
    fun showBadge(count: Int)

    fun dismissBadge()

    fun getSupportedLaunchers(): List<String>

    fun initComponentName(componentName: ComponentName)

    fun initContext(context: Context)
}