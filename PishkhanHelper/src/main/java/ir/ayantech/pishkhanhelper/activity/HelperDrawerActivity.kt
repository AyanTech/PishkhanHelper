package ir.ayantech.pishkhanhelper.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCommonCallStatus
import ir.ayantech.ayannetworking.api.CallingState
import ir.ayantech.ayannetworking.ayanModel.FailureType
import ir.ayantech.pishkhanhelper.PishkhanHelper
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.app.HelperApplication
import ir.ayantech.pishkhanhelper.bottomSheet.ChooseLanguageBottomSheet
import ir.ayantech.pishkhanhelper.bottomSheet.ConfirmationBottomSheet
import ir.ayantech.pishkhanhelper.bottomSheet.ErrorBottomSheet
import ir.ayantech.pishkhanhelper.bottomSheet.WaiterBottomSheet
import ir.ayantech.pishkhanhelper.databinding.HelperDrawerActivityBinding
import ir.ayantech.pishkhanhelper.helper.hideKeyboard
import ir.ayantech.pishkhanhelper.locale.currentLocale
import ir.ayantech.pishkhanhelper.model.AppInfo
import ir.ayantech.pishkhanhelper.rate.showRatingIntent
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.pishkhanhelper.fragment.login.EnterPhoneNumberFragment
import ir.ayantech.versioncontrol.VersionControlCore
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.delayed
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.isVisible
import ir.ayantech.whygoogle.helper.makeVisible
import ir.ayantech.whygoogle.helper.trying
import java.util.Locale

abstract class HelperDrawerActivity : LocaleHelperActivity<HelperDrawerActivityBinding>() {

    override val binder: (LayoutInflater) -> HelperDrawerActivityBinding
        get() = HelperDrawerActivityBinding::inflate

    override val containerId: Int
        get() = R.id.fragmentContainerFl

    val corePishkhan24AyanApi: AyanApi
        get() = (applicationContext as HelperApplication).corePishkhan24AyanApi

    val servicesPishkhan24AyanApi: AyanApi
        get() = (applicationContext as HelperApplication).servicesPishkhan24AyanApi

    val appInfo: AppInfo
        get() = (applicationContext as HelperApplication).appInfo

    private var waiterBottomSheet: WaiterBottomSheet? = null

    abstract fun logoutPishkhan()

    private lateinit var navigationDrawer: ActionBarDrawerToggle

    open val ayanCommonCallingStatus = AyanCommonCallStatus {
        failure { failure ->
            when (failure.failureType) {
                FailureType.LOGIN_REQUIRED -> {
                    logoutPishkhan()
                    finish()
                    startActivity(Intent(this@HelperDrawerActivity, HelperDrawerActivity::class.java))
                }

                FailureType.CANCELED -> {
                    trying {
                        ErrorBottomSheet(
                            context = this@HelperDrawerActivity,
                            message = resources.getString(R.string.timeout_error_message),
                            retry = {
                                failure.reCallApi()
                            }
                        ).show()
                    }
                }

                else -> {
                    trying {
                        if (failure.failureCode.startsWith("GOP")) {
                            ErrorBottomSheet(
                                context = this@HelperDrawerActivity,
                                message = failure.failureMessage,
                                retry = {}
                            ).show()
                        } else {
                            ErrorBottomSheet(
                                context = this@HelperDrawerActivity,
                                message = failure.failureMessage,
                                retry = {
                                    failure.reCallApi()
                                }
                            ).show()
                        }
                    }
                }
            }
        }
        changeStatus { callingState ->
            when (callingState) {
                CallingState.NOT_USED -> waiterBottomSheet?.hide()
                CallingState.LOADING -> waiterBottomSheet?.show()
                CallingState.FAILED -> waiterBottomSheet?.hide()
                CallingState.SUCCESSFUL -> waiterBottomSheet?.hide()
            }
        }
    }

    abstract fun handleIntent(intent: Intent?)
    abstract fun initialize(initViewsCallback: SimpleCallBack)
    abstract val transactionHistoryFragment: WhyGoogleFragment<*>?
    abstract val loginFragment: EnterPhoneNumberFragment
    abstract val mainFragment: WhyGoogleFragment<*>
    abstract val handleBackIvVisibilityOnTopFragmentChanged: (whyGoogleFragment: WhyGoogleFragment<*>) -> Boolean
    abstract val handleToolbarRlVisibilityOnTopFragmentChanged: (whyGoogleFragment: WhyGoogleFragment<*>) -> Boolean
    abstract val getUserPhoneNumber: () -> String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCommonCallStatus()

        if (savedInstanceState != null)
            restart()

        handleAppLanguage()

        initWaiterBottomSheet()

