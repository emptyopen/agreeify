package com.takaomatt.agreeifyer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText



class DisplayAddChoice : AppCompatActivity() {

    private val dbManager = DatabaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_choice)
    }

    fun addToDB(view: View) {
        val description = (findViewById<View>(R.id.editDescription) as EditText).text.toString()
        val tags = (findViewById<View>(R.id.editTags) as EditText).text.toString()
        val newChoice = Choice(description, tags, "F", "F")
        dbManager.insert(newChoice)

        val intent = Intent(this, DisplayAddChoice::class.java)
        startActivity(intent)
    }
}