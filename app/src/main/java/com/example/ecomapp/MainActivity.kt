package com.example.ecomapp

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.ecomapp.databinding.ActivityMainBinding
import com.example.ecomapp.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var users: MutableList<User>
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDefaultUsers()

        var activityResultContracts = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == RESULT_OK) {
                val user = result.data?.getSerializableExtra("user", User::class.java)
                val userObj = user as User
                users.add(userObj)
                Toast.makeText(applicationContext, "Added ${userObj.firstName} successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Failed to get user data", Toast.LENGTH_SHORT).show()
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            buttonSignIn.setOnClickListener { login() }
            textViewCreateNewAccount.setOnClickListener {
                val intent = Intent(this@MainActivity, Register::class.java)
                activityResultContracts.launch(intent)
            }
            textViewForgetPassword.setOnClickListener { sendPassword() }
        }
    }

    private fun sendPassword() {
        val email = binding.editTextTextEmailAddress.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }

        val user = findUserByEmail(email)

        if (user == null) {
            Toast.makeText(applicationContext, "The entered email address does not exist", Toast.LENGTH_SHORT).show()
            return
        }

        val sendEmailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, user.email)
            putExtra(Intent.EXTRA_SUBJECT, "Forget password request")
            putExtra(Intent.EXTRA_TEXT, "Your password is ${user.password}")
        }

        try {
            startActivity(sendEmailIntent)
        }catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "No application found to perform operation!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun findUserByEmail(email: String): User? {
        return users.find{user -> user.email.equals(email)}
    }

    private fun login() {
        with(binding) {
            val email = editTextTextEmailAddress.text.toString()
            val password = editTextTextPassword.text.toString()


            if (email.isEmpty() || email.isEmpty()) {
                Toast.makeText(applicationContext, "Email and Password required!", Toast.LENGTH_SHORT).show()
                return
            }

            val user = findUser(email, password)

            if (user == null) {
                Toast.makeText(applicationContext, "Invalid email or password!", Toast.LENGTH_SHORT).show()
                return
            }

            showShopActivity(user.email)
        }
    }

    private fun showShopActivity(email: String) {
        val intent = Intent(this, Shop::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun findUser(email: String, password: String) : User? {
        return users.find{user -> user.password.equals(password) && user.email.equals(email)}
    }

    private fun initializeDefaultUsers() {
        users = mutableListOf<User>(
            User("Derrick", "Korku", "derrickkorku1@gmail.com", "123"),
            User("Mabel", "Siayor", "admin1@email.com", "123"),
            User("Samuel", "Owusu-Ansah", "admin2@email.com", "123")
        );
    }
}