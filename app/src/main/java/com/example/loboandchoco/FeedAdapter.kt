package com.example.loboandchoco

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loboandchoco.databinding.ItemHomeFeedBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder

class FeedAdapter(private val context: MainActivity, private val dataList: ArrayList<Feed>):
    RecyclerView.Adapter<FeedAdapter.ViewHolder>(){

    class ViewHolder(private val binding:ItemHomeFeedBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item:Feed, position: Int){
            binding.feedTvUserId.text=item.userId
            var likeCount = StringBuilder()
            likeCount.append(context.resources.getString(R.string.home_like_count_before)).append(item.likeCount).append(context.resources.getString(R.string.home_like_count_after))
            binding.feeTvLikeCount.text = likeCount

            Glide.with(context).load(item.profileImageUrl).into(binding.feedIvProfile)
            Glide.with(context).load(item.imageUrl).into(binding.feedIvImage)

            binding.feedIvProfile.background = ShapeDrawable(OvalShape())
            binding.feedIvImage.clipToOutline = true

            var database : DatabaseReference
            val db = Firebase.database
            database = db.getReference("FeedList")


            //좋아요 버튼
            binding.feedBtnHeart.setOnClickListener{
                if(item.isLIke){
                    binding.feedBtnHeart.setImageResource(R.drawable.ic_heart_off)
                    item.isLIke=false
                    item.likeCount=item.likeCount-1
                    database.child((position+1).toString()).child("likeCount").setValue(item.likeCount)
                }else{
                    binding.feedBtnHeart.setImageResource(R.drawable.ic_heart_on)
                    item.isLIke=true
                    item.likeCount=item.likeCount+1
                    database.child((position+1).toString()).child("likeCount").setValue(item.likeCount)
                }
                likeCount= StringBuilder()
                likeCount.append(context.resources.getString(R.string.home_like_count_before))
                    .append(item.likeCount)
                    .append(context.resources.getString(R.string.home_like_count_after))
                binding.feeTvLikeCount.text = likeCount
            }

            //북마크 버튼
            binding.feedBtnBookmark.setOnClickListener{
                if(item.isBookmark){
                    binding.feedBtnBookmark.setImageResource(R.drawable.ic_bookmark_off)
                    item.isBookmark = false
                }else{
                    binding.feedBtnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                    item.isBookmark = true
                }
            }

            //댓글 버튼
            binding.feedBtnComment.setOnClickListener{
                Intent(context, CommentActivity::class.java).apply{
                }.run{context.startActivity(this)}
            }
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val binding = ItemHomeFeedBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        holder.bind(context, dataList[position],position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    }