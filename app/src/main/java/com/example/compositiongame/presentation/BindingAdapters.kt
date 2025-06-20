package com.example.compositiongame.presentation

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.compositiongame.R

@SuppressLint("StringFormatMatches")
@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count:Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}
@SuppressLint("StringFormatMatches")
@BindingAdapter("scoreAnswers")
fun bindTvScoreAnswers(textView: TextView, count:Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}
@SuppressLint("StringFormatMatches")
@BindingAdapter("requiredPercentage")
fun bindTvRequiredPercentage(textView: TextView, count:Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}
@SuppressLint("StringFormatMatches")
@BindingAdapter("scorePercentage")
fun bindTvScorePercentage(textView: TextView, count:Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        count
    )
}









