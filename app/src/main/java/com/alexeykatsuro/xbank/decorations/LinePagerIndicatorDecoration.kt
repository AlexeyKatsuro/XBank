package com.alexeykatsuro.materingrecycler

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.R.attr.left




class LinePagerIndicatorDecoration : RecyclerView.ItemDecoration() {

    val DP = Resources.getSystem().getDisplayMetrics().density
    private val indicatorHeight = 16 * DP
    private val indicatorStrokeWidth = 2 * DP
    private val indicatorItemPadding = 4 * DP
    private val indicatorItemLength = 16 * DP
    private val highlightColor: Int = 0xFFFF0000.toInt()
    private val regularColor: Int = Color.BLACK
    private val interpolator = AccelerateDecelerateInterpolator()

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = indicatorStrokeWidth
        color = Color.BLACK

    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight.toInt()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = state.itemCount
        val totalLength = calculateIndicatorLength(itemCount)
        val startX = (parent.width - totalLength) / 2f
        val startY = parent.height - indicatorHeight / 2f
        drawIndicator(c, startX, startY, itemCount)
//drawIndicator(c,startX,startY,itemCount)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left.toFloat()
        val width = activeChild.width.toFloat()
        val progress = interpolator.getInterpolation(((-left) / width))*(indicatorItemLength+ indicatorItemPadding)
        Log.d("------", "progress interpolator: $progress")
       // drawHighlights(c,startX, startY,activePosition,progress1,itemCount)
        drawHighlightIndicator(c, startX, startY, activePosition, progress, itemCount)
    }

    private fun drawIndicator(c: Canvas, startX: Float, startY: Float, itemCount: Int) {
        val step = indicatorItemLength + indicatorItemPadding
        paint.color = regularColor
        repeat(itemCount) { index ->
            c.drawLine(
                startX + step * index,
                startY,
                startX + indicatorItemLength + step * index,
                startY,
                paint
            )
        }
    }

    private fun drawHighlightIndicator(
        c: Canvas,
        startX: Float,
        startY: Float,
        activePosition: Int,
        progress: Float,
        itemCount: Int
    ) {
        paint.color = highlightColor
        val step = indicatorItemLength + indicatorItemPadding
        var offset = progress
        //first line piece
        if (offset<indicatorItemLength) {
            c.drawLine(
                startX + step * activePosition + offset,
                startY,
                startX + indicatorItemLength + step * activePosition,
                startY,
                paint
            )
        }
        //second line piece
        offset-=indicatorItemPadding
        if (offset > 0f) {
            c.drawLine(
                startX + step * (activePosition + 1),
                startY,
                startX + step * (activePosition + 1) + offset,
                startY,
                paint
            )
        }
    }

    private fun calculateIndicatorLength(itemCount: Int): Float {
        if (itemCount <= 0) return 0f
        return itemCount * indicatorItemLength + (itemCount - 1) * indicatorItemPadding
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        paint.setColor(regularColor)

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY, start + indicatorItemLength, indicatorPosY, paint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int
    ) {
        paint.setColor(highlightColor)

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawLine(
                highlightStart, indicatorPosY,
                highlightStart + indicatorItemLength, indicatorPosY, paint
            )
        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength =  progress

            // draw the cut off highlight
            c.drawLine(
                highlightStart + partialLength, indicatorPosY,
                highlightStart + indicatorItemLength, indicatorPosY, paint
            )

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth
                c.drawLine(
                    highlightStart, indicatorPosY,
                    highlightStart + partialLength, indicatorPosY, paint
                )
            }
        }
    }
}