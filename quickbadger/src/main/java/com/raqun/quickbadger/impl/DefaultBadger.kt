package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.raqun.easybadger.Badger
import com.raqun.quickbadger.ext.canResolveBroadcast
import java.util.*

/**
 * A default Badger impl for unsupported launchers
 */
open class DefaultBadger constructor(cName: ComponentName) : Badger {

    private var componentName: ComponentName = cName

    override fun showBadge(context: Context, count: Int) {
        val badgeIntent = Intent(INTENT_ACTION).apply {
            putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.className)
            putExtra(INTENT_EXTRA_PACKAGENAME, componentName.packageName)
            putExtra(INTENT_EXTRA_BADGE_COUNT, count);
        }

        if (context.canResolveBroadcast(badgeIntent)) {
            context.sendBroadcast(badgeIntent)
        }
    }

    override fun dismissBadge(context: Context) {
        showBadge(context, 0)
    }

    override fun getSupportedLaunchers(): List<String> = ArrayList()

    override fun setComponentName(componentName: ComponentName) {
        this.componentName = componentName
    }

    companion object {
        private const val INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE"
        private const val INTENT_EXTRA_BADGE_COUNT = "badge_count"
        private const val INTENT_EXTRA_PACKAGENAME = "badge_count_package_name"
        private const val INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name"
    }
}