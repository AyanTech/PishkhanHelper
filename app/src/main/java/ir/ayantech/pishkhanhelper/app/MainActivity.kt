package ir.ayantech.pishkhanhelper.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.ayanModel.LogLevel
import ir.ayantech.pishkhanhelper.PishkhanHelper
import ir.ayantech.pishkhanhelper.app.databinding.ActivityMainBinding
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.rate.showRatingIntent

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        changeThemeMode(AppCompatDelegate.MODE_NIGHT_NO)

        val ayanApi = AyanApi(
            context = this,
            getUserToken = { "72AD35BE03044CCDADE405CC7C7657A6" },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/Core.svc/v1/",
            timeout = 120,
            headers = hashMapOf("Accept-Language" to "fa"),
            logLevel = LogLevel.LOG_ALL
        )
        binding.apply {
            binding.filledButton.initButtonFilled(
                btnText = "استعلام"
            ) {
                Toast.makeText(this@MainActivity, "DONE :)", Toast.LENGTH_SHORT).show()
            }
            init.setOnClickListener {
                PishkhanHelper.initialize(application, ayanApi)
            }
            terms.setOnClickListener {
                PishkhanHelper.getConfigBusinessInfo(
                    onGetResult = {
                        Log.d(
                            "sdfkghbsdkfjb",
                            "onCreate: call= ${it.Support} \nterms= ${it.TermsAndConditions}"
                        )
                    }
                )
            }
            call.setOnClickListener {
                PishkhanHelper.openDialWithSupportPhoneNumber(application)
            }
            share.setOnClickListener {
//                PishkhanHelper.shareApp(application)
            }
            rate.setOnClickListener {
                showRatingIntent(applicationId = "ir.ayantech.topup", "cafebazaar")
            }
        }
    }
}