package com.example.uke_3_4_oppgave.login

import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import java.lang.Exception

class LoginViewModel : ViewModel() {
    fun correctCredentials(username: String, password: String): Boolean {
        return username == "kiriza" && password == "xcv234"
    }
    fun logInUser(
        requestQueue: RequestQueue,
        username: String,
        password: String,
        callBack: (UserObject?) -> Unit
    ) {
        var url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/login"
        url += "?username=$username&password=$password"

        val stringRequest = StringRequest (
                Request.Method.GET,
                url,
                {jsonResponse ->
                    val user = Gson().fromJson(jsonResponse, UserObject::class.java)
                    callBack(user)
                },
                {
                  callBack(null)
                }
        )
        requestQueue.add(stringRequest)
    }
}