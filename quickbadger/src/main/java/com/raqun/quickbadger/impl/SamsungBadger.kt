package com.raqun.quickbadger.impl

import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.raqun.quickbadger.util.Util


class SamsungBadger(compName: ComponentName? = null,
                    con: Context? = null) : DefaultBadger(compName, con) {

    override fun showBadge(count: Int) {
        if (Util.hasLollipop()) {
            super.showBadge(count)
        } else {
            val contentUri = Uri.parse(CONTENT_URI_STRING)
            var cursor: Cursor? = null
            try {
                if (context == null) return
                cursor = context!!.contentResolver.query(contentUri,
                        CONTENT_PROJECTION,
                        "package=?",
                        arrayOf(componentName?.packageName),
                        null)

                cursor?.let {
                    var isEntryActivityExists = false
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(0)
                        if (componentName == null) return
                        val contentValues = getContentValues(componentName!!, count, false)
                        context!!.contentResolver.update(contentUri, contentValues, "_id=?", arrayOf(id.toString()))
                        if (componentName!!.className == cursor.getString(cursor.getColumnIndex(CLASS_COLUMN))) {
                            isEntryActivityExists = true
                        }
                    }

                    if (!isEntryActivityExists) {
                        val contentValues = getContentValues(componentName!!, count, true)
                        context!!.contentResolver.insert(contentUri, contentValues)
                    }
                }
            } catch (e: Exception) {
                // ignored
            } finally {
                if (cursor != null && !cursor.isClosed) {
                    cursor.close()
                }
            }
        }
    }

    override fun getSupportedLaunchers(): List<String> = supportedLaunchers

    private fun getContentValues(componentName: ComponentName, badgeCount: Int, isInsert: Boolean): ContentValues =
            ContentValues().apply {
                if (isInsert) {
                    put(KEY_PACKAGE, componentName.packageName)
                    put(KEY_CLASS, componentName.className)
                }
                put(KEY_BADGE_COUNT, badgeCount)
            }

    companion object {
        private val supportedLaunchers = listOf("com.sec.android.app.launcher", "com.sec.android.app.twlauncher")
        private val CONTENT_PROJECTION = arrayOf("_id", "class")

        private const val CONTENT_URI_STRING = "content://com.sec.badge/apps?notify=true"
        private const val KEY_PACKAGE = "package"
        private const val KEY_CLASS = "class"
        private const val KEY_BADGE_COUNT = "badgecount"
        private const val CLASS_COLUMN = "class"
    }
}