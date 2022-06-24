package xyz.teamgravity.todo.core.util

import android.content.Context
import android.content.Intent
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.constant.ConnectionConst

object Helper {

    /**
     * Shares the app text with other apps
     */
    fun shareApp(context: Context) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareAppText(context))
            context.startActivity(Intent.createChooser(this, context.getString(R.string.choose)))
        }
    }

    /**
     * Returns the share app text
     */
    private fun shareAppText(context: Context): String {
        return context.getString(R.string.share_app, "${ConnectionConst.PLAY_STORE_DETAIL_PAGE}${context.packageName}")
    }
}