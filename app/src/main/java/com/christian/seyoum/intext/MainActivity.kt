package com.christian.seyoum.intext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var myClipboard: ClipboardManager? = null
    private var clip:ClipboardManager? = null
    private var level = 1
    private var key:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        level_in.setText(level.toString())

        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        clip = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        encrypt_btn.setOnClickListener {
            key.add("Decrypt "+ level.toString())
            level = level_in.text.toString().toInt()
            val text = input.text.toString()
            val nText = encrypt(text, level)
            input.setText(nText)
        }
        decrypt_btn.setOnClickListener {
            key.add("Encrypt "+ level.toString())
            level = level_in.text.toString().toInt()
            val text = input.text.toString()
            val nText = dencrypt(text, level)
            input.setText(nText)
        }

        up_btn.setOnClickListener {
            level++
            level_in.setText(level.toString())
        }
        down_btn.setOnClickListener {
            level--
            level_in.setText(level.toString())
        }
        key_btn.setOnClickListener{
            if (key.size != 0) {
                key.add("Use the following steps to decrypt the text: ")
            }
            else{
                key.add("There is no key at the moment")
            }
            key.reverse()
            val myClip = ClipData.newPlainText("text", key.toString())
            myClipboard?.setPrimaryClip(myClip)
            Toast.makeText(this, "Key Copied", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.clear -> {
                input.setText("")
                return true
            }

            R.id.copy -> {
                val myClip = ClipData.newPlainText("text", input.text)
                myClipboard?.setPrimaryClip(myClip)
                Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.paste -> {
                val itemCount = clip?.primaryClip
                val text = itemCount?.getItemAt(0)?.text.toString()
                input.setText(text)
                return true
            }
            R.id.theme -> {
                Toast.makeText(this,"Coming Soon Ruthye", Toast.LENGTH_LONG).show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
    fun encrypt(text:String, size:Int):String{
        val eText = text.toCharArray()
        val numText:MutableList<Int> = mutableListOf()
        var newText = ""
        var num:Int
        val s = size
        for (n in eText){
            num = n.toInt() + s
            numText.add(num)
        }
        for (m in numText){
            newText = newText + m.toChar()

        }

        return newText
    }
    fun dencrypt(text:String, size: Int):String{
        val eText = text.toCharArray()
        val numText:MutableList<Int> = mutableListOf()
        var newText = ""
        var num:Int
        val s = size
        for (n in eText){
            num = n.toInt() - s
            numText.add(num)
        }
        for (m in numText){
            newText = newText + m.toChar()

        }

        return newText
    }
}
