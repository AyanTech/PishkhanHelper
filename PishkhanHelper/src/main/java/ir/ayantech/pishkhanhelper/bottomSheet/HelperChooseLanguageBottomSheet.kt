package ir.ayantech.pishkhanhelper.bottomSheet

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.databinding.BottomSheetChooseLanguageBinding
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.whygoogle.helper.StringCallBack

open class HelperChooseLanguageBottomSheet(
    context: Context,
    private val isFirstTimeOpen: Boolean,
    private val onLanguageSelected: StringCallBack
): HelperBaseBottomSheet<BottomSheetChooseLanguageBinding>(context) {

    override val title: String
        get() = getString(R.string.choose_language)

    override val binder: (LayoutInflater) -> BottomSheetChooseLanguageBinding
        get() = BottomSheetChooseLanguageBinding::inflate

    override val isCancelable: Boolean
        get() = !isFirstTimeOpen

    override val hasCloseOption: Boolean
        get() = !isFirstTimeOpen

    private var selectedLanguage = SavedData.appLanguage

    override fun onCreate() {
        super.onCreate()

        binding.languageContainer.check(
            when (SavedData.appLanguage) {

                "en" -> binding.englishLang.id

                "fa" -> binding.persianLang.id

                else -> -1
            }
        )

        binding.languageContainer.setOnCheckedChangeListener { _, checkedId ->
            selectedLanguage = getAppLangCodeFromSelectedId(id = checkedId)
        }

        binding.confirmLanguageBtnComponent.initButtonFilled(
            btnText = getString(R.string.submit)
        ) {
            if (selectedLanguage.isEmpty()) {
                Toast.makeText(context, getString(R.string.please_choose_app_language), Toast.LENGTH_SHORT).show()
            } else {
                dismiss()
                onLanguageSelected(selectedLanguage)
            }
        }
    }

    private fun getAppLangCodeFromSelectedId(id: Int): String = when (id) {
        binding.englishLang.id -> {
            "en"
        }
        binding.persianLang.id -> {
            "fa"
        }
        else -> {
            ""
        }
    }
}