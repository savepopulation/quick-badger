package com.raqun.quickbadgerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.raqun.quickbadger.QuickBadger
import com.raqun.quickbadger.impl.SamsungBadger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Quick Badger Usage
         * Shows a Badge with number 10
         */

        QuickBadger.provideBadger(this)?.showBadge(10)

        /*
         * Dismiss Badge Example
         */

        QuickBadger.provideBadger(this)?.dismissBadge()

        /*
         * Custom Badger Example
         */
        QuickBadger.withBadgers(SamsungBadger::class,
                MyCustomBadger::class)
                .provideBadger(this)?.showBadge(5)
    }
}
