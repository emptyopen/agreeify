package com.takaomatt.agreeifyer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView


class DisplayEditChoice : AppCompatActivity() {

    private val dbManager = DatabaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_choice)

        findViewById<TextView>(R.id.editDescription).text = intent.getStringExtra("description")
        findViewById<TextView>(R.id.editTags).text = intent.getStringExtra("tags")
    }

    fun updateDB(view: View) {
        val description = (findViewById<View>(R.id.editDescription) as EditText).text.toString()
        val tags = (findViewById<View>(R.id.editTags) as EditText).text.toString()
        val newChoice = Choice(description, tags, "F", "F")
        dbManager.insert(newChoice)

        val intent = Intent(this, DisplayEditChoice::class.java)
        startActivity(intent)
    }
}