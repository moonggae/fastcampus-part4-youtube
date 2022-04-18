package fastcampus.aop.part3.aop_part4_chapter01.model

data class VideoModel(
    val description : String,
    val sources : String,
    val subtitle : String,
    val thumb : String,
    val title : String
){
    constructor() : this("", "", "", "", "")
}