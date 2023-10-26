package com.mickey.taipeitraveling.viewmodel

import ImagePagerAdapter
import android.content.Context
import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager

class InformationViewModel : ViewModel() {
    private lateinit var viewPager: ViewPager
    private lateinit var imageUrlList: ArrayList<String>
    private var currentPage = 0
    private val handler = Handler()

    fun setAutoPlayImageViewPager(context: Context, viewPager : ViewPager, imageUrlList: ArrayList<String>){
        this.viewPager = viewPager
        this.imageUrlList = imageUrlList
        viewPager.adapter = ImagePagerAdapter(context, imageUrlList)
        // 定時循環
        startAutoPlay()
    }

    private fun startAutoPlay() {
        handler.postDelayed(runnable, 3000) // 每3秒切换一次图片
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (currentPage == imageUrlList.size) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage, true)
            currentPage++
            handler.postDelayed(this, 3000) // 每3秒換一張圖
        }
    }
}