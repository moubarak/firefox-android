/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.toolbar

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.text.TextPaint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mozilla.components.concept.toolbar.Toolbar
import mozilla.components.support.ktx.android.content.res.resolveAttribute

class PageContentSummarizerToolbarButton(
    private val summarizePage: () -> Unit
) : Toolbar.Action {

    @SuppressLint("SetTextI18n")
    override fun createView(parent: ViewGroup): View {
        // TODO: Replace with ImageView
        val tldr = TextView(parent.context).apply { text = "tldr" }
        tldr.gravity = Gravity.CENTER
        tldr.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tldr.setTypeface(null, Typeface.BOLD)
        tldr.textSize = 16F

        val paint: TextPaint = tldr.paint
        val width = paint.measureText("tldr")
        tldr.setTextColor(Color.parseColor("#F97C3C"))
        val textShader: Shader = LinearGradient(
            0f, 0f, width, tldr.textSize,
            intArrayOf(
                Color.parseColor("#F97C3C"),
                Color.parseColor("#F97C3C"),
                Color.parseColor("#F97C3C"),
                Color.parseColor("#64B678"),
            ),
            null, Shader.TileMode.CLAMP,
        )
        paint.setShader(textShader)
        tldr.setOnClickListener {
            summarizePage.invoke()
        }
        tldr.setBackgroundResource(
            parent.context.theme.resolveAttribute(
                android.R.attr.selectableItemBackgroundBorderless,
            ),
        )

        return tldr
    }

    override fun bind(view: View) = Unit
}
