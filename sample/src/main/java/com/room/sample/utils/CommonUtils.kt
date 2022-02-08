package com.room.sample.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast

const val TAG = "log"

fun logcatD(str: String) {
    Log.d(TAG, str)
}

fun logcatE(str: String) {
    Log.e(TAG, str)
}

fun toast(activity: Activity, str: String) {
    activity.runOnUiThread {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
    }
}