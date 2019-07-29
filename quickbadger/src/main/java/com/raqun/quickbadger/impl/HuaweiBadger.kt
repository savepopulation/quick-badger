package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.raqun.quickbadger.Badger


class HuaweiBadger(compName: ComponentName? = null,
                   con: Context? = null) : Badger {

    private var componentName = compName
        get() = componentName

    private var context = con?.applicationContext
        get() = context

    override fun showBadge(count: Int) {
        val localBundle = Bundle().apply {
            putString(BUNDLE_PACKAGE, componentName?.packageName)
            putString(BUNDLE_CLASS, componentName?.className)
            putInt(BUNDLE_BADGE_NUMBER, count)
        }

        try {
            context?.contentResolver?.call(Uri.parse(HUAWEI_LAUNCHER_URI), HUAWEI_BADGE_ACTION, null, localBundle)
        } catch (e: Exception) {
            //ignored
        }
    }

    override fun dismissBadge() = showBadge(0)

    override fun getSupportedLaunchers(): List<String> = supportedLaunchers

    override fun initComponentName(componentName: ComponentName) {
        this.componentName = componentName
    }

    override fun initContext(context: Context) {
        this.context = context.applicationContext
    }

    companion object {
        private val supportedLaunchers = listOf("com.huawei.android.launcher")
        private const val HUAWEI_LAUNCHER_URI = "content://com.huawei.android.launcher.settings/badge/"
        private const val HUAWEI_BADGE_ACTION = "change_badge"
        private const val BUNDLE_PACKAGE = "package"
        private const val BUNDLE_CLASS = "class"
        private const val BUNDLE_BADGE_NUMBER = "badgenumber"
    }
}