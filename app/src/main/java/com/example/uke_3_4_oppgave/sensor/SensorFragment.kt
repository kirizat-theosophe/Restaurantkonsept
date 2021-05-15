package com.example.uke_3_4_oppgave.sensor

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.uke_3_4_oppgave.R
import com.example.uke_3_4_oppgave.SHARED_PREFS_FIRSTNAME_KEY
import com.example.uke_3_4_oppgave.SHARED_PREFS_NAME
import com.example.uke_3_4_oppgave.SHARED_PREFS_USERNAME_KEY
import com.example.uke_3_4_oppgave.webviewa.MainActivity
import kotlinx.android.synthetic.main.profile_fragment.view.*

class SensorFragment : Fragment(), SensorEventListener {

    private lateinit var viewModel: SensorViewModel

   private lateinit var sensorValueTextView: TextView
    private lateinit var logOutButton: Button

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        logOutButton = view.findViewById(R.id.log_out_button)
        sensorValueTextView = view.sensor_text_view

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(SHARED_PREFS_USERNAME_KEY, "")
        val firstName = sharedPreferences.getString(SHARED_PREFS_FIRSTNAME_KEY, "")

        /*circleTextView.text = firstName?.first()?.toString() ?: "-"
        usernameTextView.text = username
        firstNameTextView.text = firstName*/

            logOutButton.setOnClickListener {
            (activity as? MainActivity)?.logOutUser()
        }

        //Sensor
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SensorViewModel::class.java)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val value1 = event?.values?.get(0)
        val value2 = event?.values?.get(1)
        val value3 = event?.values?.get(2)
        sensorValueTextView.text = "Values are:\n$value1\n$value2\n$value3"

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)

        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}