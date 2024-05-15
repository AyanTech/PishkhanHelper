package ir.ayantech.pishkhanhelper.bottomSheet

import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.components.initButtonOutlined
import ir.ayantech.pishkhanhelper.databinding.BottomSheetRatingBinding
import ir.ayantech.pishkhanhelper.rate.showRatingIntent
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.whygoogle.helper.BooleanCallBack

class MarketRatingBottomSheet(
    private val activity: AppCompatActivity,
    private val applicationId: String,
    private val marketName: String,
    private val onOptionsClicked: BooleanCallBack?
) : BaseBottomSheet<BottomSheetRatingBinding>(activity) {

    override val binder: (LayoutInflater) -> BottomSheetRatingBinding
        get() = BottomSheetRatingBinding::inflate

    override val title: String
        get() = "امتیاز به برنامه"

    override fun onCreate() {
        super.onCreate()

        binding.apply {
            yesBtn.initButtonFilled(btnText = getString(R.string.rate)) {
                activity.showRatingIntent(
                    applicationId = applicationId,
                    marketName = marketName
                ) {
                    Toast.makeText(context, getString(R.string.thanks_for_your_feedback), Toast.LENGTH_SHORT).show()
                }
                onButtonClicked(hasRated = true)
            }
            laterBtn.initButtonOutlined(context = activity, btnText = getString(R.string.later)) {
                onButtonClicked(hasRated = false)
            }
        }
    }

    private fun onButtonClicked(hasRated: Boolean) {
        SavedData.userHasRated = hasRated
        onOptionsClicked?.invoke(hasRated)
        dismiss()
    }
}