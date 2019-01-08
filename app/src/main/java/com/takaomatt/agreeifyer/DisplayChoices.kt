package com.takaomatt.agreeifyer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView

class DisplayChoices : AppCompatActivity() {

    private val dbManager = DatabaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_choices)

        val type = intent.getStringExtra("type")

        val allChoices = dbManager.readAll()
        var choices = ArrayList(allChoices)
        when (type) {
            "t1f" -> {
                choices = ArrayList(allChoices.filter { it.team1 == "F" })
                findViewById<TextView>(R.id.choicesTitle).text = "Favorites"
                findViewById<TextView>(R.id.switchButton).text = "Vetoes"
            }
            "t1v" -> {
                choices = ArrayList(allChoices.filter { it.team1 == "V" })
                findViewById<TextView>(R.id.choicesTitle).text = "Vetoes"
                findViewById<TextView>(R.id.switchButton).text = "Favorites"
            }
            "t2f" -> {
                choices = ArrayList(allChoices.filter { it.team2 == "F" })
                findViewById<TextView>(R.id.choicesTitle).text = "Favorites"
                findViewById<TextView>(R.id.switchButton).text = "Vetoes"
            }
            "t2v" -> {
                choices = ArrayList(allChoices.filter { it.team2 == "V" })
                findViewById<TextView>(R.id.choicesTitle).text = "Vetoes"
                findViewById<TextView>(R.id.switchButton).text = "Favorites"
            }
        }

        // choices
        val recyclerView = findViewById<RecyclerView>(R.id.choicesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChoiceAdapter(applicationContext, choices)
    }

    fun goToAddChoice(view: View) {
        val intent = Intent(this, DisplayAddChoice::class.java)
        startActivity(intent)
    }

    fun goToEditChoice(view: View) {
        val intent = Intent(this, DisplayEditChoice::class.java)
        intent.putExtra("description", "test description")
        intent.putExtra("tags", "sample tags")
        intent.putExtra("team1", "F")
        intent.putExtra("team2", "V")
        startActivity(intent)
    }

    fun toggleFavVeto(view: View) {
        val intent = Intent(this, DisplayChoices::class.java)
        startActivity(intent)
    }
}
