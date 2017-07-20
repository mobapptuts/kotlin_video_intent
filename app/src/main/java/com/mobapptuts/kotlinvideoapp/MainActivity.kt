package com.mobapptuts.kotlinvideoapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    val REQUEST_VIDEO_APP_RESULT = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoButton.setOnClickListener {
            callVideoApp()
        }

        videoView.setOnCompletionListener {
            videoView.visibility = View.GONE
            videoButton.visibility = View.VISIBLE
        }
    }

    fun callVideoApp() {
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if(videoIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(videoIntent, REQUEST_VIDEO_APP_RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_VIDEO_APP_RESULT -> {
                if(resultCode == Activity.RESULT_OK &&
                        data != null) {
                    videoView.setVideoURI(data.data)
                    videoButton.visibility = View.GONE
                    videoView.visibility = View.VISIBLE
                    videoView.start()
                }
            }
            else -> {
                toast("Unrecognized request code $requestCode")
            }
        }
    }
}
