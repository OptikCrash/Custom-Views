package com.digitalrelay.customviews.views.animations

import android.animation.*
import androidx.core.animation.addListener
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.digitalrelay.customviews.views.AnimatedButton
import kotlin.properties.Delegates


class BarToCircleAnimation(private val params: Params) {

    open class Params(val button: AnimatedButton) {
        var fromCornerRadius: Float by Delegates.notNull()
        var toCornerRadius: Float by Delegates.notNull()
        var fromHeight: Int by Delegates.notNull()
        var toHeight: Int by Delegates.notNull()
        var fromWidth: Int by Delegates.notNull()
        var toWidth: Int by Delegates.notNull()
        var fromColor: Int by Delegates.notNull()
        var toColor: Int by Delegates.notNull()
        var duration: Int by Delegates.notNull()
        var fromStrokeWidth: Int by Delegates.notNull()
        var toStrokeWidth: Int by Delegates.notNull()
        var fromStrokeColor: Int by Delegates.notNull()
        var toStrokeColor: Int by Delegates.notNull()
        lateinit var animationListener: Animator.AnimatorListener

        open fun duration(aDuration: Int): Params {
            this.duration = aDuration
            return this
        }
        open fun listener(aListener: Animator.AnimatorListener): Params {
            this.animationListener = aListener
            return this
        }
        open fun color(fColor: Int, tColor: Int): Params {
            this.fromColor = fColor
            this.toColor = tColor
            return this
        }
        open fun cornerRadius(fCornerRadius: Int, tCornerRadius: Int): Params {
            this.fromCornerRadius = fCornerRadius.toFloat()
            this.toCornerRadius = tCornerRadius.toFloat()
            return this
        }
        open fun height(fHeight: Int, tHeight: Int): Params {
            this.fromHeight = fHeight
            this.toHeight = tHeight
            return this
        }
        open fun width(fWidth: Int, tWidth: Int): Params {
            this.fromWidth = fWidth
            this.toWidth = tWidth
            return this
        }
        open fun strokeWidth(fStrokeWidth: Int, tStrokeWidth: Int): Params {
            this.fromStrokeWidth = fStrokeWidth
            this.toStrokeWidth = tStrokeWidth
            return this
        }
        open fun strokeColor(fStrokeColor: Int, tStrokeColor: Int): Params {
            this.fromStrokeColor = fStrokeColor
            this.toStrokeColor = tStrokeColor
            return this
        }
    }
    fun start() {
        val background = params.button.getDrawableNormal()
        val cornerAnimation = ObjectAnimator.ofFloat(background, "cornerRadius", params.fromCornerRadius, params.toCornerRadius)
        val strokeWidthAnimation = ObjectAnimator.ofInt(background, "strokeWidth", params.fromStrokeWidth, params.toStrokeWidth)
        val strokeColorAnimation = ObjectAnimator.ofInt(background, "strokeColor", params.fromStrokeColor, params.toStrokeColor)
        val bgColorAnimation = ObjectAnimator.ofInt(background, "color", params.fromColor, params.toColor)
        bgColorAnimation.setEvaluator(ArgbEvaluator())
        val heightAnimation = ValueAnimator.ofInt(params.fromHeight, params.toHeight)
        heightAnimation.addUpdateListener {
            val h = it.animatedValue as Int
            val layoutParams = params.button.layoutParams
            layoutParams.height = h
            params.button.layoutParams = layoutParams
        }
        val widthAnimation = ValueAnimator.ofInt(params.fromWidth, params.toWidth)
        widthAnimation.addUpdateListener {
            val w = it.animatedValue as Int
            val layoutParams = params.button.layoutParams
            layoutParams.width = w
            params.button.layoutParams = layoutParams
        }
        val animatorSet = AnimatorSet()
        animatorSet.duration = params.duration.toLong()
        animatorSet.playTogether(strokeWidthAnimation, strokeColorAnimation, cornerAnimation, bgColorAnimation, heightAnimation, widthAnimation)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (params.animationListener != null) {
                    params.animationListener.onAnimationEnd(animation)
                }
            }
        })
        animatorSet.start()
    }
}