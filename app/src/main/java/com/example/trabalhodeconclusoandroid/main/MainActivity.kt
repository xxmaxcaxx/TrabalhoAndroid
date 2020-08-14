package com.example.trabalhodeconclusoandroid.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.abaout.AboutActivity
import com.example.trabalhodeconclusoandroid.adapter.AdapterRecord
import com.example.trabalhodeconclusoandroid.constants.Constants
import com.example.trabalhodeconclusoandroid.dbhelper.MyDbHelper
import com.example.trabalhodeconclusoandroid.maps.MapsActivity
import com.example.trabalhodeconclusoandroid.record.AddUpdateRecordActivity
import com.example.trabalhodeconclusoandroid.signout.LogoutActivity
import com.example.trabalhodeconclusoandroid.utils.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    lateinit var dbHelper: MyDbHelper

    private var NEWEST_FIRST = "${Constants.C_ADDED_TIMESTAMP} DESC"
    private var OLDEST_FIRST = "${Constants.C_ADDED_TIMESTAMP} ASC"
    private var TITLE_ASC = "${Constants.C_NAME} ASC"
    private var TITLE_DESC = "${Constants.C_NAME} DESC"

    private var recentSortOrder = NEWEST_FIRST
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper =
            MyDbHelper(this)

        loadRecords(NEWEST_FIRST)

        addRecordBtn.setOnClickListener {
            val intent = Intent(this, AddUpdateRecordActivity::class.java)
            intent.putExtra("isEditMOde", false)
            startActivity(intent)
        }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigation.getMenu().getItem(0).setChecked(true)

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var itemId = item.itemId
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_home -> {
            }
            R.id.navigation_maps
            -> {
                val intent = Intent(applicationContext, MapsActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_signout -> {
                val intent = Intent(this, LogoutActivity::class.java)
                startActivity(intent)
            }
            else -> {
                null
            }
        }
        false
    }

    private fun loadRecords(orderBy:String) {
        recentSortOrder = orderBy;
        val adapterRecord =
            AdapterRecord(
                this,
                dbHelper.getAllRecords((orderBy))
            )

        recordsRv.adapter = adapterRecord
    }

    private fun searchRecords(query:String) {
        val adapterRecord =
            AdapterRecord(
                this,
                dbHelper.searchRecords(query)
            )

        recordsRv.adapter = adapterRecord
    }

    private fun sortDialog(){
        val options = arrayOf("Title Ascending", "Tittle Descending", "Newest", "Oldest")

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sort by")
            .setItems(options){_, which ->
                if(which==0){
                   loadRecords(TITLE_ASC)
                } else if(which==1){
                    loadRecords(TITLE_DESC)
                } else if(which==2){
                    loadRecords(NEWEST_FIRST)
                } else if(which==3){
                    loadRecords(OLDEST_FIRST)
                }
            }
            .show()
    }

    public override fun onResume() {
        super.onResume()
        loadRecords(recentSortOrder)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu!!.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    searchRecords(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    searchRecords(query)
                }
                return true
            }
        }
        )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id== R.id.action_sort){
            sortDialog()
        }
        return super.onOptionsItemSelected(item)
    }
}
