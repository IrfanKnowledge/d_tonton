package com.irfan.core.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarHelper {
    fun showSnackBarSingleEvent(view: View, event: SingleEvent<Unit>, message: String) {
        event.getContentIfNotHandled()?.let {
            Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun showSnackBarSingleEvent(view: View, message: SingleEvent<String>) {
        message.getContentIfNotHandled()?.let {
            Snackbar.make(
                view,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}