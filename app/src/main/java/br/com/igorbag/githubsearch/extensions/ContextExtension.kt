package br.com.igorbag.githubsearch.extensions

import android.content.Context
import br.com.igorbag.githubsearch.ui.utils.Preferences

fun Context.getPreferenceData(): Preferences {
    return Preferences(this)
}