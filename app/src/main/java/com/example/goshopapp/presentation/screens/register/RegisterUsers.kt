package com.example.goshopapp.presentation.screens.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goshopapp.MainActivity
import com.example.goshopapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterUsers : AppCompatActivity() {

    private lateinit var registerEmail : EditText
    private lateinit var registerPassword : EditText
    private lateinit var btnRegistro : Button

    private lateinit var auth : FirebaseAuth
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_layout)
        supportActionBar!!.title = "Registro"
        initVars()
        btnRegistro.setOnClickListener {
            validateData()
        }
    }

    private fun initVars() {
        registerEmail = findViewById(R.id.user_email)
        registerPassword = findViewById(R.id.user_password)
        btnRegistro = findViewById(R.id.registerBtn)
        auth = FirebaseAuth.getInstance()
    }

    private fun validateData() {
        val email : String = registerEmail.text.toString()
        val password : String = registerPassword.text.toString()
        if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Debe poner email", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Debe poner contraseña", Toast.LENGTH_SHORT).show()
        } else {
            userRegistration(email, password)
        }
    }

    private fun userRegistration(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
            task->
                if (task.isSuccessful) {
                    var uid : String = auth.currentUser!!.uid
                    reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                    val hashmap = HashMap<String, Any>()
                    val h_email = registerEmail.text.toString()
                    hashmap["uid"] = uid
                    hashmap["h_email"] = h_email
                    reference.updateChildren(hashmap).addOnCompleteListener {
                        task2 ->
                            if (task2.isSuccessful) {
                                val intent = Intent(this@RegisterUsers, MainActivity::class.java)
                                Toast.makeText(applicationContext, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                            }
                    }.addOnFailureListener{
                        e -> Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Se ha producido algún error", Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener{
                e -> Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}