package ru.sug4chy.englishwordsapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

class LearnWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)
    }

    //дан верный ответ
    fun onCorrectAnswerChoose(view: View) {
        onAnswerChoose(true)
        lightCorrectAnswer(view)
    }

    //дан неверный ответ
    fun onWrongAnswerChoose(view: View) {
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
        turnOffLightness(correctVariant)
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
        turnOffLightness(variant)
        val layout = variant as LinearLayout
        layout.setBackgroundResource(R.drawable.shape_wrong_layout_lighted)

        val numberTextView = layout.children.elementAt(0) as TextView
        numberTextView.setBackgroundResource(R.drawable.shape_wrong_variant_number)
        numberTextView.setTextColor(Color.WHITE)

        val variantTextView = layout.children.elementAt(1) as TextView
        variantTextView.setTextColor(Color.parseColor("#FA4169"))
    }

    //выключает подсветку на всех вариантах ответа
    private fun turnOffLightness(variant: View) {
        val layout = variant.parent as LinearLayout
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
}