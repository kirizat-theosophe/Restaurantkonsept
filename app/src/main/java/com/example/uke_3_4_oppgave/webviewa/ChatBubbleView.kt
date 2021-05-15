package com.example.uke_3_4_oppgave.webviewa

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.uke_3_4_oppgave.R

class ChatBubbleView(context: Context) : ConstraintLayout(context) {

    private val chatLeftTextView: TextView
    private val authorLeftTextView: TextView
    private val chatRightTextView: TextView
    private val authorRightTextView: TextView


    private  val view = LayoutInflater.from(context).inflate(R.layout.chat_bubble, this)

    init {
        chatLeftTextView= view.findViewById(R.id.chat_left_text_view)
        authorLeftTextView = view.findViewById(R.id.chat_left_author_text_view)
        chatRightTextView= view.findViewById(R.id.chat_right_text_view)
        authorRightTextView = view.findViewById(R.id.chat_right_author_text_view)
    }

    fun setChatText(chatText: String) {
        chatRightTextView.text = chatText
        chatLeftTextView.text = chatText
    }

    fun setAuthorText(authorName: String) {
        authorLeftTextView.text = authorName
        authorRightTextView.text = authorName
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setSelfAuthor(isSelfAuthor: Boolean) {
        chatLeftTextView.isVisible = !isSelfAuthor // det betyr true
        authorLeftTextView.isVisible = !isSelfAuthor

        chatRightTextView.isVisible = isSelfAuthor // det betyr false
        authorRightTextView.isVisible = isSelfAuthor
    }
}