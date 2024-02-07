package ir.ayantech.pishkhanhelper.scanner

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.journeyapps.barcodescanner.ScanIntentResult
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.scanner.CustomScannerOptions.setUpScannerOptions
import java.util.concurrent.atomic.AtomicInteger

/**
 * For Managing The Scanner
 * for more information please refer to
 * ### https://developer.android.com/training/basics/intents/result#separate
 *
 *
 * ## important note =
 * When using the ActivityResultRegistry APIs,
 * it's strongly recommended to use the APIs that take a LifecycleOwner,
 * as the LifecycleOwner automatically removes your registered launcher when the Lifecycle is destroyed.
 * However, in cases where a LifecycleOwner is not available,
 * each ActivityResultLauncher class allows you to manually call unregister() as an alternative.
 * */
class ScannerLifecycleObserver(private val registry: ActivityResultRegistry) :
    DefaultLifecycleObserver {

    private val mNextLocalRequestCode = AtomicInteger()

    lateinit var getContent: ActivityResultLauncher<Intent>

    var handleScanResult: ((String, String) -> Unit)? = null

    override fun onCreate(owner: LifecycleOwner) {
        getContent =
            registry.register(
                "scanner_request" + mNextLocalRequestCode.getAndIncrement(), owner,
                ActivityResultContracts.StartActivityForResult()
            ) { result -> getScannerResult(scannerResult = result) }
    }

    private fun getScannerResult(scannerResult: ActivityResult) {
        ScanIntentResult.parseActivityResult(
            scannerResult.resultCode,
            scannerResult.data
        )?.contents?.let { scanResult ->
            try {
                BillRecognizer.handleResult(scanResult) { billId, paymentId ->
                    handleScanResult?.invoke(billId, paymentId)
                }
            } catch (exception: Exception) {
                Log.e("SCANNER_TAG", "scannerSetup: $exception")
            }
        }
    }

    /**
     * Use This Function To Launch Scanner
     * @param [scanTitle] [context] */
    fun scannerLauncher(scanTitle: Int = R.string.barcodeScan, context: Context) {
        if (::getContent.isInitialized) {
            getContent.launch(
                setUpScannerOptions(
                    scanTitle = context.resources.getString(
                        scanTitle
                    )
                )
                    .createScanIntent(context)
            )
        } else {
            Log.d("SCANNER_TAG", "An Issue Happen For Launching The Scanner")
        }
    }
}
