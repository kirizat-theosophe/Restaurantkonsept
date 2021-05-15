package com.example.uke_3_4_oppgave.webviewa

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.uke_3_4_oppgave.R
import com.example.uke_3_4_oppgave.SHARED_PREFS_ID_KEY
import com.example.uke_3_4_oppgave.SHARED_PREFS_NAME
import com.example.uke_3_4_oppgave.webviewa.data.Drink
import com.example.uke_3_4_oppgave.webviewa.data.Reqres
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private val dataList: MutableList<Drink> = mutableListOf()
    private lateinit var myAdapter: MyAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatInput: EditText
    private lateinit var sendButton: Button
    private lateinit var loader: ProgressBar


    private var timer: Timer? = null

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //logOutButton = view.findViewById(R.id.log_out_button)
        recyclerView = view.findViewById(R.id.my_recycler_view)

        chatInput = view.findViewById(R.id.chat_input)
        sendButton = view.findViewById(R.id.send_message_button)
        myAdapter = MyAdapter(dataList)


        //activity?.hideKeyboard()

        loader = view.findViewById(R.id.loader)
        loader.isVisible = false

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
         requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        initRecyclerView()
        randomDrink()
    }

    override fun onResume() {
        super.onResume()

        startChatFetchTimer()
    }
    private fun randomDrink() {
        myAdapter = MyAdapter(dataList)

        my_recycler_view.layoutManager = LinearLayoutManager( context)
        my_recycler_view.addItemDecoration(DividerItemDecoration(context, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter


        AndroidNetworking.initialize(context)

        AndroidNetworking.get("https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic")
            .build()
            .getAsObject(Reqres::class.java, object : ParsedRequestListener<Reqres> {
                override fun onResponse(response: Reqres) {
                    dataList.addAll(response.drinks)
                    myAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {

                }

            })

    }


    private fun initRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        val userId= sharedPreferences.getString(SHARED_PREFS_ID_KEY,null)

        if (userId != null) {
            chatAdapter = ChatAdapter(
                listOf(),
                userId
            )
            recyclerView.adapter = chatAdapter
        }else{
            logOutUser()
        }
    }

    private fun getChatMessages(){
        val userId= sharedPreferences.getString(SHARED_PREFS_ID_KEY,null)

        //viewModel.getChatMessages(
                //userId,
                Volley.newRequestQueue(context)
                /*{chatMessages ->
                    chatAdapter.updateData(chatMessages)
                    //recyclerView.scrollToPosition(chatMessages.size - 1)
                    scrollToBottom()
                }*/
         /*) { Toast.makeText(
                        context,
                        "Noe gikk galt. Kunne ikke hente meldinger",
                        Toast.LENGTH_LONG
                ).show()
                }*/
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition((recyclerView.adapter?.itemCount ?: 1) -1)
    }

    private fun startChatFetchTimer(){
        if (timer != null){
            return
        }

        timer = fixedRateTimer("chatFetchTimer", false, 0L, 15 * 1000) {
            getChatMessages()
        }
    }
    private fun logOutUser() {
        (activity as? MainActivity)?.logOutUser()

    }

    override fun onPause() {
        super.onPause()

        timer?.cancel()
        timer = null
    }

}