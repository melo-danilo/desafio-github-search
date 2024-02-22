package br.com.igorbag.githubsearch.extensions

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBarRed(message: String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackBar.setBackgroundTint(Color.RED)
    snackBar.show()
}

fun View.showSnackBar(message: String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackBar.show()
}