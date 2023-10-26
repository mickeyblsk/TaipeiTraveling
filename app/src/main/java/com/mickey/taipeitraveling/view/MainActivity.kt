package com.mickey.taipeitraveling.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mickey.taipeitraveling.R
import com.mickey.taipeitraveling.databinding.ActivityMainBinding
import com.mickey.taipeitraveling.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedLanguage: String = languageSetting()

        supportActionBar?.apply {
            setDisplayShowCustomEnabled(true)
            title = getString(R.string.app_name)
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.reloadList(selectedLanguage, this, binding.attractionRecyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_translate_button -> {
                // 開啟語系選擇 Dialog
                openLanguageDialog()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun languageSetting(): String {
        // 從 SharedPreference 取得語系並修改
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val selectedLanguage: String =
            sharedPreferences.getString("user_language", "en")!! // 默认语言为英语

        val locale: Locale = Locale(selectedLanguage)
        Locale.setDefault(locale)

        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return selectedLanguage
    }

    private fun openLanguageDialog() {
        // 建立選擇語言 Dialog
        val languages: Array<String> = arrayOf(
            "繁體中文",
            "简体中文",
            "English",
            "日本語",
            "한국어",
            "Ortografía del español",
            "Bahasa Indonesia",
            "อักษรไทย",
            "Tiếng Việt"
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Language")
        builder.setSingleChoiceItems(languages, -1) { dialog, which ->
            // 語言清單
            val selectedLanguage = when (which) {
                0 -> "zh-tw"  // 正體中文
                1 -> "zh-cn"  // 簡體中文
                2 -> "en"  // 英文
                3 -> "ja"  // 日文
                4 -> "ko"  // 韓文
                5 -> "es"  // 西班牙文
                6 -> "id"  // 印尼文
                7 -> "th"  // 泰文
                8 -> "vi"  // 越南文
                else -> "zh-rTW"  // 默認
            }

            // 將選到的語言存入 SharedPreferences
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user_language", selectedLanguage)
            editor.apply()

            // 關閉 Dialog
            dialog.dismiss()

            // 重啟 App
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // 顯示 Dialog
        builder.create().show()
    }
}