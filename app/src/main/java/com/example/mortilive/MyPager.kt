package com.example.mortilive

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import com.bumptech.glide.request.target.CustomTarget
import java.io.File
import java.io.FileOutputStream
import java.util.*
import android.webkit.PermissionRequest
import android.Manifest.permission
import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener


@Suppress("DEPRECATION")
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
            val cardViev : CardView = dialog.findViewById(R.id.СardView_3)

            cardViev.setOnClickListener {



                Glide.with(cardViev.context)
                    .asBitmap()
                    .load(imageList[position])
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?

                        ) {
                            saveImage(resource,position);
                        }

//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            imageView.setImageBitmap(resource)
//                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })
//               



            }
            }




        container.addView(view)

        return view
    }

    private fun saveImage(resource: Bitmap, position: Int): String? {
        var savedImagePath: String? = null
        val random = Random()
        val rand = random.nextInt(1000000)
        val imageFileName = "JPEG_$rand.jpg"

        val storageDir : File =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/MortiLive")
        var success = true

        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }

        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                resource.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            galleryAddPic(savedImagePath)

        }
        return savedImagePath;
    }

    private fun galleryAddPic(savedImagePath: String) {
       val  mediaScanIntent : Intent =  Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(savedImagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
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