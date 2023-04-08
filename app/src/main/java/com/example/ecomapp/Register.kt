package com.example.ecomapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecomapp.databinding.ActivityRegisterBinding
import com.example.ecomapp.model.User

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonCreateAccount.setOnClickListener { registerAccount() }
    }

    private fun registerAccount() {
        with(binding) {
            val lastName = editTextLastName.text.toString()
            val firstName = editTextFirstName.text.toString()
            val email = editTextEmailAddress.text.toString()
            val password = editTextPassword.text.toString()

            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "All fields required!", Toast.LENGTH_SHORT).show()
                return
            }

            val user = User(firstName, lastName, email, password) as java.io.Serializable

            val intent = Intent()
            intent.putExtra("user", user)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}