import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mickey.taipeitraveling.R
import com.mickey.taipeitraveling.model.Attraction
import com.mickey.taipeitraveling.view.InformationActivity
import com.squareup.picasso.Picasso

class AttractionListAdapter(private val attractions: List<Attraction>) :
    RecyclerView.Adapter<AttractionListAdapter.AttractionViewHolder>() {
    class AttractionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val introductionTextView: TextView = itemView.findViewById(R.id.introduction_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_attraction, parent, false)
        return AttractionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        val attraction = attractions[position]
        if (attraction.images.isNotEmpty()) {
            val imageUrl = attraction.images[0].src

            // 將 attraction.images[0] 放進 ImageView 中
            // 使用 Picasso 載入圖片
            Picasso.get().load(imageUrl).into(holder.imageView)
        }
        // 使用 attraction.name 和 attraction.introduction 放進 TextView 中
        holder.nameTextView.text = attraction.name
        holder.introductionTextView.text = attraction.introduction

        // 加入 onclick
        holder.itemView.setOnClickListener {
            //將圖片轉成StringList
            val imageUrlList: ArrayList<String> = ArrayList<String>()
            if (attraction.images.isNotEmpty()) {
                for (element in attraction.images)
                    imageUrlList.add(element.src)
            } else {
                imageUrlList.add("no image")
            }

            // 建立往 InformationActivity 的intent
            val intent = Intent(holder.itemView.context, InformationActivity::class.java)

            // 將需求的資訊放入 bundle
            val bundle: Bundle = Bundle()
            bundle.putString("name", attraction.name)
            bundle.putString("introduction", attraction.introduction)
            bundle.putString("modified", attraction.modified)
            bundle.putString("address", attraction.address)
            bundle.putString("website", attraction.url)
            bundle.putStringArrayList("imageUrlList", imageUrlList)

            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = attractions.size
}