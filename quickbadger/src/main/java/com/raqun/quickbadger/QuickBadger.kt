package com.raqun.quickbadger

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.raqun.quickbadger.ext.isBadgerValid
import com.raqun.quickbadger.ext.isSupported
import com.raqun.quickbadger.impl.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object QuickBadger {

    private const val TAG = "QuickBadger"
    private const val RESOLVER_SUFFIX = "resolver"
    private const val MAX_PROVIDE_ATTEMPT_COUNT = 1

    private val supportedBadgers: MutableList<KClass<out Badger>> = mutableListOf(SamsungBadger::class,
            LgBadger::class,
            HuaweiBadger::class,
            SonyBadger::class)

    private var badger: Badger? = null

    @Volatile
    private var provideAttemptCount = 0

    @Synchronized
    fun provideBadger(context: Context): Badger? {

        /**
         * Returns if there's already a valid badger provided
         */
        if (badger != null) {
            return badger
        }

        /**
         * Check if we reached to max provide attemt count
         */
        if (provideAttemptCount >= MAX_PROVIDE_ATTEMPT_COUNT) {
            return badger
        }

        /**
         * Increase attempt count
         */
        provideAttemptCount++

        /**
         * Check if there's a launcher intent for package.
         */
        val launcherIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        if (launcherIntent == null) {
            Log.e(TAG, "Could not find and intent for " + context.packageName)
            return badger
        }

        /**
         * Check ResolveInfo and Type
         */
        val componentName = launcherIntent.component
        val resolveInfo = context.packageManager.resolveActivity(Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }, PackageManager.MATCH_DEFAULT_ONLY)

        if (resolveInfo == null || resolveInfo.activityInfo.name.toLowerCase().contains(RESOLVER_SUFFIX)) {
            Log.e(TAG, "ResolveInfo is null or has unexpected type")
            return badger
        }

        /**
         * Tries to instantiate a Badger from supported device badgers.
         */
        val currentHomePackage = resolveInfo.activityInfo.packageName
        var launcherBadger: Badger? = null
        for (supportedBadger in supportedBadgers) {
            try {
                launcherBadger = supportedBadger.createInstance()
                launcherBadger.initComponentName(componentName)
                launcherBadger.initContext(context)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            if (launcherBadger != null && launcherBadger.isBadgerValid(currentHomePackage)) {
                badger = launcherBadger
                break
            }
        }

        /**
         * If cannot instantiate check if default badger is supported and return it.
         */
        if (badger == null) {
            launcherBadger = DefaultBadger(componentName, context)
            if (launcherBadger.isSupported(context)) {
                badger = launcherBadger
            }
        }

        return badger
    }

    @Synchronized
    fun withCustomBadgers(vararg customBadgers: KClass<out Badger>) {
        supportedBadgers.addAll(customBadgers)
    }
}