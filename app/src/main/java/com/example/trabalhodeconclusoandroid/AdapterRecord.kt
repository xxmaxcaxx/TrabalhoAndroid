package com.example.trabalhodeconclusoandroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecord() : RecyclerView.Adapter<AdapterRecord.HolderRecord>() {

    private var context:Context?=null
    private var recordList:ArrayList<ModelRecord>?=null

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
            context.startActivities(intent)
        }

        holder.moreBtn.setOnClickListener{

        }
    }

    inner class HolderRecord(itemView: View): RecyclerView.ViewHolder(itemView) {

        var profileTV:ImageView = itemView.findViewById(R.id.profileIv)
        var nameTv:TextView = itemView.findViewById(R.id.nameTv)
        var phoneTv:TextView = itemView.findViewById(R.id.phoneTv)
        var emailTv:TextView = itemView.findViewById(R.id.emailTv)
        var dobTv:TextView = itemView.findViewById(R.id.dobTv)
        var moreBtn:TextView = itemView.findViewById(R.id.moreBtn)

    }
}