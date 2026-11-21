package com.example.diaryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryAdapter (
    private var diaryList: List<Diary>
): RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    //1個分のレイアウト保持
    class DiaryViewHolder(view: View):
        RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tv_title)
            val tvDate: TextView = view.findViewById(R.id.tv_date)
            val tvContent: TextView = view.findViewById(R.id.tv_content)
            val ivDiaryImage: ImageView = view.findViewById(R.id.iv_diary_image)
        }

    //レイアウト生成
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType:Int
    ): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary,parent,false)
        return DiaryViewHolder(view)
    }

    //データをレイアウトに格納
    override fun onBindViewHolder(
        holder: DiaryViewHolder,
        position: Int
    ){
        val diary = diaryList[position]
        holder.tvTitle.text = diary.title
        holder.tvDate.text = diary.date
        holder.tvContent.text = diary.content

        val imageRes = when(diary.imageId) {
            1 -> R.drawable.red
            2 -> R.drawable.blue
            3 -> R.drawable.green
            4 -> R.drawable.yellow
            5 -> R.drawable.purple
            else -> R.drawable.red
        }
        holder.ivDiaryImage.setImageResource(imageRes)
    }

    //日記の件数返す
    override fun getItemCount(): Int {
        return diaryList.size
    }
}