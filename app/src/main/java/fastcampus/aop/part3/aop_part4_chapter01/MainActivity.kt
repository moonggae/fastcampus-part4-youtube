package fastcampus.aop.part3.aop_part4_chapter01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fastcampus.aop.part3.aop_part4_chapter01.adapter.VideoAdapter
import fastcampus.aop.part3.aop_part4_chapter01.databinding.ActivityMainBinding
import fastcampus.aop.part3.aop_part4_chapter01.dto.VideoDto
import fastcampus.aop.part3.aop_part4_chapter01.service.VideoService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "로그"

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var videoAdapter : VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, PlayerFragment())
            .commit()

        videoAdapter = VideoAdapter { sources, title ->
            supportFragmentManager.fragments.find { it is PlayerFragment }?.let {
                (it as PlayerFragment).play(sources, title)
            }
        }

        binding.mainRecyclerView.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        getVideoList()
    }

    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also {
            it.listVideos().enqueue(object : Callback<VideoDto>{
                override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                    if(response.isSuccessful.not()){
                        Log.d(TAG, "MainActivity onResponse() - called : response fail")
                        return
                    }

                    response.body()?.let { videoDto ->
                        Log.d(TAG, "MainActivity onResponse() - called : it.videos.toString() : ${videoDto.videos.toString()}")
                        videoAdapter.submitList(videoDto.videos)
                    }


                }

                override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                    Log.d(TAG, "MainActivity onFailure() - called : ${t.message}")
                }

            })
        }
    }
}