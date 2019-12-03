package com.digitalrelay.customviews.views.animations

import android.graphics.drawable.GradientDrawable

class CustomDrawable(private val drawable: GradientDrawable) {
    private var sWidth: Int = 0
    private var sColor: Int = 0
    private var mRadius: Float = 0f
    private var mColor: Int = 0

    fun getGradientDrawable(): GradientDrawable  { return drawable }
    fun getStrokeWidth(): Int { return sWidth }
    fun setStrokeWidth(strokeWidth: Int) {
        sWidth = strokeWidth
        drawable.setStroke(strokeWidth, getStrokeColor())
    }
    fun getStrokeColor(): Int { return sColor }
    fun setStrokeColor(strokeColor: Int) { sColor = strokeColor }
    fun getRadius(): Float { return mRadius }
    fun setCornerRadius(radius: Float) {
        mRadius = radius
        drawable.cornerRadius = radius
    }
    fun getColor(): Int { return mColor }
    fun setColor(color: Int) {
        mColor = color
        drawable.setColor(color)
    }
}