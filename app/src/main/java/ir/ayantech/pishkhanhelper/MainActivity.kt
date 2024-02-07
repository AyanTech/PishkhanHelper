package ir.ayantech.pishkhanhelper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import ir.ayantech.pishkhanhelper.themeMode.changeThemeMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        changeThemeMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
}