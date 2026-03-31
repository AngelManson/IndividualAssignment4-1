package com.example.individualassignment4_1

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.individualassignment4_1.ui.theme.IndividualAssignment41Theme
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var ballX by mutableStateOf(0f)
    private var ballY by mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        setContent {
            IndividualAssignment41Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(ballX = ballX, ballY = ballY)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val ax = it.values[0]
                val ay = it.values[1]
                ballX = ax
                ballY = ay

//                val speed = 100f
//                ballX -= speed * ax
//                ballY += speed * ay
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun GameScreen(ballX: Float, ballY: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        var newBallX by remember { mutableStateOf(250f) }
        var newBallY by remember { mutableStateOf(400f) }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

//            newBallX -= ballX * 30f
//            newBallY += ballY * 40f

            val clampedX = max(20f, min((newBallX - ballX * 15f), canvasWidth - 20f))
            val clampedY = max(20f, min((newBallY + ballY * 15f), canvasHeight - 20f))

            val obstacles = listOf(
                androidx.compose.ui.geometry.Rect(300f, 500f, 950f, 520f),
                androidx.compose.ui.geometry.Rect(50f, 500f, 70f, 1350f),
                androidx.compose.ui.geometry.Rect(950f, 500f, 970f, 1360f),
                androidx.compose.ui.geometry.Rect(50f, 1350f, 600f, 1370f),
                androidx.compose.ui.geometry.Rect(600f, 1150f, 950f, 1170f),
                androidx.compose.ui.geometry.Rect(300f, 500f, 320f, 750f),
                androidx.compose.ui.geometry.Rect(70f, 970f, 420f, 990f),
                androidx.compose.ui.geometry.Rect(300f, 750f, 650f, 770f)
            )

            fun collides(x: Float, y: Float): Boolean {
                val ballRect = androidx.compose.ui.geometry.Rect(
                    x - 20f,
                    y - 20f,
                    x + 20f,
                    y + 20f
                )
                return obstacles.any { it.overlaps(ballRect) }
            }

            if (!collides(clampedX, newBallY)) {
                newBallX = clampedX
            }

            if (!collides(newBallX, clampedY)) {
                newBallY = clampedY
            }

            drawRect(
                color = Color.Black,
                topLeft = Offset(300f, 500f),
                size = androidx.compose.ui.geometry.Size(650f, 20f)
            )
            drawRect(
                color = Color.Black,
                topLeft = Offset(50f, 500f),
                size = androidx.compose.ui.geometry.Size(20f, 850f)
            )
            drawRect(
                color = Color.Black,
                topLeft = Offset(950f, 500f),
                size = androidx.compose.ui.geometry.Size(20f, 860f)
            )
            drawRect(
                color = Color.Black,
                topLeft = Offset(50f, 1350f),
                size = androidx.compose.ui.geometry.Size(550f, 20f)
            )


            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(600f, 1150f),
                size = androidx.compose.ui.geometry.Size(350f, 20f)
            )
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(300f, 500f),
                size = androidx.compose.ui.geometry.Size(20f, 250f)
            )
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(70f, 970f),
                size = androidx.compose.ui.geometry.Size(350f, 20f)
            )
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(300f, 750f),
                size = androidx.compose.ui.geometry.Size(350f, 20f)
            )


            drawCircle(
                color = Color.Magenta,
                radius = 20f,
                center = Offset(newBallX, newBallY)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "Tilt your phone to move the ball!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IndividualAssignment41Theme {
        GameScreen(ballX = 0f, ballY = 0f)
    }
}