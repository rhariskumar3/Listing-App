package com.haris.listingapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.haris.listingapp.App
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
object Toast {
    private lateinit var context: Context

    operator fun invoke(activity: App) {
        context = activity
    }

    fun success(message: String, length: Int = Toast.LENGTH_SHORT) =
        show(State.SUCCESS, message, length)

    fun success(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) =
        show(State.SUCCESS, message, length)

    fun error(message: String, length: Int = Toast.LENGTH_SHORT) =
        show(State.ERROR, message, length)

    fun error(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) =
        show(State.ERROR, message, length)

    fun info(message: String, length: Int = Toast.LENGTH_SHORT) = show(State.INFO, message, length)

    fun info(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) =
        show(State.INFO, message, length)

    private fun show(state: State, @StringRes message: Int, length: Int = Toast.LENGTH_SHORT) =
        show(state, context.getString(message), length)

    private fun show(state: State, message: String, length: Int = Toast.LENGTH_SHORT) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val msg = message.ifEmpty { "No message found" }
                when (state) {
                    State.INFO -> Toasty.info(context, msg, length)
                    State.ERROR -> Toasty.error(context, msg, length)
                    State.SUCCESS -> Toasty.success(context, msg, length)
                }.show()
            }
        }
    }

    enum class State {
        INFO,
        ERROR,
        SUCCESS
    }
}

