package xyz.teamgravity.todo.core.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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

    /**
     * Navigates the user to rate the app
     */
    fun rateApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("${ConnectionConst.PLAY_STORE_DETAIL_PAGE}$appPackageName")))
        }
    }

    /**
     * Navigates the user to show source code on Github
     */
    fun viewSourceCode(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ConnectionConst.GITHUB_SOURCE_CODE)))
    }
}