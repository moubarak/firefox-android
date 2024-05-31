/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.toolbar

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.mozilla.fenix.databinding.FragmentAiSummarizerDialogBinding

class AISummarizerFragment : BottomSheetDialogFragment() {

    internal var _aiSummarizerBinding: FragmentAiSummarizerDialogBinding? = null
    private val aiSummarizerBinding get() = _aiSummarizerBinding!!

    val chatViewModel: ChatViewModel by viewModels {
        ChatViewModel.getFactory(requireContext())
    }

    internal var _url: String? = null
    private val url get() = _url

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _aiSummarizerBinding = FragmentAiSummarizerDialogBinding.inflate(
            inflater,
            container,
            false,
        )
        _url = requireArguments().getString("url")

        val title = aiSummarizerBinding.tldr
        title.text = "tldr"
        title.setTypeface(null, Typeface.BOLD)
        title.textSize = 22F

        val paint: TextPaint = title.paint
        val width = paint.measureText("tldr")
        title.setTextColor(Color.parseColor("#F97C3C"))
        val textShader: Shader = LinearGradient(
            0f, 0f, width, title.textSize,
            intArrayOf(
                Color.parseColor("#F97C3C"),
                Color.parseColor("#F97C3C"),
                Color.parseColor("#F97C3C"),
                Color.parseColor("#64B678"),
            ),
            null, Shader.TileMode.CLAMP,
        )
        paint.setShader(textShader)

        val summary = aiSummarizerBinding.summary

        summary.textAlignment = View.TEXT_ALIGNMENT_CENTER
        summary.textSize = 18F
        summary.setTextColor(Color.parseColor("#F97C3C"))
        chatViewModel.summarize(url.toString())
        return aiSummarizerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val summary = aiSummarizerBinding.summary
        chatViewModel.uiState.observe(this
        ) {
            summary.text = it.summary
        }

    }
}
