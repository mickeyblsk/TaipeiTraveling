import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.mickey.taipeitraveling.R
import com.squareup.picasso.Picasso

class ImagePagerAdapter(private val context: Context, private val imageUrlList: List<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageUrlList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_image, container, false)
        val imageView = view.findViewById<ImageView>(R.id.image_view)

        Picasso.get()
            .load(imageUrlList[position])
            .into(imageView)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
