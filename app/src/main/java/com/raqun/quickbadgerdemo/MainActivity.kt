package com.raqun.quickbadgerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.raqun.quickbadger.QuickBadger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Quick Badger Usage
         * Shows a Badge with number 10
         */

        QuickBadger.provideBadger(this)?.showBadge(10)

        /**
         * Dismiss Badge Example
         */

        QuickBadger.provideBadger(this)?.dismissBadge()
    }
}
