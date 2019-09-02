package com.example.mortilive

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mortilive.Network.App
import com.example.mortilive.fragment.HomeFragment
import com.example.mortilive.fragment.HomeFragment_three
import com.example.mortilive.fragment.HomeFragment_twoo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.lang.Exception
import java.lang.reflect.Executable


class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentInteractor {
    var page = 1
    val thread = Thread{
        try{
        val response = App.getApi().getData("bash", 50).execute().body()
        print(response)
        print(response)}
        catch (e:Exception ){
        e.printStackTrace()
        }

    }
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

        thread.start()




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


        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    // permission is granted, open the camera
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // check for permanent denial of permission
                    if (response.isPermanentlyDenied) {
                        // navigate user to app settings
                    }
                }


            }).check()


    }


    private var isNavigationHide = false
    public fun animateNavigation(hide: Boolean) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return
        isNavigationHide = hide
        val moveY = if (hide) 2 * navigation!!.height else 0
        navigation!!.animate().translationY(moveY.toFloat()).setStartDelay(300).setDuration(400).start()
    }





}



fun View.setColor(@ColorRes colorId: Int) {
    val color = ContextCompat.getColor(context, colorId)

    setBackgroundColor(color)
}



