package com.example.lib_base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @CreateDate: 2023/4/24 17:55
 * @Author: 青柠
 * @Description: 应用进入前台后台监听
 */
class ApplicationObserver : LifecycleEventObserver {
    private val TAG = "LifecycleOwner"

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d(TAG, "onCreate")
            }
            Lifecycle.Event.ON_START -> {
                Log.d(TAG, "onStart")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.d(TAG, "onResume")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d(TAG, "onPause")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d(TAG, "onStop")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.d(TAG, "onDestroy")
            }
            Lifecycle.Event.ON_ANY -> {
                Log.d(TAG, "onAny")
            }
        }
    }

}
