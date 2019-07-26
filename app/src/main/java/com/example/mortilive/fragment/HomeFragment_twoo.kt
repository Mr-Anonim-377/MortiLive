package com.example.mortilive.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.mortilive.MyPager
import com.example.mortilive.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment_twoo : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home_twoo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter  =  MyPager(context!!, listOf("https://images8.alphacoders.com/625/thumb-1920-625910.png","https://images2.alphacoders.com/642/thumb-1920-642540.png",
            "https://images4.alphacoders.com/936/thumb-1920-936813.png"),viewPage.height,
            viewPage.width)

        viewPage.adapter = adapter



        container.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < oldScrollY) { // up
                (activity as? HomeFragmentInteractor)?.onScrollLisener(1)

            }
            if (scrollY > oldScrollY) { // down
                (activity as? HomeFragmentInteractor)?.onScrollLisener(0)
            }
        })





    }


interface HomeFragmentInteractor{



    fun onScrollLisener(move:Int)





}





}