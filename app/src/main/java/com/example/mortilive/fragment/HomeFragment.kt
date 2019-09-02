package com.example.mortilive.fragment

import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mortilive.MyPager
import com.example.mortilive.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        Glide.with(this).load("https://rickandmortyapi.com/api/character/avatar/1.jpeg")
            .override(image1.width,image1.height)
            .into(image1)
        Glide.with(this).load("https://rickandmortyapi.com/api/character/avatar/2.jpeg")
            .override(image2.width,image2.height)
            .into(image2)
        Glide.with(this).load("https://rickandmortyapi.com/api/character/avatar/3.jpeg")
            .override(image3.width,image3.height)
            .into(image3)

        val adapter  =  MyPager(context!!, listOf("https://images8.alphacoders.com/625/thumb-1920-625910.png","https://images2.alphacoders.com/642/thumb-1920-642540.png",
            "https://images4.alphacoders.com/936/thumb-1920-936813.png"),viewPage.height,
            viewPage.width)

        viewPage.adapter = adapter



        var scrollSum : Int = 0

        container.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            scrollSum += scrollY - oldScrollY
            if (scrollSum < 140) { // up
                println("---------------------------     $scrollY             $oldScrollY   $scrollSum")
                    (activity as? HomeFragmentInteractor)?.onScrollLisener(1)

            }
            if (scrollSum > 350) { // down
                println("---------------------------     $scrollY             $oldScrollY     $scrollSum")
                (activity as? HomeFragmentInteractor)?.onScrollLisener(0)

            }


        })

        bt_clear.setOnClickListener {

            et_search.text = null
        }



    }


interface HomeFragmentInteractor{



    fun onScrollLisener(move:Int)





}





}