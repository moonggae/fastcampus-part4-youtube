package fastcampus.aop.part3.aop_part4_chapter01.adapter

import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop.part3.aop_part4_chapter01.model.VideoModel

class VideoAdapter : ListAdapter<VideoModel, VideoAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(videoModel: VideoModel) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<VideoModel>(){
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem.sources == newItem.sources
            }

        }
    }


}