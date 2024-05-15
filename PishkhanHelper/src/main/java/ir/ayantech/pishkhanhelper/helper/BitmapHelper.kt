package ir.ayantech.pishkhanhelper.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.share(context: Activity, extraText: String? = null) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.putExtra(
        Intent.EXTRA_STREAM,
        this.createAndPassUri(context, "receipt.png")
    )
    extraText?.let {
        intent.putExtra(Intent.EXTRA_TEXT, it)
    }
    intent.type = "image/png"
    context.startActivity(Intent.createChooser(intent, "به اشتراک‌گذاری از طریق"))
}

fun Bitmap.createAndPassUri(context: Context, fileName: String): Uri {
    val cachePath = File(context.externalCacheDir, "my_images/")
    cachePath.mkdirs()

    //create png file
    val file = File(cachePath, fileName)
    val fileOutputStream: FileOutputStream
    try {
        fileOutputStream = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    //---Share File---//
    //get file uri
    return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName.toString() + ".provider",
        file
    )
}