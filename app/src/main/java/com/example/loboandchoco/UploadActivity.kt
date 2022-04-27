package com.example.loboandchoco

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.loboandchoco.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    private val PICK_STORAGE = 1001
    private val PICK_CAMERA = 1000
    private val PERMISSIONS_REQUEST = 100
    private var imageUrl: String = ""

    private val Permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickImage()
        checkPermissions(Permissions)

        binding.uploadIvImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, PICK_CAMERA)
            }
        }
        binding.uploadBtnBack.setOnClickListener {
            onBackPressed()
        }
        binding.uploadBtnComplete.setOnClickListener {
            finish()
            //Firebase
        }

    }


    private fun pickImage() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/* video/*"

        startActivityForResult(intent, PICK_STORAGE)
    }

    private fun checkPermissions(permissions: Array<String>): Boolean {
        val permissionList: MutableList<String> = mutableListOf()
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this, permissionList.toTypedArray(), PERMISSIONS_REQUEST
            )
            return false
        }
        return true

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_STORAGE) {
                val pickedImage: Uri? = data?.data
                if (pickedImage != null) {
                    imageUrl = pickedImage.toString()
                }
                Glide.with(this).load(imageUrl).into(binding.uploadIvImage)
            }
            if (requestCode == PICK_CAMERA) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                val pickedImage: Uri? = data.data
                if (pickedImage != null) {
                    imageUrl = pickedImage.toString()
                }
                binding.uploadIvImage.setImageBitmap(imageBitmap)
            }
        }
    }
}