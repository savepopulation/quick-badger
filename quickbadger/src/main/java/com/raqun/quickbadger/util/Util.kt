package com.raqun.quickbadger.util

import android.os.Build

class Util private constructor() {

    companion object {
        fun hasLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }
}