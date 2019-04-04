package com.example.assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class LogInActivity : AppCompatActivity() {
    private var preferences: SharedPreferences? = null
    private var usernameField: EditText? = null
    private var passwordField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        preferences = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        usernameField = findViewById(R.id.loginUsernameET)
        passwordField = findViewById(R.id.loginPasswordET)
        val username = preferences!!.getString(USERNAME, null)
        val password = preferences!!.getString(PASSWORD, null)
        if (username != null && password != null) {
            usernameField!!.setText(username)
            passwordField!!.setText(password)
        }
    }

    fun loginButtonClicked(view: View) {
        val username = usernameField!!.text.toString()
        val password = passwordField!!.text.toString()
        val ok = CheckPassword.Check(username, password)
        if (ok) {
            val checkBox = findViewById<CheckBox>(R.id.loginCB)
            val editor = preferences!!.edit()
            if (checkBox.isChecked) {
                editor.putString(USERNAME, username)
                editor.putString(PASSWORD, password)
            } else {
                editor.remove(USERNAME)
                editor.remove(PASSWORD)
            }
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(USERNAME, username)
            startActivity(intent)
        } else {
            usernameField!!.error = "Wrong username or password"
            val messageView = findViewById<TextView>(R.id.loginTV)
            messageView.text = "Username + password not valid"
        }
    }

    companion object {

        val PREF_FILE_NAME = "loginPref"
        val USERNAME = "USERNAME"
        val PASSWORD = "PASSWORD"
    }
}
