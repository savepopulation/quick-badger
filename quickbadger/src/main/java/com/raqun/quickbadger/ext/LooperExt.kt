package com.raqun.quickbadger.ext

import android.os.Looper

fun Looper.isMainLooper() = this == Looper.getMainLooper()