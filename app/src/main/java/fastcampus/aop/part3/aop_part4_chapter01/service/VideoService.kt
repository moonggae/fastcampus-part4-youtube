package fastcampus.aop.part3.aop_part4_chapter01.service

import fastcampus.aop.part3.aop_part4_chapter01.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {
    @GET("/v3/8d628a15-0b58-43ca-9861-5fee21926d23")
    fun listVideos() : Call<VideoDto>
}