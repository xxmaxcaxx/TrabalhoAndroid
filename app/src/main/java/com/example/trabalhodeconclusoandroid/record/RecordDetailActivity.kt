package com.example.trabalhodeconclusoandroid.record

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.constants.Constants
import com.example.trabalhodeconclusoandroid.dbhelper.MyDbHelper
import com.example.trabalhodeconclusoandroid.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_record_detail.*
import java.util.*

class RecordDetailActivity : BaseActivity() {

    private var actionBar:ActionBar?=null

    private var dbHelper: MyDbHelper?=null

    private var recordId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_detail)

        actionBar = supportActionBar
        actionBar!!.title = "Record Details"
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        dbHelper =
            MyDbHelper(this)

        val intent = intent
        recordId = intent.getStringExtra("RECORD_ID")

        showRecordDetails()
    }

    private fun showRecordDetails() {
        val selectQuery = "SELECT * FROM " + Constants.TABLE_NAME+ " WHERE " + Constants.C_ID + " =\""+recordId+"\""

        val db = dbHelper!!.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if(cursor.moveToFirst()){
            do{
                val id = ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID))
                val name = ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME))
                val image = ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE))
                val bio = ""+cursor.getString(cursor.getColumnIndex(Constants.C_BIO))
                val phone = ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE))
                val email = ""+cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL))
                val dob = ""+cursor.getString(cursor.getColumnIndex(Constants.C_DOB))
                val addedTimestamp = ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP))
                val updatedTimestamp = ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))

                val calendar1 = Calendar.getInstance(Locale.getDefault())
                calendar1.timeInMillis = addedTimestamp.toLong()
                val timeAdded = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm aa", calendar1)

                val calendar2 = Calendar.getInstance(Locale.getDefault())
                calendar2.timeInMillis = updatedTimestamp.toLong()
                val timeUpdated = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm aa", calendar2)

                nameTv.text = name
                bioTv.text = bio
                phoneTv.text = phone
                emailTv.text = email
                dobTv.text = dob
                addedDateTv.text = timeAdded
                updateDateTv.text = timeUpdated

                if(image == "null"){
                    profileTv.setImageResource(R.drawable.ic_person_black)
                } else{
                    profileTv.setImageURI(Uri.parse(image))
                }
            }while (cursor.moveToNext())
        }
        db.close()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}