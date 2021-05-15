package com.example.uke_3_4_oppgave.webviewa

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uke_3_4_oppgave.R
import com.example.uke_3_4_oppgave.webviewa.data.Drink
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class MyAdapter(private val dataList: MutableList<Drink>) : RecyclerView.Adapter<MyHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
            val data = dataList [position]

        val userFullNameTextView = holder.itemView.user_full_name
        val userDrinkImgView = holder.itemView.user_drink

        //userFullNameTextView.text = data.capitalize()

        val fullName = "${data.strDrink} ${data.idDrink}"
        userFullNameTextView.text = fullName

         Picasso.get().load(data.strDrinkThumb).into(userDrinkImgView)

        holder.itemView.setOnClickListener{
            Toast.makeText(context, fullName, Toast.LENGTH_LONG).show()
        }
    }
}