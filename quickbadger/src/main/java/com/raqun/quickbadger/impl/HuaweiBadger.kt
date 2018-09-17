package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import com.raqun.easybadger.Badger
import android.os.Bundle


class HuaweiBadger(private val componentName: ComponentName) : Badger {

    override fun showBadge(context: Context, count: Int) {
        val localBundle = Bundle()
        localBundle.putString(BUNDLE_PACKAGE, componentName.packageName)
        localBundle.putString(BUNDLE_CLASS, componentName.className)
        localBundle.putInt(BUNDLE_BADGE_NUMBER, count)
        try {
            context.contentResolver.call(Uri.parse(HUAWEI_LAUNCHER_URI), HUAWEI_BADGE_ACTION, null, localBundle)
        } catch (e: Exception) {
            //ignored
        }
    }

    override fun dismissBadge(context: Context) = showBadge(context, 0)

    override fun getSupportedLaunchers(): List<String> = listOf("com.huawei.android.launcher")

    companion object {
        private const val HUAWEI_LAUNCHER_URI = "content://com.huawei.android.launcher.settings/badge/"
        private const val HUAWEI_BADGE_ACTION = "change_badge"
        private const val BUNDLE_PACKAGE = "package"
        private const val BUNDLE_CLASS = "class"
        private const val BUNDLE_BADGE_NUMBER = "badgenumber"
    }
}