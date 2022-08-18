package dev.davlatov.github_clone_app.utils

import android.util.Log
import dev.davlatov.github_clone_app.data.remote.ApiService.Companion.IS_TEST_SERVER

object Logger {
    fun d(tag: String, message: String) = run { if (IS_TEST_SERVER) Log.d(tag, message) }
    fun e(tag: String, message: String) = run { if (IS_TEST_SERVER) Log.e(tag, message) }
    fun i(tag: String, message: String) = run { if (IS_TEST_SERVER) Log.i(tag, message) }
    fun v(tag: String, message: String) = run { if (IS_TEST_SERVER) Log.v(tag, message) }
    fun w(tag: String, message: String) = run { if (IS_TEST_SERVER) Log.w(tag, message) }
}
