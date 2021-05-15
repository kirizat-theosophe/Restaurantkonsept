package com.example.uke_3_4_oppgave.webviewa

import android.os.Build
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
        private var dataSet: List<ChatObject>,
        private val userId: String
): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatBubbleView(parent.context)

        view.layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
        )

        return ChatViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatObject = dataSet[position]

        holder.view.setChatText(chatObject.message)

       val dateOfMessage = Date(chatObject.timestamp)

        val calender = Calendar.getInstance()
        calender.time = dateOfMessage
        val timeFormat = SimpleDateFormat("HH:mm dd/MM/yyyy")

        val authorText = "${chatObject.userName.capitalize()} - ${timeFormat.format(calender.time)}"

        holder.view.setAuthorText(authorText)

        //holder.view.setSelfAuthor(chatObject.userId == "wgV7RMZXD2")
        holder.view.setSelfAuthor(chatObject.userId == "CRPcJ2B0QM")
        //holder.view.setSelfAuthor(chatObject.userId == userId)

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
    fun updateData(newData: List<ChatObject>){
        dataSet = newData
        notifyDataSetChanged()
    }

    fun addInstance(chatObject: ChatObject){
        updateData(dataSet + chatObject)
    }

    inner class ChatViewHolder(
            //val view: TextView
            val view: ChatBubbleView
    ): RecyclerView.ViewHolder(view)


}