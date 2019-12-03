package com.digitalrelay.customviews.views

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.StateSet
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.digitalrelay.customviews.R
import com.digitalrelay.customviews.views.animations.BarToCircleAnimation
import com.digitalrelay.customviews.views.animations.CustomDrawable
//https://www.spaceotechnologies.com/create-android-button-tutorial/
class AnimatedButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatButton(context, attrs, defStyleAttr) {
    private var mDrawableNormal: CustomDrawable = CustomDrawable(GradientDrawable())
    private var mDrawablePressed: CustomDrawable = CustomDrawable(GradientDrawable())
    private var padding: Padding = Padding()
    var animationInProgress: Boolean = false
    private var mHeight = 0
    private var mWidth = 0
    private var mColor = 0
    private var mCornerRadius = 0
    private var mStrokeWidth = 0
    private var mStrokeColor = 0
    var animationToggle: Boolean = false

    init {
        initView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mHeight == 0 && mWidth == 0 && w != 0 && h != 0)
            mHeight = height
            mWidth = width
    }
    fun getDrawableNormal(): CustomDrawable {
        return mDrawableNormal
    }
    fun animation(params: Params) {
        if (!animationInProgress) {
            mDrawablePressed.setColor(params.colorPressed)
            mDrawablePressed.setCornerRadius(params.cornerRadius.toFloat())
            mDrawablePressed.setStrokeColor(params.strokeColor)
            mDrawablePressed.setStrokeWidth(params.strokeWidth)
            if (params.duration == 0) {
                aniBtWithoutAnimation(params)
            } else {
                aniBtWithAnimation(params)
            }
            mColor = params.color
            mCornerRadius = params.cornerRadius
            mStrokeWidth = params.strokeWidth
            mStrokeColor = params.strokeColor
        }
    }
    private fun aniBtWithAnimation(params: Params) {
        animationInProgress = true
        text = ""
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        setPadding(padding.left, padding.top, padding.right, padding.bottom)
        val animationParams: BarToCircleAnimation.Params =
            BarToCircleAnimation.Params(this)
                .color(mColor, params.color)
                .cornerRadius(mCornerRadius, params.cornerRadius)
                .strokeWidth(mStrokeWidth, params.strokeWidth)
                .strokeColor(mStrokeColor, params.strokeColor)
                .height(height, params.height)
                .width(width, params.width)
                .duration(params.duration)
                .listener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        finalizeAnimation(params)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
        val animation = BarToCircleAnimation(animationParams)
        animation.start()
    }
    private fun aniBtWithoutAnimation(params: Params) {
        mDrawableNormal.setColor(params.color)
        mDrawableNormal.setCornerRadius(params.cornerRadius.toFloat())
        mDrawableNormal.setStrokeColor(params.strokeColor)
        mDrawableNormal.setStrokeWidth(params.strokeWidth)
        if (params.width != 0 && params.height != 0) {
            val layoutParams = layoutParams
            layoutParams.width = params.width
            layoutParams.height = params.height
            setLayoutParams(layoutParams)
        }
        finalizeAnimation(params)
    }
    private fun finalizeAnimation(params: Params) {
        animationInProgress = false
        if (params.icon != 0 && params.text.isNotBlank()) {
            setIconLeft(params.icon)
            text = params.text
            gravity = Gravity.CENTER
        } else if (params.icon != 0) {
            setIcon(params.icon)
            gravity = Gravity.CENTER
        } else if (params.text.isNotBlank()) {
            text = params.text
            gravity = Gravity.CENTER
        }
        if (params.animationListener != null) {
            params.animationListener!!.onAnimationEnd(null)
        }
    }
    fun blockTouch() {
        setOnTouchListener { v, event -> true }
    }
    fun unblockTouch() {
        setOnTouchListener { v, event -> false }
    }
    private fun initView() {
       padding = Padding()
       padding.left = paddingLeft
       padding.right = paddingRight
       padding.top = paddingTop
       padding.bottom = paddingBottom
        val cornerRadius = resources.getDimension(R.dimen.bt_corner_radius_2).toInt()
        val purple = resources.getColor(R.color.bt_purple)
        val purpleDark = resources.getColor(R.color.bt_purple_dark)
        val background = StateListDrawable()
        mDrawableNormal = createDrawable(purple, cornerRadius, 0)
        mDrawablePressed = createDrawable(purpleDark, cornerRadius, 0)
        mColor = purple
        mStrokeColor = purple
        mCornerRadius = cornerRadius
        background.addState(intArrayOf(android.R.attr.state_pressed), mDrawablePressed.getGradientDrawable())
        background.addState(StateSet.WILD_CARD, mDrawableNormal.getGradientDrawable())
        setBackgroundCompat(background)
    }
    private fun createDrawable(color: Int, cornerRadius: Int, strokeWidth: Int): CustomDrawable {
        val drawable = CustomDrawable(GradientDrawable())
        drawable.getGradientDrawable().shape = GradientDrawable.RECTANGLE
        drawable.setColor(color)
        drawable.setCornerRadius(cornerRadius.toFloat())
        drawable.setStrokeColor(color)
        drawable.setStrokeWidth(strokeWidth)
        return drawable
    }
    private fun setBackgroundCompat(drawable: Drawable?) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable)
        } else {
            background = drawable
        }
    }
    fun setIcon(@DrawableRes icon: Int) { // post is necessary, to make sure getWidth() doesn't return 0
        post {
            val drawable = ContextCompat.getDrawable(context, icon)
            val padding = width.div(2) - (drawable?.intrinsicWidth?.div(2) ?: 0)
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            setPadding(padding, 0, 0, 0)
        }
    }
    fun setIconLeft(@DrawableRes icon: Int) {
        setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
    }
    fun toCircle(icon: Int?, animationDuration: Int = 500) {
        val circle = Params.create()
            .duration(animationDuration)
            .cornerRadius(resources.getDimension(R.dimen.bt_height_56).toInt())
            .width(resources.getDimension(R.dimen.bt_height_56).toInt())
            .height(resources.getDimension(R.dimen.bt_height_56).toInt())
            .color(ContextCompat.getColor(context, R.color.colorPrimary))
            .colorPressed(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        if (icon != null) {
            circle.icon(icon)
        } else {
            circle.icon(R.drawable.ic_android)
        }
        animation(circle)
    }
    fun toBar(icon: Int?, text: String = "", animationDuration: Int = 500) {
        val bar = Params.create()
            .cornerRadius(resources.getDimension(R.dimen.bt_corner_radius_2).toInt())
            .width(resources.getDimension(R.dimen.bt_width_200).toInt())
            .height(resources.getDimension(R.dimen.bt_height_56).toInt())
            .color(ContextCompat.getColor(context, R.color.bt_purple))
            .colorPressed(ContextCompat.getColor(context, R.color.bt_purple_dark))
            .duration(animationDuration)
        if (text.isNotBlank()) {
            bar.text(text)
        }
        if (icon != null) {
            bar.icon(icon)
//            setIconLeft(icon)
        }
        animation(bar)
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
        internal var animationListener: Animator.AnimatorListener? = null
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
        fun animationListener(animationListener: Animator.AnimatorListener): Params {
            this.animationListener = animationListener
            return this
        }
    }
}