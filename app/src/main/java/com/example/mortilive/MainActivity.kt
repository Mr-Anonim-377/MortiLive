package com.example.mortilive

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mortilive.fragment.HomeFragment
import com.example.mortilive.fragment.HomeFragment_three
import com.example.mortilive.fragment.HomeFragment_twoo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentInteractor {
    override fun onScrollLisener(move: Int) {
        if(move == 1) {
        animateNavigation(false)

        } else {
            animateNavigation(true)

        }
    }

    private var mTextMessage: TextView? = null
    private var navigation: BottomNavigationView? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layot, HomeFragment()).commit()

        initComponent()
    }

    private fun initComponent() {



        navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation!!.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.character -> {
                        mTextMessage?.text = item.title
                        navigation!!.setColor(R.color.teal_600)
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layot, HomeFragment()).commit()
                        return true
                    }
                    R.id.location -> {
                        mTextMessage?.text = item.title
                        navigation!!.setColor(R.color.pink_600)
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layot, HomeFragment_twoo()).commit()
                        return true
                    }
                    R.id.episode -> {
                        mTextMessage?.text = item.title
                        navigation!!.setColor(R.color.orange_600)
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layot, HomeFragment_three()).commit()
                        return true
                    }

                }
                return false
            }
        })


    }


    private var isNavigationHide = false
    public fun animateNavigation(hide: Boolean) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return
        isNavigationHide = hide
        val moveY = if (hide) 2 * navigation!!.height else 0
        navigation!!.animate().translationY(moveY.toFloat()).setStartDelay(200).setDuration(400).start()
    }





}



fun View.setColor(@ColorRes colorId: Int) {
    val color = ContextCompat.getColor(context, colorId)

    setBackgroundColor(color)
}
