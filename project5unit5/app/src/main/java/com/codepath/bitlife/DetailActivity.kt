package com.codepath.bitlife

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codepath.bitlife.data.DisplayWaterIntake

class DetailActivity : AppCompatActivity() {
    private lateinit var dateTextView: TextView
    private lateinit var amountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Initialize the TextViews for date and amount
        dateTextView = findViewById(R.id.detailDate)
        amountTextView = findViewById(R.id.detailAmount)

        // Get the DisplayWaterIntake object from the intent
        val waterIntake = intent.getSerializableExtra(ARTICLE_EXTRA) as? DisplayWaterIntake

        // Set the text for the TextViews with the water intake data
        waterIntake?.let {
            dateTextView.text = it.date
            amountTextView.text = "${it.amount} ml"
        }
    }

    companion object {
        const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
    }
}
