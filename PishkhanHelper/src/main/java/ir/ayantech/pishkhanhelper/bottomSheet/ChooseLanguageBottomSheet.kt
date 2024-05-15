package ir.ayantech.pishkhanhelper.bottomSheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.databinding.BottomSheetChooseLanguageBinding
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.whygoogle.helper.StringCallBack

class ChooseLanguageBottomSheet(
    context: Context,
    private val isFirstTimeOpen: Boolean,
    private val onLanguageSelected: StringCallBack
): BaseBottomSheet<BottomSheetChooseLanguageBinding>(context) {

    override val title: String
        get() = getString(R.string.choose_language)

    override val binder: (LayoutInflater) -> BottomSheetChooseLanguageBinding
        get() = BottomSheetChooseLanguageBinding::inflate

    override val isCancelable: Boolean
        get() = !isFirstTimeOpen

    override val hasCloseOption: Boolean
        get() = !isFirstTimeOpen

    private var selectedLanguage = "fa"

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
            onLanguageSelected(selectedLanguage)
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
            "fa"
        }
    }
}