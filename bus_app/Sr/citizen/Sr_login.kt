package com.example.bus_app.Sr.citizen

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bus_app.R
import com.example.bus_app.student_home
import com.google.firebase.auth.FirebaseAuth

const val RC_SIGN_IN=123

class Sr_login : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sr_login)

        val edemail = findViewById<EditText>(R.id.edemail)
        val edpassword = findViewById<EditText>(R.id.edpassword)
        val btnforgot = findViewById<Button>(R.id.btnforgotpassword)
        val btnlogin = findViewById<Button>(R.id.btnlogin)
        auth = FirebaseAuth.getInstance()

        val btn = findViewById<Button>(R.id.btnregister)
        btn.setOnClickListener {
            val intent = Intent(applicationContext,sr_register::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener {
            if(edemail.text.isEmpty() ) {
                edemail.setError("Enter Email Id")
                return@setOnClickListener
            }
            else if (edpassword.text.isEmpty()){
                edpassword.setError("Enter Password")
                return@setOnClickListener
            }



            auth.signInWithEmailAndPassword(edemail.text.toString(),edpassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        Toast.makeText(applicationContext,"successfully Login", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, sr_home::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Failed to login", Toast.LENGTH_LONG).show()
                    }
                }
        }

        btnforgot.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dailog_forgot,null)
            val username = view.findViewById<EditText>(R.id.ed_forgot)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgotpassword(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->  })
            builder.show()

        }


    }

    private fun forgotpassword(username: EditText) {
        auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    Toast.makeText(applicationContext,"Email Sent", Toast.LENGTH_LONG).show()
                }
            }

    }


}