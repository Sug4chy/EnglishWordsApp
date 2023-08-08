package ru.sug4chy.englishwordsapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import kotlin.random.Random

class LearnWordActivity : AppCompatActivity() {

    private var wordsWithTranslation: MutableMap<String, String> = mutableMapOf()

    private val alreadyUsedWords = mutableListOf<String>()

    private var iterationsCount = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        val words = intent.getStringArrayExtra("words")!!
        val translations = intent.getStringArrayExtra("translations")!!
        for (i in words.indices) {
            wordsWithTranslation[words[i]] = translations[i]
        }
        super.onCreate(savedInstanceState)
        generateWordAndVariants()
    }

    //дан верный ответ
    private fun onCorrectAnswerChoose(view: View) {
        onAnswerChoose(true)
        lightCorrectAnswer(view)
    }

    //дан неверный ответ
    private fun onWrongAnswerChoose(view: View) {
        onAnswerChoose(false)
        lightWrongAnswer(view)
    }

    //выдаёт результат в зависимости от того, верный был дан ответ или нет
    private fun onAnswerChoose(answerCorrect: Boolean) {
        val skipButton: Button = findViewById(R.id.skip_button)
        skipButton.visibility = View.GONE

        val wrongAnswerView: ConstraintLayout = findViewById(R.id.wrong_result)
        val correctAnswerView: ConstraintLayout = findViewById(R.id.correct_result)

        if (answerCorrect) {
            correctAnswerView.visibility = View.VISIBLE
            wrongAnswerView.visibility = View.GONE
        } else {
            wrongAnswerView.visibility = View.VISIBLE
            correctAnswerView.visibility = View.GONE
        }
    }

    //подсвечивает верный вариант ответа
    private fun lightCorrectAnswer(correctVariant: View) {
        turnOffLightness()
        val layout = correctVariant as LinearLayout
        layout.setBackgroundResource(R.drawable.shape_correct_layout_lighted)

        val numberTextView = layout.children.elementAt(0) as TextView
        numberTextView.setBackgroundResource(R.drawable.shape_correct_variant_number)
        numberTextView.setTextColor(Color.WHITE)

        val variantTextView = layout.children.elementAt(1) as TextView
        variantTextView.setTextColor(Color.parseColor("#0EAD69"))
    }

    //подсвечивает неверный вариант ответа
    private fun lightWrongAnswer(variant: View) {
        turnOffLightness()
        val layout = variant as LinearLayout
        layout.setBackgroundResource(R.drawable.shape_wrong_layout_lighted)

        val numberTextView = layout.children.elementAt(0) as TextView
        numberTextView.setBackgroundResource(R.drawable.shape_wrong_variant_number)
        numberTextView.setTextColor(Color.WHITE)

        val variantTextView = layout.children.elementAt(1) as TextView
        variantTextView.setTextColor(Color.parseColor("#FA4169"))
    }

    //выключает подсветку на всех вариантах ответа
    private fun turnOffLightness() {
        val layout: LinearLayout = findViewById(R.id.linearLayout)
        layout.children.forEach {
            val variantLayout = it as LinearLayout
            variantLayout.setBackgroundResource(R.drawable.shape_rounded_containers)

            val numberTextView = variantLayout.children.elementAt(0) as TextView
            numberTextView.setBackgroundResource(R.drawable.shape_rounded_variants)
            numberTextView.setTextColor(Color.parseColor("#888992"))

            val variantTextView = variantLayout.children.elementAt(1) as TextView
            variantTextView.setTextColor(Color.parseColor("#888992"))
        }
    }

    //возврат к дефолтному состоянию визуала
    fun onVisualReset(view: View) {
        setContentView(R.layout.activity_learn_word)
    }

    fun onContinueButtonClicked(view: View) {
        if (--iterationsCount > 0) {
            generateWordAndVariants()
        } else {
            val intent = Intent(this, EndActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateWordAndVariants() {
        val index = Random.nextInt(wordsWithTranslation.size)

        setContentView(R.layout.activity_learn_word)

        val textViewWithWord: TextView = findViewById(R.id.textView2)
        var key = wordsWithTranslation.keys.elementAt(index)

        if (key in alreadyUsedWords) {
            key = wordsWithTranslation.keys.first { it !in alreadyUsedWords }
        }

        textViewWithWord.text = key

        val correctAnswerIndex = Random.nextInt(0, 4)

        val allVariantsLayout: LinearLayout = findViewById(R.id.linearLayout)
        val correctVariant = allVariantsLayout.children.elementAt(correctAnswerIndex) as LinearLayout
        correctVariant.setOnClickListener(::onCorrectAnswerChoose)

        val textViewWithCorrectVariant = correctVariant.children.elementAt(1) as TextView
        val value = wordsWithTranslation.remove(key)
        alreadyUsedWords.add(key)
        textViewWithCorrectVariant.text = value

        for (variant in allVariantsLayout.children) {
            if (variant == correctVariant) {
                continue
            }
            val randomIndex = Random.nextInt(wordsWithTranslation.size)
            val variantLayout = variant as LinearLayout
            variantLayout.setOnClickListener(::onWrongAnswerChoose)
            val textViewWithVariant = variantLayout.children.elementAt(1) as TextView
            textViewWithVariant.text = wordsWithTranslation.values.elementAt(randomIndex)
        }

        value?.let { wordsWithTranslation[key] = it }
    }

    fun onWrongContinueButtonClicked(view: View) {
        turnOffLightness()
        val skipButton: Button = findViewById(R.id.skip_button)
        skipButton.visibility = View.VISIBLE

        val wrongResultLayout = view.parent as ConstraintLayout
        wrongResultLayout.visibility = View.GONE
    }
}