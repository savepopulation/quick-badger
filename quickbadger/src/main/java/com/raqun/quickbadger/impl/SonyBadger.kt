package com.raqun.quickbadger.impl

import android.annotation.SuppressLint
import android.content.*
import android.net.Uri
import android.os.Looper
import com.raqun.quickbadger.Badger
import com.raqun.quickbadger.ext.isMainLooper
import com.raqun.quickbadger.ext.providerExists

class SonyBadger(compName: ComponentName, con: Context) : Badger {

    private var componentName: ComponentName = compName
    private var context: Context = con.applicationContext

    override fun showBadge(count: Int) {
        val contentValues = createContentValues(componentName, count)
        if (context.providerExists(SONY_HOME_PROVIDER_NAME)) {
            showBadgeWithContentProvider(context, contentValues)
        } else {
            showBadgeWithBroadcast(context, count)
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

    private fun showBadgeWithContentProvider(context: Context,
                                             contentValues: ContentValues) {
        if (Looper.myLooper().isMainLooper()) {
            showBadgeAsync(context, contentValues)
        } else {
            showBadgeSync(context, contentValues)
        }
    }

    @SuppressLint("HandlerLeak")
    private fun showBadgeAsync(context: Context, contentValues: ContentValues) {
        if (queryHandler == null) {
            queryHandler = object : AsyncQueryHandler(context.applicationContext.contentResolver) {}
        }

        queryHandler!!.startInsert(0, null, BADGE_CONTENT_URI, contentValues)
    }

    private fun showBadgeSync(context: Context, contentValues: ContentValues) =
            context.applicationContext.contentResolver.insert(BADGE_CONTENT_URI, contentValues)

    private fun showBadgeWithBroadcast(context: Context, count: Int) =
            context.sendBroadcast(Intent(INTENT_ACTION).apply {
                putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.className)
                putExtra(INTENT_EXTRA_PACKAGE_NAME, componentName.packageName)
                putExtra(INTENT_EXTRA_SHOW_MESSAGE, count > 0)
                putExtra(INTENT_EXTRA_MESSAGE, count.toString())
            })

    private fun createContentValues(componentName: ComponentName, count: Int) =
            ContentValues().apply {
                put(PROVIDER_COLUMNS_BADGE_COUNT, count)
                put(PROVIDER_COLUMNS_PACKAGE_NAME, componentName.packageName)
                put(PROVIDER_COLUMNS_ACTIVITY_NAME, componentName.className)
            }

    companion object {
        private const val INTENT_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE"
        private const val INTENT_EXTRA_PACKAGE_NAME = "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME"
        private const val INTENT_EXTRA_ACTIVITY_NAME = "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME"
        private const val INTENT_EXTRA_MESSAGE = "com.sonyericsson.home.intent.extra.badge.MESSAGE"
        private const val INTENT_EXTRA_SHOW_MESSAGE = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE"

        private const val PROVIDER_CONTENT_URI = "content://com.sonymobile.home.resourceprovider/badge"
        private const val PROVIDER_COLUMNS_BADGE_COUNT = "badge_count"
        private const val PROVIDER_COLUMNS_PACKAGE_NAME = "package_name"
        private const val PROVIDER_COLUMNS_ACTIVITY_NAME = "activity_name"
        private const val SONY_HOME_PROVIDER_NAME = "com.sonymobile.home.resourceprovider"

        private val BADGE_CONTENT_URI = Uri.parse(PROVIDER_CONTENT_URI)
        private val supportedLaunchers = listOf("com.sonyericsson.home", "com.sonymobile.home")
        private var queryHandler: AsyncQueryHandler? = null
    }
}