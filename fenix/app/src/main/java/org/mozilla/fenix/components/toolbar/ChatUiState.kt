/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.toolbar

class GemmaUiState(
    summary: String = ""
) {

    private var _summary: String = summary
    val summary get() = _summary

    /**
     * Prompt engineering
     */
    val fullPrompt: String
        get() = "Provide a funny summary in one sentence from this url"
}
