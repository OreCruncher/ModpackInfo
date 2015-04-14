/*
 * This file is part of ModpackInfo, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.ModpackInfo.localization;

/**
 * TextBuilder is a helper class that makes it easy to generate strings based on
 * localized resources.
 *
 */
public class TextBuilder {
	
	private static final String NEWLINE = "\n";

	protected LanguagePack language;
	protected StringBuilder builder = new StringBuilder();

	public TextBuilder(LanguagePack pack) {

		this.language = pack;
	}

	public TextBuilder append(String formatId, Object... parms) {
		builder.append(language.translate(formatId, parms));
		return this;
	}

	public TextBuilder appendLiteral(String format, Object... parms) {
		builder.append(String.format(format, parms));
		return this;
	}

	public TextBuilder appendNewline() {
		builder.append(NEWLINE);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