        handleIntent(intent)
    }

    private fun initViews() {
        initNavigationDrawer()
        initToolbar()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun initWaiterBottomSheet() {
        waiterBottomSheet = WaiterBottomSheet(this) {
            corePishkhan24AyanApi.cancelCalls()
            servicesPishkhan24AyanApi.cancelCalls()
        }
    }

    private fun onNewLanguageSelected(language: String) {
        SavedData.appLanguage = language
        updateLocale(Locale(language))
        recreate()
    }

    private fun handleAppLanguage() {
        if (SavedData.appLanguage.isEmpty()) {
            ChooseLanguageBottomSheet(context = this, isFirstTimeOpen = true) { selectedLanguage ->
                onNewLanguageSelected(selectedLanguage)
            }.show()
        } else {
            initialize {
                initViews()
            }
        }

        if (SavedData.appLanguage.isNotEmpty() && (SavedData.appLanguage != this.currentLocale.language))
            updateLocale(Locale(SavedData.appLanguage))
    }

    private fun restart() {
        corePishkhan24AyanApi.cancelCalls()
        servicesPishkhan24AyanApi.cancelCalls()
        this.finish()
        this.startActivity(Intent(this, this.javaClass))
    }

    private fun setCommonCallStatus() {
        corePishkhan24AyanApi.commonCallStatus = ayanCommonCallingStatus
        servicesPishkhan24AyanApi.commonCallStatus = ayanCommonCallingStatus
    }

    private fun initToolbar() {
        accessViews {
            backIv.setOnClickListener {
                pop()
            }
            backIv.rotation = if (SavedData.appLanguage != "fa") 0f else 180f
        }
    }

    private fun initNavigationDrawer() {
        accessViews {
            menuIv.setOnClickListener {
                hideKeyboard()
                drawerLayout.openDrawer(GravityCompat.START)
            }

            navigationDrawer =
                ActionBarDrawerToggle(
                    this@HelperDrawerActivity,
                    drawerLayout,
                    R.string.open,
                    R.string.close
                )
            drawerLayout.addDrawerListener(navigationDrawer)
            navigationDrawer.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            phoneNumberTv.changeVisibility(show = getUserPhoneNumber().isNotEmpty())
            phoneNumberTv.text = getUserPhoneNumber()

            transactionHistoryRl.changeVisibility(transactionHistoryFragment.isNotNull())
            transactionHistoryRl.setOnClickListener {
                onMenuItemClicked {
                    transactionHistoryFragment?.let { fragment -> start(fragment) }
                }
            }

            rateAppRl.setOnClickListener {
                onMenuItemClicked {
                    showRatingIntent(appInfo.applicationId, appInfo.flavor)
                }
            }

            shareAppRl.setOnClickListener {
                onMenuItemClicked {
                    VersionControlCore.getInstance()
                        .setApplicationName(appInfo.applicationName)
                        .setCategoryName(appInfo.applicationCategory)
                        .setApplicationVersion(appInfo.applicationVersion)
                        .setApplicationType(appInfo.applicationType)
                    PishkhanHelper.shareApp(this@HelperDrawerActivity)
                }
            }

            appLanguageRl.changeVisibility(show = appInfo.flavor == "playstore")
            appLanguageRl.setOnClickListener {
                onMenuItemClicked {
                    ChooseLanguageBottomSheet(
                        context = this@HelperDrawerActivity,
                        isFirstTimeOpen = false
                    ) { selectedLanguage ->
                        onNewLanguageSelected(selectedLanguage)
                    }.show()
                }
            }

            val isUserLoggedIn = getUserPhoneNumber().isNotEmpty()
            loginLogoutRl.makeVisible()
            loginLogoutTv.text =
                getString(if (isUserLoggedIn) R.string.logout else R.string.login_to_account)
            loginLogoutRl.setOnClickListener {
                onMenuItemClicked {
                    if (isUserLoggedIn)
                        logout()
                    else
                        login()
                }
            }
            loginLogoutIv.setImageResource(if (isUserLoggedIn) R.drawable.helper_ic_logout else R.drawable.helper_ic_login)

            menuNavigationDrawer.setBackgroundResource(if (SavedData.appLanguage == "fa") R.drawable.helper_background_left_radius_16 else R.drawable.helper_background_right_radius_16)
            navigationDrawerTopLayoutCl.setBackgroundResource(if (SavedData.appLanguage == "fa") R.drawable.helper_background_top_left_radius_16 else R.drawable.helper_background_top_right_radius_16)

            drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                }

                override fun onDrawerOpened(drawerView: View) {
                    hideKeyboard()
                }

                override fun onDrawerClosed(drawerView: View) {
                    hideKeyboard()
                }

                override fun onDrawerStateChanged(newState: Int) {

                }

            })
        }
    }

    private fun onMenuItemClicked(callback: SimpleCallBack) {
        hideKeyboard()
        callback()
        closeDrawer()
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun login() {
        start(loginFragment)
    }

    private fun logout() {
        ConfirmationBottomSheet(
            context = this,
            description = R.string.logout_confirmation
        ) {
            logoutPishkhan()
        }.show()
    }

    override fun onTopFragmentChanged(whyGoogleFragment: WhyGoogleFragment<*>) {
        super.onTopFragmentChanged(whyGoogleFragment)

        binding.backIv.changeVisibility(show = handleBackIvVisibilityOnTopFragmentChanged(whyGoogleFragment))
        binding.menuIv.changeVisibility(show = !binding.backIv.isVisible())
        binding.toolbarRl.changeVisibility(show = handleToolbarRlVisibilityOnTopFragmentChanged(whyGoogleFragment))
        delayed {
            if (whyGoogleFragment == mainFragment)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }
}