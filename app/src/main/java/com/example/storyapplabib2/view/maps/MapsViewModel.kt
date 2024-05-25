package com.example.storyapplabib2.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapplabib2.data.model.UserPreference
import com.example.storyapplabib2.data.remote.network.ApiConfig
import com.example.storyapplabib2.data.remote.response.ItemStory
import com.example.storyapplabib2.data.remote.response.StoriesResponse
import com.example.storyapplabib2.view.maps.MapsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel() : ViewModel()  {
 private val _listStory = MutableLiveData<List<ItemStory>>()
 val listStory: LiveData<List<ItemStory>> = _listStory

 private val _loadingScreen = MutableLiveData<Boolean>()
 val loadingScreen: LiveData<Boolean> = _loadingScreen

  fun getStories() {
   _loadingScreen.value = true
   val cilent = ApiConfig.getApiService().getStories()
   cilent.enqueue(object : Callback<StoriesResponse> {
    override fun onResponse(
     call: Call<StoriesResponse>,
     response: Response<StoriesResponse>
    ) {
     _loadingScreen.value = false
     if (response.isSuccessful) {
      val responseBody = response.body()
      if (responseBody != null && !responseBody.error) {
       _listStory.value = responseBody.listStory ?: emptyList()
       Log.d(MapsViewModel.TAG, responseBody.message.toString())
      }
     } else {
      Log.e(MapsViewModel.TAG, "onFailure: ${response.message()}")
     }
    }
 
    override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
     _loadingScreen.value = false
     Log.e(MapsViewModel.TAG, "onFailure2: Gagal")
    }
   })
  }

 companion object {
  private const val TAG = "MapsViewModel"
 }
}