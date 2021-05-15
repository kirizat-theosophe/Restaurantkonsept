package com.example.uke_3_4_oppgave.webviewa

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    /* val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
     val chatMessagesLiveData: MutableLiveData<List<ChatObject>> = MutableLiveData()

    fun showLoader() {
        isLoading.postValue(true)
    }

    fun getChatMessages(
            userId: String?,
            queue: RequestQueue,
            //successCallback: (List<ChatObject>) -> Unit,
            errorCallback: () -> Unit
    ) {
        val gson = Gson()

        var url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/messages"
        url += "?userId=$userId"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
           { response ->
               val listType: Type = object : TypeToken<List<ChatObject?>?>() {}.type
               val chatMessages = gson.fromJson<List<ChatObject>>(response, listType)
               //successCallback(chatMessages)

               if (chatMessagesLiveData.value != chatMessages) {
                   chatMessagesLiveData.postValue(chatMessages)
               }
               isLoading.postValue(false)

           },

           {
               errorCallback()
               isLoading.postValue(false)
           }
        )
        queue.add(stringRequest)

    }
    fun sendChatMessage(
            requestQueue: RequestQueue,
            chatObject: ChatObject,
            callback: (Boolean) -> Unit) {
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/sendMessage"

        //val chatObject = ChatObject("wgV7RMZXD2", "øtziii", message)
        val chatJson = Gson().toJson(chatObject)

        val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                JSONObject(chatJson),
                { jsonObjectResponse ->
                    callback(true)
                },
                {error ->
                    callback(false)
                }
            )
        requestQueue.add(request)

    }*/
}