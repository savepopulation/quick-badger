package com.raqun.quickbadger.ext

import android.content.Context
import android.content.Intent
import com.raqun.quickbadger.Badger
import com.raqun.quickbadger.impl.DefaultBadger

fun Badger.isBadgerValid(homePackage: String) =
        !getSupportedLaunchers().isEmpty() && getSupportedLaunchers().contains(homePackage)

fun Badger.isSupported(context: Context) = context.canResolveBroadcast(Intent(DefaultBadger.INTENT_ACTION))