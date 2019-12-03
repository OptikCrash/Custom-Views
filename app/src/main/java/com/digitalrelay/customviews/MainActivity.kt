package com.digitalrelay.customviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.digitalrelay.customviews.views.AnimatedButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_animated.toBar(null, "Test Animation")
        btn_right.toCircle(R.drawable.ic_add_circle)
        btn_right.animationToggle = true
        setButtons()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setButtons() {
        btn_start.setOnClickListener {
            timer_1.start()
        }
        btn_stop.setOnClickListener {
            timer_1.stop()
        }
        btn_animated.setOnClickListener {
            if (btn_animated.animationToggle) {
                btn_animated.animationToggle = false
                btn_animated.toBar(null, "Animated Button")
            } else {
                btn_animated.animationToggle = true
                btn_animated.toCircle(R.drawable.ic_add_circle)
            }
        }
        btn_right.setOnClickListener {
            if (btn_right.animationToggle) {
                btn_right.toBar(R.drawable.ic_android, "Test with icon")
            } else {
                btn_right.toCircle(R.drawable.ic_android)
            }
            btn_right.animationToggle = !btn_right.animationToggle
        }
    }
}
