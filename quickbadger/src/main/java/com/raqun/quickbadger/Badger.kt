package com.raqun.easybadger

import android.content.ComponentName
import android.content.Context

interface Badger {
    fun showBadge(context: Context, count: Int)

    fun dismissBadge(context: Context)

    fun getSupportedLaunchers(): List<String>

    fun initComponentName(componentName: ComponentName)

    fun initContext(context: Context)
}