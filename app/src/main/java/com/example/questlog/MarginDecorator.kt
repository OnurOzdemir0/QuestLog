package com.example.questlog

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MarginDecorator(val bottomMargin : Int = 0,
                      val topMargin : Int = 0,
                      val leftMargin : Int = 0,
                      val rightMargin : Int = 0) : RecyclerView.ItemDecoration() {


    override  fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = bottomMargin
        outRect.top = topMargin
        outRect.left = leftMargin
        outRect.right = rightMargin
    }
}