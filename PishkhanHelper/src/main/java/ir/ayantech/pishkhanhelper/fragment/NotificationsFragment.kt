package ir.ayantech.pishkhanhelper.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import ir.ayantech.pushsdk.storage.PushUser
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import ir.ayantech.whygoogle.helper.BooleanCallBack

abstract class NotificationsFragment<T : ViewBinding> : WhyGoogleFragment<T>() {

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            handleActivityResultLauncher(permissions)
        }

    open fun handleActivityResultLauncher(permissions: Map<String, @JvmSuppressWildcards Boolean>) {
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in Manifest.permission.POST_NOTIFICATIONS && it.value == false)
                permissionGranted = false
        }
        if (!permissionGranted) {
            onPermissionGranted(false)
        } else {
            onPermissionGranted(true)
            if (PushUser.pushNotificationToken.isNotEmpty())
                onNotificationEnabled()
            else
                onNotificationDisabled()
        }
    }

    open val onPermissionGranted: BooleanCallBack = {}

    abstract fun onNotificationDisabled()

    abstract fun onNotificationEnabled()

    fun onSwitchCheckedChanged(isChecked: Boolean) {
        if (isChecked) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activityResultLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
            }
        }
    }

    val isNotificationReady: Boolean
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                PushUser.pushNotificationToken.isNotEmpty() &&
                        ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
            } else {
                PushUser.pushNotificationToken.isNotEmpty()
            }
        }

    override fun onCreate() {
        super.onCreate()
        if (isNotificationReady)
            onNotificationEnabled()
        else
            onNotificationDisabled()
    }
}