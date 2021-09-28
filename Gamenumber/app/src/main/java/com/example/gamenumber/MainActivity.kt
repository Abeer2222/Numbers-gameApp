package com.example.gamenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpractice.MessageAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var guessField: EditText
    private lateinit var gButton: Button
    private lateinit var myLayout: ConstraintLayout
    private lateinit var theList: ArrayList<String>
    private lateinit var tvPrompt: TextView

    private var rNumber = 0
    private var guessCount = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rNumber = Random.nextInt(10)

        myLayout = findViewById(R.id.clMain)
        theList = ArrayList()

        tvPrompt = findViewById(R.id.tvPrompt)

        rvMessages.adapter = MessageAdapter(this, theList)
        rvMessages.layoutManager = LinearLayoutManager(this)

        guessField = findViewById(R.id.etGuessField)
        gButton = findViewById(R.id.btGuess)

        gButton.setOnClickListener { addMessage() }
    }
    private fun addMessage(){
        val input = guessField.text.toString()
        if(input.isNotEmpty()){
            if(guessCount>0){
                if(input.toInt() == rNumber){
                    disableEntry()
                    showAlertDialog("Correct!\n\n Again?")
                }else{
                    guessCount--
                    theList.add("You guessed $input")
                    theList.add("You have guessed $guessCount ")
                }
                if(guessCount==0){
                    disableEntry()
                    theList.add("Y The correct answer was $rNumber")
                    theList.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $rNumber.\n\nPlay again?")
                }
            }
            guessField.text.clear()
            guessField.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(myLayout, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun disableEntry(){
        gButton.isEnabled = false
        gButton.isClickable = false
        guessField.isEnabled=false
        guessField.isClickable = false
    }

    private fun showAlertDialog(title: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(title)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }

}