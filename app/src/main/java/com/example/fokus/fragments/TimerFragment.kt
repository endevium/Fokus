package com.example.fokus.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fokus.R
import com.example.fokus.activities.MainActivity
import com.example.fokus.api.pomodoroSettings
import com.example.fokus.api.saveSettings
import org.w3c.dom.Text

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val CHANNEL_ID = "fokus_notification_channel"
    private lateinit var timerTextView: TextView
    private lateinit var tvPomodoro: TextView
    private lateinit var tvPomodoroDesc: TextView
    private lateinit var playButton: ImageButton
    private lateinit var restartButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var stopBtn: ImageButton
    private lateinit var shortBreakFrag: ShortBreakFragment
    private lateinit var longBreakFrag: LongBreakFragment
    private var pmdr = pomodoroSettings()
    private val settings = saveSettings()
    private var timer: CountDownTimer? = null
    private var timeLeft: Long = 25 * 60 * 1000
    private var isTimerRunning: Boolean = false
    private var phase: Int = 0
    private val notificationID = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assign values to element variables
        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        timerTextView = view.findViewById(R.id.timerTextView)
        playButton = view.findViewById(R.id.playButton)
        restartButton = view.findViewById(R.id.restartButton)
        nextButton = view.findViewById(R.id.tnxtBtn)
        stopBtn = view.findViewById(R.id.stopBtn)
        tvPomodoro = view.findViewById(R.id.tvPomodoro)
        tvPomodoroDesc = view.findViewById(R.id.tvPomodoroDesc)
        shortBreakFrag = ShortBreakFragment()
        longBreakFrag = LongBreakFragment()

        createNotificationChannel()
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, show the notification
                timerNotification()
            } else {
                // Permission denied, notify user using Toast
                Toast.makeText(requireContext(), "Permission denied to send notifications", Toast.LENGTH_SHORT).show()
            }
        }

        if (pmdr.pomodoroMinutes(requireContext()) != null && pmdr.pomodoroSeconds(requireContext()) != null) {
            val minutes = pmdr.pomodoroMinutes(requireContext()) ?: 25
            val seconds = pmdr.pomodoroSeconds(requireContext()) ?: 0
            timeLeft = (minutes * 60 * 1000) + (seconds * 1000)
            updateTimer()
        }

        val args = this.arguments
        val fetchedPhase = args?.getInt("phase")
        val fetchedAutoStart = args?.getBoolean("autoStart")

        // Start automatically if fetchedAutoStart exists and is true
        if (fetchedAutoStart != null && fetchedAutoStart) {
            startTimer()
        }

        // Fetch passed phase argument if it exists
        if (fetchedPhase != null) {
            phase = fetchedPhase
        }

        // Start/pause timer if clicked
        playButton.setOnClickListener {
            (activity as? MainActivity)?.toggleMusic()
            if (!isTimerRunning) {
                startTimer()
            } else if (isTimerRunning) {
                isTimerRunning = false
                timer?.cancel()
            }
        }

        // Reset timer if clicked
        restartButton.setOnClickListener {
            resetTimer()
        }

        // Increment and pass pomodoro phase args and move to the next phase
        nextButton.setOnClickListener {
            val bundle = Bundle()
            phase += 1
            bundle.putInt("phase", phase)

            /*
                Phases:
                0 -> Pomodoro
                1 -> Short Break
                2 -> Pomodoro
                3 -> Long Break
             */

            if (phase == 1) {
                shortBreakFrag.arguments = bundle
                shortBreakFragment()
            } else if (phase == 3) {
                longBreakFrag.arguments = bundle
                longBreakFragment()
            }
        }

        stopBtn.setOnClickListener {
            phase = 0
            Toast.makeText(requireContext(), "Pomodoro session ended", Toast.LENGTH_SHORT).show()
            resetTimer()
        }

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvPomodoro.setTextColor(color)
            tvPomodoroDesc.setTextColor(color)
        })
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "fokus_notification"
            val descriptionText = "fokus_notification_description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            var vibrationPattern = longArrayOf(0, 600, 600, 600)
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                val state = settings.getVibration(requireContext().applicationContext)
                enableVibration(state ?: true)
                vibrationPattern = vibrationPattern
            }

            val notificationManager: NotificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun timerNotification() {
        // Create notification builder
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Fokus")
            .setContentText("Pomodoro timer is over! Take a short break now.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        with(NotificationManagerCompat.from(requireContext())) {
            notify(notificationID, builder.build())
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeft, 1000) {
            // Modify countdown time and update per tick
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimer()
            }

            // Increment phase and move to the next phase if timer hits 0
            override fun onFinish() {
                timerTextView.text = "00:00"
                isTimerRunning = false

                val bundle = Bundle()
                phase += 1
                bundle.putInt("phase", phase)
                bundle.putBoolean("autoStart", true)

                /*
                    Phases:
                    0 -> Pomodoro
                    1 -> Short Break
                    2 -> Pomodoro
                    3 -> Long Break
                */

                if (phase == 1) {
                    shortBreakFrag.arguments = bundle
                    shortBreakFragment()
                } else if (phase == 3) {
                    longBreakFrag.arguments = bundle
                    longBreakFragment()
                }

                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    timerNotification()
                }
            }
        }.start()
        isTimerRunning = true
    }

    // Reset timer
    private fun resetTimer() {
        timer?.cancel()
        if (pmdr.pomodoroMinutes(requireContext()) != null && pmdr.pomodoroSeconds(requireContext()) != null) {
            val minutes = pmdr.pomodoroMinutes(requireContext()) ?: 25
            val seconds = pmdr.pomodoroSeconds(requireContext()) ?: 0
            timeLeft = (minutes * 60 * 1000) + (seconds * 1000)
            updateTimer()
        }

        isTimerRunning = false
    }

    // Update timerTextView
    private fun updateTimer() {
        val seconds = (timeLeft / 1000) % 60
        val minutes = (timeLeft / 1000) / 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    // Redirect to ShortBreakFragment
    private fun shortBreakFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, shortBreakFrag)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    // Redirect to LongBreakFragment
    private fun longBreakFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, longBreakFrag)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    // Cancel countdown on destroy
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}