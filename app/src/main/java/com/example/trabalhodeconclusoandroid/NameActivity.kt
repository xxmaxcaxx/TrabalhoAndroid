package com.example.trabalhodeconclusoandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.trabalhodeconclusoandroid.db.DatabaseHandler
import com.example.trabalhodeconclusoandroid.model.Pessoa
import kotlinx.android.synthetic.main.activity_name.*

class NameActivity : AppCompatActivity() {

    // Database
    val databaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        val edit = intent.getBooleanExtra("edit", false)
        val position = intent.getIntExtra("position", 0)
        if(edit){
            val pessoa = databaseHandler.getPessoa(position)
            etNome.setText(pessoa.nome)
            btnInsertNome.text = getString(R.string.Edit)
        }
        btnInsertNome.setOnClickListener {
            if(etNome.text.toString() == ""){
                Toast.makeText(this,getString(R.string.Name_empty),Toast.LENGTH_SHORT).show()
            }
            else {
                if(edit){
                    val pessoa = Pessoa(position, etNome.text.toString())
                    databaseHandler.updatePessoa(pessoa)
                    finish()
                }
                else {
                    val pessoa = Pessoa(0, etNome.text.toString())
                    databaseHandler.addPessoa(pessoa)
                    finish()
                }
            }
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
}