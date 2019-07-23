package com.example.mortilive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MyPager(private val context: Context, private val imageList: List<String>, private val height:Int, private val width:Int) : PagerAdapter() {
    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.viev_page, null)
        val imageView : ImageView = view.findViewById(R.id.imageView)
        val viewPage =
        Glide.with(context).load(imageList[position])
            .override(width,height)
            .into(imageView)



        container.addView(view)
        return view
    }

    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    /*
    Returns the count of the total pages
    */
    override fun getCount(): Int {
        return imageList.size
    }

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

}