package com.irfan.dtonton.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RvHorizontalHelper {
    fun rvLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false,
        )
    }

    fun rvItemDecoration(spacing: Int): RecyclerView.ItemDecoration {
        return object :
            RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State,
            ) {
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.left = spacing
                }
            }
        }
    }
}