package com.example.mortilive

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import android.graphics.BitmapFactory
import android.R.attr.bitmap
import java.net.URL
import android.graphics.Bitmap
import android.os.Environment
import java.nio.file.Files.exists
import android.system.Os.mkdir
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import java.io.FileOutputStream
import java.util.*


class MyPager(private val context: Context, private val imageList: List<String>, private val height:Int, private val width:Int) : PagerAdapter(){

    class SimpleRunnable: Runnable {
        public override fun run() {
            println("${Thread.currentThread()} has run.")
        }
    }


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

        imageView.setOnClickListener {

            val dialog = Dialog(imageView.context)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
            dialog.setContentView(R.layout.dialog_image)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)

            val image : ImageView = dialog.findViewById(R.id.dialogImage)

            Glide.with(imageView.context).load(imageList[position])
                .override(width,height)
                .into(image)
            dialog.show()
            val cardViev : CardView = dialog.findViewById(R.id.cardView)

            cardViev.setOnClickListener {
                val thread = Thread {
                    try {
                        val url = URL(imageList[position])
                        val conn = url.openConnection()

                        val generator = Random()
                        var n = 10000
                        n = generator.nextInt(n)
                        val image = BitmapFactory.decodeStream(conn.getInputStream())
                        saveToSdCard(image,"image$n")

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                    println("${Thread.currentThread()} has run.")
                }
                thread.start()



            }
            }




        container.addView(view)

        return view
    }

    fun saveToSdCard(bitmap: Bitmap, filename: String): String? {

        var stored: String? = null


        val folder =
            File(context.filesDir, "Test")//the dot makes this directory hidden to the user


        folder.mkdir()


        val file = File(folder.absoluteFile, "$filename.jpg")

        if (file.exists())

            return "Файл с таким именени уже есть"
        try {


            val out = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)


            out.flush()
            out.close()
            stored = "Файл сохранен"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stored
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