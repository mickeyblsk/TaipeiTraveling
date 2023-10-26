package com.mickey.taipeitraveling.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.mickey.taipeitraveling.databinding.ActivityInformationBinding
import com.mickey.taipeitraveling.viewmodel.InformationViewModel

class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding
    private lateinit var informationViewModel: InformationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformationBinding.inflate(layoutInflater)
        informationViewModel = ViewModelProvider(this).get(InformationViewModel::class.java)
        setContentView(binding.root)

        // 接收 bundle
        val bundle: Bundle = intent.extras!!

        val name: String = bundle.getString("name")!!
        val introduction: String = bundle.getString("introduction")!!
        val address: String = bundle.getString("address")!!
        val modified: String = bundle.getString("modified")!!
        val website: String = bundle.getString("website")!!
        val imageUrlList: ArrayList<String> = bundle.getStringArrayList("imageUrlList")!!

        // 設定可使用 ActionBar 返回
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = name
        }

        // 如果有圖片，就設定圖片輪播
        if (imageUrlList[0] != "no image")
            informationViewModel.setAutoPlayImageViewPager(
                this,
                binding.imageViewPager,
                imageUrlList
            )

        putInformation(name, introduction, address, modified, website)
        Log.i(
            "InformationActivity",
            "$name,$introduction,$modified,$address,$imageUrlList"
        )
    }

    private fun putInformation(
        name: String,
        introduction: String,
        address: String,
        modified: String,
        website: String
    ) {
        binding.nameTextView.text = name
        binding.introductionTextView.text = introduction
        binding.addressTextView.text = address
        binding.modifiedTextView.text = modified
        binding.websiteTextView.text = website

        // 使用Linkify来使链接可点击
        Linkify.addLinks(binding.websiteTextView, Linkify.WEB_URLS)
        binding.websiteTextView.setOnClickListener { view ->
            val text = (view as TextView).text.toString()
            val uri = Uri.parse(text)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}