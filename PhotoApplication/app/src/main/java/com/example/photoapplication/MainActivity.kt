package com.example.photoapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var previousImage: ImageView
    private lateinit var recentImage: ImageView
    private lateinit var btnTakePhoto: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        previousImage=findViewById(R.id.imagePrevious)
        recentImage=findViewById(R.id.imageRecent)
        btnTakePhoto=findViewById(R.id.btnTakePhoto)
        previousImage.tag=0
        btnTakePhoto.setOnClickListener {
            if(isPermissionGranted){
                launchCamera()
            }else
            {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),111)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111){
            launchCamera()
        }
    }
    private fun launchCamera() {
        cameraLauncher.launch(Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE))
    }
    private val cameraLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val data = it.data?.extras?.get("data")
        data?.let {
            if(previousImage.tag==0){
                previousImage.tag=1
                previousImage.setImageBitmap(it as Bitmap)
            }else
            {
                recentImage.setImageBitmap(it as Bitmap)
            }
        }
    }

    private val isPermissionGranted:Boolean get() {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    
}