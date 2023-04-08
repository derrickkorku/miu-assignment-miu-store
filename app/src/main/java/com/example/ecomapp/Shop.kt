package com.example.ecomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ecomapp.databinding.ActivityShopBinding

class Shop : AppCompatActivity() {
    private lateinit var binding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initializeWelcomeMessage()
    }

    private fun initializeWelcomeMessage() {
        val intent = intent
        binding.textViewWelcome.text = resources.getString(R.string.text_view_welcome) + " " +  intent.getStringExtra("email")
    }

    fun shopCategory(view: View) {
        when (view.id) {
            R.id.imageViewBook -> Toast.makeText(
                applicationContext,
                resources.getString(R.string.text_book_category_clicked),
                Toast.LENGTH_LONG
            ).show()
            R.id.imageViewClothing -> Toast.makeText(
                applicationContext,
                resources.getString(R.string.text_clothing_category_clicked),
                Toast.LENGTH_LONG
            ).show()
            R.id.imageViewMedicine -> Toast.makeText(
                applicationContext,
                resources.getString(R.string.text_medicines_category_clicked),
                Toast.LENGTH_LONG
            ).show()
            R.id.imageViewOrganicFood -> Toast.makeText(
                applicationContext,
                resources.getString(R.string.text_organic_food_category_clicked),
                Toast.LENGTH_LONG
            ).show()
            else -> Toast.makeText(applicationContext, resources.getString(R.string.text_unknown), Toast.LENGTH_LONG).show()
        }
    }
}