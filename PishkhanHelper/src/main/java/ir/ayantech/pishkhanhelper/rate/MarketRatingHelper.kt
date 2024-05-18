package ir.ayantech.pishkhanhelper.rate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.annotation.StringDef
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import ir.ayantech.pishkhanhelper.bottomSheet.HelperMarketRatingBottomSheet
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.whygoogle.helper.BooleanCallBack
import ir.ayantech.whygoogle.helper.openUrl
import ir.ayantech.whygoogle.helper.trying

@StringDef("cafebazaar", "myket", "playstore", "galaxystore")
@Retention(AnnotationRetention.SOURCE)
annotation class MarketName

fun AppCompatActivity.showRatingIntent(applicationId: String, @MarketName marketName: String, onFailed: (() -> Unit)? = null) {
    when(marketName.lowercase()) {
        "cafebazaar" -> showCafeBazaarIntent(applicationId) { onFailed?.invoke() }
        "myket" -> showMyketIntent(applicationId) { onFailed?.invoke() }
        "galaxystore" -> showGalaxyStoreIntent(applicationId) { onFailed?.invoke() }
        "playstore" -> showPlayStoreIntent(applicationId) { onFailed?.invoke() }
//        "xiaomistore"
        else -> {
            onFailed?.invoke()
        }
    }
}

fun AppCompatActivity.showRatingBottomSheet(applicationId: String, marketName: String, callback: BooleanCallBack? = null) {
    if (!SavedData.userHasRated) {
        trying {
            HelperMarketRatingBottomSheet(
                activity = this,
                applicationId = applicationId,
                marketName = marketName,
                onOptionsClicked = callback
            ).show()
        }
    } else {
        callback?.invoke(true)
    }
}

fun AppCompatActivity.showCafeBazaarIntent(
    applicationId: String,
    onFailed: (() -> Unit)? = null
) {
    try {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = Uri.parse("bazaar://details?id=$applicationId")
        intent.setPackage("com.farsitel.bazaar")
        startActivity(intent)
    } catch (e: Exception) {
        Log.e("MarketIntent", "startMarketIntent: Cafebazaar => ${e.printStackTrace()}")
        onFailed?.invoke()
    }
}

fun AppCompatActivity.showMyketIntent(
    applicationId: String,
    onFailed: (() -> Unit)? = null
) {
    try {
        val url = "myket://comment?id=$applicationId"
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        startActivity(intent)
    } catch (e: Exception) {
        Log.e("MarketIntent", "startMarketIntent: Myket => ${e.printStackTrace()}")
        onFailed?.invoke()
    }
}

fun AppCompatActivity.showGalaxyStoreIntent(
    applicationId: String? = null,
    onFailed: (() -> Unit)? = null
) {
    try {
        val ai: ApplicationInfo = packageManager.getApplicationInfo(
            "com.sec.android.app.samsungapps",
            PackageManager.GET_META_DATA
        )
        val inappReviewVersion =
            ai.metaData.getInt("com.sec.android.app.samsungapps.review.inappReview", 0)
        if (inappReviewVersion > 0) {
            val intent = Intent("com.sec.android.app.samsungapps.REQUEST_INAPP_REVIEW_AUTHORITY")
            intent.setPackage("com.sec.android.app.samsungapps")
            intent.putExtra("callerPackage", applicationId ?: packageName) // applicationId : package name

            sendBroadcast(intent)
            val filter = IntentFilter()
            filter.addAction("com.sec.android.app.samsungapps.RESPONSE_INAPP_REVIEW_AUTHORITY")
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
//                    val hasAuthority = intent?.getBooleanExtra("hasAuthority", false)

                    val deeplinkUri = intent?.getStringExtra("deeplinkUri")
                    val newIntent = Intent()
                    newIntent.data = Uri.parse(deeplinkUri)

                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    startActivity(newIntent)
                }
            }
        } else {
            onFailed?.invoke()
        }
    } catch (e: Exception) {
        Log.e("MarketIntent", "startMarketIntent: GalaxyStore => ${e.printStackTrace()}")
        onFailed?.invoke()
    }
}

enum class CommentingReference {
    SETTINGS, HISTORY
}

fun AppCompatActivity.showPlayStoreIntent(
    applicationId: String,
    commentingReference: CommentingReference = CommentingReference.SETTINGS,
    onFailed: (() -> Unit)? = null
) {
    try {
        if (commentingReference == CommentingReference.SETTINGS)
            "https://play.google.com/store/apps/details?id=${applicationId}".openUrl(this)
        else
            rateGooglePlay(this)
    } catch (e: Exception) {
        Log.e("MarketIntent", "startMarketIntent: PlayStore => ${e.printStackTrace()}")
        onFailed?.invoke()
    }
}

private fun rateGooglePlay(mainActivity: AppCompatActivity) {
    val manager = ReviewManagerFactory.create(mainActivity)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        google(mainActivity, task, manager)
    }
}

private fun google(mainActivity: AppCompatActivity, task: Task<ReviewInfo>, manager: ReviewManager) {
    if (task.isSuccessful) {
        // We got the ReviewInfo object
        val reviewInfo = task.result
        val flow = manager.launchReviewFlow(mainActivity, reviewInfo)
        flow.addOnCompleteListener { _ ->
        }
    } else {
        "https://play.google.com/store/apps/details?id=${mainActivity.packageName}".openUrl(mainActivity)
    }
}