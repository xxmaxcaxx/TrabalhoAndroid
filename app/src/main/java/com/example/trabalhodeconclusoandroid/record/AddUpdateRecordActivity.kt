package com.example.trabalhodeconclusoandroid.record

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.dbhelper.MyDbHelper
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_update_record.*

class AddUpdateRecordActivity : AppCompatActivity() {

    //permission constants
    private val CAMERA_REQUEST_CODE = 100;
    private val STORAGE_REQUEST_CODE = 101;
    //image pick constants
    private val IMAGE_PICK_CAMERA_CODE = 102;
    private val IMAGE_PICK_GALLERY_CODE = 103;
    //arrays of permissions
    private lateinit var cameraPermissions:Array<String> //camera and storage
    private lateinit var storagePermissions:Array<String> //only storage

    private var imageUri:Uri? = null
    private var name:String? = ""
    private var id:String? = ""
    private var phone:String? = ""
    private var email:String? = ""
    private var dob:String? = ""
    private var bio:String? = ""
    private var addTime:String? = ""
    private var updateTime:String? = ""

    private var isEditMode = false

    //actionbar
    private var actionBar:ActionBar? = null;

    lateinit var dbHelper: MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_record)

        actionBar = supportActionBar

        actionBar!!.title = getString(R.string.AddRecord)

        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        val intent = intent
        isEditMode = intent.getBooleanExtra("isEditMOde", false)
        if (isEditMode){
            actionBar!!.title = "Update Record"

            id = intent.getStringExtra("ID")
            name = intent.getStringExtra("NAME")
            phone = intent.getStringExtra("PHONE")
            email = intent.getStringExtra("EMAIL")
            dob = intent.getStringExtra("DOB")
            bio = intent.getStringExtra("BIO")
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"))
            addTime = intent.getStringExtra("ADDED_TIME")
            updateTime = intent.getStringExtra("UPDATE_TIME")

            if (imageUri.toString() == null){
                profileIv.setImageResource(R.drawable.ic_person_black)
            } else{
                profileIv.setImageURI(imageUri)
            }
            nameEt.setText(name)
            genreEt.setText(phone)
            lenghtEt.setText(email)
            dobEt.setText(dob)
            bioEt.setText(bio)
        }else{
            actionBar!!.title = "Add Record"
        }

        dbHelper =
            MyDbHelper(this)

        cameraPermissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        profileIv.setOnClickListener {
            imagePickDialog();
        }

        saveBtn.setOnClickListener {
            if(nameEt.text.toString() != "" && genreEt.text.toString() != "" && lenghtEt.text.toString() != "" && dobEt.text.toString() != "") {
                inputData()
            } else {
                Toast.makeText(this, getResources().getString(R.string.AddUpdateError), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputData() {
        name = ""+nameEt.text.toString().trim()
        phone = ""+genreEt.text.toString().trim()
        email = ""+lenghtEt.text.toString().trim()
        dob = ""+dobEt.text.toString().trim()
        bio = ""+bioEt.text.toString().trim()

        if (isEditMode){

            val timeStamp = "${System.currentTimeMillis()}"
            dbHelper?.updateRecord(
                "$id",
                "$name",
                "$imageUri",
                "$bio",
                "$phone",
                "$email",
                "$dob",
                "$addTime",
                "$timeStamp"
            )

            Toast.makeText(this,"Updated...", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            val timestamp = System.currentTimeMillis()
            val id = dbHelper.insertRecord(
                ""+name,
                ""+imageUri,
                ""+bio,
                ""+phone,
                ""+email,
                ""+dob,
                ""+timestamp,
                ""+timestamp
            )
            Toast.makeText(this, "Record added against ID $id", Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    private fun imagePickDialog() {
        val options = arrayOf(getString(R.string.Camera), getString(
            R.string.Gallery
        ))
        val builder =  AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.PickImageFrom))
        builder.setItems(options){dialog, which ->
            if (which==0){
                if(!chekCameraPermissions()){
                    requestCameraPermission()
                } else {
                    pickFromCamera()
                }
            } else {
                if (!checkStoragePermission()){
                    requestStoragePermission()
                } else {
                    pickFromGallery()
                }
            }
        }
        builder.show()
        }

    private fun pickFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(
            galleryIntent,
            IMAGE_PICK_GALLERY_CODE
        )
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE)
    }

    private fun checkStoragePermission(): Boolean{

        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun pickFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Image Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(
            cameraIntent,
            IMAGE_PICK_GALLERY_CODE
        )
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }

    private fun chekCameraPermissions(): Boolean {
        val results = ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED
        val results1 = ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        return results && results1
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if (grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if(cameraAccepted && storageAccepted){
                        pickFromCamera()
                    } else {
                        Toast.makeText(this, "Camera and Storage permission are required", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            STORAGE_REQUEST_CODE->{
                if (grantResults.isNotEmpty()){
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if(storageAccepted){
                        pickFromGallery()
                    } else {
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data!!.data)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this)
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this)
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                val result = CropImage.getActivityResult(data)
                if(resultCode == Activity.RESULT_OK){
                    val resultUri = result.uri
                    imageUri = resultUri
                    profileIv.setImageURI(resultUri)
                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    val error = result.error
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}