package com.digitalrelay.customviews.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.digitalrelay.customviews.R
import com.google.android.material.button.MaterialButton


class NaceyButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MaterialButton(context, attrs, defStyleAttr) {
    private var padding: Padding = Padding()

    init {
        padding.left = paddingLeft
        padding.right = paddingRight
        padding.top = paddingTop
        padding.bottom = paddingBottom
        if (text.isNotBlank() && icon != null) {
            if (iconGravity == ICON_GRAVITY_TEXT_START) {
                val drawable = ContextCompat.getDrawable(context, R.drawable.btn_start_icon)
                val color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                this.background = getBackgroundDrawable(color, drawable)
            } else if (iconGravity == ICON_GRAVITY_TEXT_END) {
                val drawable = ContextCompat.getDrawable(context, R.drawable.btn_end_icon)
                val color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                this.background = getBackgroundDrawable(color, drawable)
            }
        } else if (text.isNullOrBlank() && icon != null) {
            this.setPadding(36,0,0,0)
            this.height = 112
            this.width = 112
            val drawable = ContextCompat.getDrawable(context, R.drawable.btn_only_icon)
            val color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            this.background = getBackgroundDrawable(color, drawable)
        }
    }

    private fun getBackgroundDrawable(pressedColor: Int, backgroundDrawable: Drawable?): RippleDrawable? {
        return RippleDrawable(getPressedState(pressedColor)!!, backgroundDrawable, null)
    }

    private fun getPressedState(pressedColor: Int): ColorStateList? {
        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))
    }



    open class Padding(var left: Int = 0, var right: Int = 0, var top: Int = 0, var bottom: Int = 0)
    open class Params {
        internal var cornerRadius: Int = 0
        internal var width: Int = 0
        internal var height: Int = 0
        internal var color: Int = 0
        internal var colorPressed: Int = 0
        internal var duration: Int = 0
        internal var icon: Int = 0
        internal var strokeWidth: Int = 0
        internal var strokeColor: Int = 0
        internal var text: String = ""
        companion object {
            fun create(): Params { return Params() }
        }
        fun text(text: String): Params {
            this.text = text
            return this
        }
        fun icon(@DrawableRes icon: Int): Params {
            this.icon = icon
            return this
        }
        fun cornerRadius(cornerRadius: Int): Params {
            this.cornerRadius = cornerRadius
            return this
        }
        fun width(width: Int): Params {
            this.width = width
            return this
        }
        fun height(height: Int): Params {
            this.height = height
            return this
        }
        fun color(color: Int): Params {
            this.color = color
            return this
        }
        fun colorPressed(colorPressed: Int): Params {
            this.colorPressed = colorPressed
            return this
        }
        fun duration(duration: Int): Params {
            this.duration = duration
            return this
        }
        fun strokeWidth(strokeWidth: Int): Params {
            this.strokeWidth = strokeWidth
            return this
        }
        fun strokeColor(strokeColor: Int): Params {
            this.strokeColor = strokeColor
            return this
        }
    }
}