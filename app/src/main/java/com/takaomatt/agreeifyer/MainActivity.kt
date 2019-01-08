package com.takaomatt.agreeifyer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.util.*


/* to-do:
*
* add veto alternate screen for choices
* add ability to slide and delete choices
* add ability to generate random tags
* add ability to auto update if same name is added
* add update to dbmanager
*
* */

class MainActivity : AppCompatActivity() {

    private val dbManager = DatabaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.agreed_choice).text = "Where to next?"
    }

    fun agreeify(view: View) {
        val choices = dbManager.readWithQuery("where team1 = 'F' and team2 = 'F'")
        val randomElement = choices.get(Random().nextInt(choices.size))
        findViewById<TextView>(R.id.agreed_choice).text = randomElement.description
    }

    fun goToChoices(view: View) {
        val intent = Intent(this, DisplayChoices::class.java)
        var v = "all"
        when(view.id) {
            R.id.allChoicesButton -> v = "all"
            R.id.team1Button -> v = "t1f"
            R.id.team2Button -> v = "t2f"
        }
        intent.putExtra("type", v)
        startActivity(intent)
    }
}
