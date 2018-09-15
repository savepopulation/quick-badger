package com.raqun.quickbadger.ext

import android.content.Context
import android.content.Intent

fun Context.canResolveBroadcast(intent: Intent) = packageManager.queryBroadcastReceivers(intent, 0).isNotEmpty()

fun Context.providerExists(providerName:String) = packageManager.resolveContentProvider(providerName,0) != null