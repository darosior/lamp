package com.lvaccaro.lamp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_log.*
import org.jetbrains.anko.doAsync
import java.io.BufferedReader
import java.io.File
import java.lang.Exception

class LogActivity : AppCompatActivity() {

    var daemon = "lightningd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        readLog()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_log, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_lightning -> {
                daemon = "lightningd"
                readLog()
                true
            }
            R.id.action_tor -> {
                daemon = "tor"
                readLog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun readLog() {
        title = "Log $daemon"
        val logFile = File(rootDir(),"$daemon.log")
        if (!logFile.exists()) {
            Toast.makeText(this, "No log file found", Toast.LENGTH_LONG).show()
            return
        }
        val et = findViewById<EditText>(R.id.editText)
        et.movementMethod = ScrollingMovementMethod()
        et.isVerticalScrollBarEnabled = true
        et.setText("")

        read(logFile.bufferedReader(), et)
    }

    fun read(logReader: BufferedReader, et: EditText) {
        var text = "."
        while (!text.isEmpty()) {
            text = read100(logReader)
            et.append(text)
        }
    }

    fun read100(logReader: BufferedReader): String {
        val sb = StringBuilder()
        for (i in 0..100) {
            try {
                val line = logReader.readLine() ?: break
                sb.append(line)
            } catch (e: Exception) {
                break
            }
        }
        return sb.toString()
    }
}
