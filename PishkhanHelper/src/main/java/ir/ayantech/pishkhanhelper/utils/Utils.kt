package ir.ayantech.pishkhanhelper.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun openDial(context: Context?, phoneNumber: String) {
    if (context == null) return
    var finalNumber = phoneNumber
    if (finalNumber.contains("#")) {
        finalNumber = finalNumber.replace("#", "")
        finalNumber += Uri.encode("#")
    }
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = "tel:$finalNumber".toUri()
    context.startActivity(intent)
}