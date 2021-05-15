package com.example.uke_3_4_oppgave.login

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.example.uke_3_4_oppgave.*
import com.example.uke_3_4_oppgave.webviewa.MainActivity

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var insertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        loginButton = view.findViewById(R.id.login_button)
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        insertButton = view.findViewById(R.id.button)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        val applicationContext = null
        var helpers = applicationContext?.let { MyDBhelper(it) }
        var db = helpers?.readableDatabase
        var rs = db?.rawQuery("SELECT * FROM USERS", null)

        if (rs != null) {
            if (rs.moveToNext())
                Toast.makeText(applicationContext, rs.getString(1), Toast.LENGTH_LONG).show()
        }

        insertButton.setOnClickListener {
            var contentValues = ContentValues()
            contentValues.put("UNAME", usernameEditText.text.toString())
            contentValues.put("PWD", passwordEditText.text.toString())
            db?.insert("USERS", null, contentValues)

            usernameEditText.setText("")
            passwordEditText.setText("")
            //editText.requestFocus()
        }
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (viewModel.correctCredentials(username, password)) {
                val sharedPref = activity?.getSharedPreferences(
                    LoginActivity.SHARED_PREF_FILENAME,
                    Context.MODE_PRIVATE
                )
                sharedPref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY, true)?.apply()

                viewModel.logInUser(
                    Volley.newRequestQueue(context),
                    username,
                    password
                ) { user ->
                    if (user != null) {

                        val sharedPreferences = requireActivity().getSharedPreferences(
                            SHARED_PREFS_NAME,
                            Context.MODE_PRIVATE
                        )
                        val editor = sharedPreferences.edit()

                        editor.putString(SHARED_PREFS_ID_KEY, user.id)
                        editor.putString(SHARED_PREFS_USERNAME_KEY, user.userName)
                        editor.putString(SHARED_PREFS_FIRSTNAME_KEY, user.firstName)

                        editor.apply()

                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

}