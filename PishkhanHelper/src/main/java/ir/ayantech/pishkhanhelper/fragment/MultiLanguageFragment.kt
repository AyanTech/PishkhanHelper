package ir.ayantech.pishkhanhelper.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhanhelper.activity.LocaleHelperActivity
import ir.ayantech.pishkhanhelper.databinding.FragmentMultiLanguageBinding
import ir.ayantech.pishkhanhelper.locale.Locales
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import java.util.*

open class MultiLanguageFragment : WhyGoogleFragment<FragmentMultiLanguageBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMultiLanguageBinding
        get() = FragmentMultiLanguageBinding::inflate

    override fun onCreate() {
        super.onCreate()
        accessViews {
            binding.languageContainer.check(
                when (SavedData.appLanguage) {
                    Locales.English.language -> binding.englishLang.id
                    Locales.Persian.language -> binding.persianLang.id
                    else -> -1
                }
            )

            binding.languageContainer.setOnCheckedChangeListener { _, checkedId ->
                changeAppLanguage(languageCode = getAppLangCodeFromSelectedId(id = checkedId))
            }
        }
    }

    private fun getAppLangCodeFromSelectedId(id: Int): String = when (id) {
        binding.englishLang.id -> {
            Locales.English.language
        }
        binding.persianLang.id -> {
            Locales.Persian.language
        }
        else -> {
            Locales.Persian.language
        }
    }

    private fun changeAppLanguage(languageCode: String) {
        SavedData.appLanguage = languageCode
        (requireActivity() as? LocaleHelperActivity<*>)?.updateLocale(Locale(languageCode))
        onAppLanguageChanged()
    }

    open fun onAppLanguageChanged() {
        requireActivity().recreate()
    }
}