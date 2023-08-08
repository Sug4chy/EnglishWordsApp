package ru.sug4chy.englishwordsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.sug4chy.englishwordsapp.models.A1WordsList

class BeginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin)
    }

    fun onA1JumpButtonClick(view: View) {
        val words = A1WordsList()
        val intent = Intent(this, LearnWordActivity::class.java)
        intent.putExtra("words", words.words)
        intent.putExtra("translations", words.translations)
        startActivity(intent)
    }
}