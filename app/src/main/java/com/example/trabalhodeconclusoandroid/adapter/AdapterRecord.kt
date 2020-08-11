package com.example.trabalhodeconclusoandroid.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalhodeconclusoandroid.*
import com.example.trabalhodeconclusoandroid.dbhelper.MyDbHelper
import com.example.trabalhodeconclusoandroid.main.MainActivity
import com.example.trabalhodeconclusoandroid.model.ModelRecord
import com.example.trabalhodeconclusoandroid.record.AddUpdateRecordActivity
import com.example.trabalhodeconclusoandroid.record.RecordDetailActivity

class AdapterRecord() : RecyclerView.Adapter<AdapterRecord.HolderRecord>() {

    private var context:Context?=null
    private var recordList:ArrayList<ModelRecord>?=null

    lateinit var ddbHelper: MyDbHelper

    constructor(context: Context?, recordList: ArrayList<ModelRecord>?) :this(){
        this.context = context
        this.recordList = recordList

        ddbHelper =
            MyDbHelper(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecord {
        return HolderRecord(
            LayoutInflater.from(context).inflate(R.layout.row_record, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recordList!!.size
    }

    override fun onBindViewHolder(holder: HolderRecord, position: Int) {
        var model = recordList!!.get(position)

        val id = model.id
        val name = model.name
        val image = model.image
        val bio = model.bio
        val phone = model.phone
        val email = model.email
        val dob = model.dob
        val addedTime = model.addedTime
        val updateTime = model.updateTime

        holder.nameTv.text = name
        holder.phoneTv.text = phone
        holder.emailTv.text = email
        holder.dobTv.text = dob

        if(image == "null"){
            holder.profileTV.setImageResource(R.drawable.ic_person_black)
        } else{
            holder.profileTV.setImageURI(Uri.parse(image))
        }

        holder.itemView.setOnClickListener(){
            val intent = Intent(context, RecordDetailActivity::class.java)
            intent.putExtra("RECORD_ID", id)
            context!!.startActivity(intent)
        }

        holder.moreBtn.setOnClickListener{
              showMoreOptions(
                  position,
                  id,
                  name,
                  phone,
                  email,
                  dob,
                  bio,
                  image,
                  addedTime,
                  updateTime
              )
        }
    }

    private fun showMoreOptions(
        position: Int,
        id: String,
        name: String,
        phone: String,
        email: String,
        dob: String,
        bio: String,
        image: String,
        addedTime: String,
        updateTime: String
    ) {
        val options = arrayOf("Edit", "Delete")

        val dialog:AlertDialog.Builder = AlertDialog.Builder(context)

         dialog.setItems(options) { dialog, which ->
             if (which==0){
                 val intent = Intent(context, AddUpdateRecordActivity::class.java)
                 intent.putExtra("ID", id)
                 intent.putExtra("NAME", name)
                 intent.putExtra("PHONE", phone)
                 intent.putExtra("EMAIL", email)
                 intent.putExtra("DOB", dob)
                 intent.putExtra("BIO", bio)
                 intent.putExtra("IMAGE", image)
                 intent.putExtra("ADDED_TIME", addedTime)
                 intent.putExtra("UPDATE_TIME", updateTime)
                 intent.putExtra("isEditMOde", true)
                 context!!.startActivity(intent)
             }else{

                 ddbHelper.deleteRecord(id)

                 (context as MainActivity)!!.onResume()

             }
         }
        dialog.show()
    }

    inner class HolderRecord(itemView: View): RecyclerView.ViewHolder(itemView) {

        var profileTV:ImageView = itemView.findViewById(R.id.profileIv)
        var nameTv:TextView = itemView.findViewById(R.id.nameTv)
        var phoneTv:TextView = itemView.findViewById(R.id.phoneTv)
        var emailTv:TextView = itemView.findViewById(R.id.emailTv)
        var dobTv:TextView = itemView.findViewById(R.id.dobTv)
        var moreBtn:ImageButton = itemView.findViewById(R.id.moreBtn)

    }
}