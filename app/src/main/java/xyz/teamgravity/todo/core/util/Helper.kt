package xyz.teamgravity.todo.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.constant.ConnectionConst
import xyz.teamgravity.todo.core.extension.safelyStartActivity

object Helper {

    private fun getShareAppText(context: Context): String {
        return context.getString(R.string.share_app, getPlayStorePageUrl(context))
    }

    private fun getPlayStorePageUrl(context: Context): String {
        return "${ConnectionConst.PLAY_STORE_DETAIL_PAGE}${context.packageName}"
    }

    private fun getGravityPlayStorePageUrl(): String {
        return "https://play.google.com/store/apps/${ConnectionConst.GRAVITY}"
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Shares the app text with other apps.
     */
    fun shareApp(context: Context) {
        ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setText(getShareAppText(context))
            .setChooserTitle(context.getString(R.string.choose))
            .startChooser()
    }


    /**
     * Navigates the user to rate the app.
     */
    fun rateApp(context: Context) {
        context.safelyStartActivity(
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}")),
            onError = {
                context.safelyStartActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getPlayStorePageUrl(context))))
            }
        )
    }

    /**
     * Navigates the user to show source code on Github.
     */
    fun viewSourceCode(context: Context) {
        context.safelyStartActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ConnectionConst.GITHUB_SOURCE_CODE)))
    }

    /**
     * Navigates the user to connect us via Telegram.
     */
    fun connectViaTelegram(context: Context) {
        context.safelyStartActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ConnectionConst.SUPPORT_TELEGRAM)))
    }

    /**
     * Navigates the user to connect us via Email.
     */
    fun connectViaEmail(context: Context) {
        ShareCompat.IntentBuilder(context)
            .setType("message/rfc822")
            .addEmailTo(ConnectionConst.SUPPORT_MAIL)
            .setSubject(context.getString(R.string.improvement))
            .setChooserTitle(context.getString(R.string.choose))
            .startChooser()
    }

    /**
     * Navigates the user to Gravity Play Store page.
     */
    fun viewGravityPage(context: Context) {
        context.safelyStartActivity(
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://${ConnectionConst.GRAVITY}")),
            onError = {
                context.safelyStartActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(getGravityPlayStorePageUrl()))
                )
            }
        )
    }
}