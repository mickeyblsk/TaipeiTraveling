package com.mickey.taipeitraveling.viewmodel;

import AttractionApi
import AttractionListAdapter
import AttractionResponse
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mickey.taipeitraveling.model.Attraction
import com.mickey.taipeitraveling.view.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    fun reloadList(language: String, context: Context, recyclerView: RecyclerView){

        // 建立 OkHttpClient
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        // 建立 Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/$language/") // 实际的 Swagger API 基本 URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // 可选，用于配置请求
            .build()

        // 建立 API 服務
        val service: AttractionApi = retrofit.create(AttractionApi::class.java)

        val call: Call<AttractionResponse> = service.getAttractions()

        // 發送需求
        call.enqueue(object : Callback<AttractionResponse> {
            override fun onResponse(
                call: Call<AttractionResponse>,
                response: Response<AttractionResponse>
            ) {
                if (response.isSuccessful) {
                    // 處理回傳檔案（attractions）
                    val attractionResponse: AttractionResponse = response.body()!!
                    val attractionList: List<Attraction> = attractionResponse.data

                    val adapter = AttractionListAdapter(attractionList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.invalidate()
                    Log.i("Swagger", "$attractionList")
                } else {
                    // 請求失敗
                    Log.i("Swagger", "${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<AttractionResponse>, t: Throwable) {
                // 網路異常
                Log.i("Swagger", "fail")
            }
        })
    }
}
