package ir.ayantech.pishkhanhelper.scanner

import com.journeyapps.barcodescanner.ScanOptions

/**
 * Create Singleton Scanner Option That Uses Over The Application Lifecycle*/
object CustomScannerOptions {
    fun setUpScannerOptions(scanTitle: String): ScanOptions = ScanOptions().apply {
        setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        setPrompt(scanTitle)
        setCameraId(0)
        setBeepEnabled(false)
        setBarcodeImageEnabled(false)
        setOrientationLocked(false)
    }
}