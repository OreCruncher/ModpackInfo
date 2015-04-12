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

package org.blockartistry.mod.ModpackInfo.colorcode;

import javax.xml.transform.Transformer;

import org.blockartistry.mod.ModpackInfo.FormatterType;

import net.minecraftforge.common.config.Configuration;

/**
 * @author OreCruncher
 *
 *         An instance of this class provides basic color support for plain text
 *         output files. Specifically, there will be no special tagging of the
 *         output to cause text attribute changes. Just plain old text.
 *
 */
public class ColorCodeProvider {

	protected static final String TEXT_DEFAULT = "";
	protected static final String TEXT_MODPACK = "modpack";
	protected static final String TEXT_MOD = "mod";

	protected static final String TEXT_NAME = "name";
	protected static final String TEXT_ID = "id";
	protected static final String TEXT_VERSION = "version";
	protected static final String TEXT_WEBSITE = "website";
	protected static final String TEXT_WEBSITE_URL = "websiteURL";
	protected static final String TEXT_DESCRIPTION = "description";
	protected static final String TEXT_AUTHORS = "authors";
	protected static final String TEXT_CREDITS = "credits";
	protected static final String TEXT_ENVIRONMENT = "environment";
	protected static final String TEXT_BEGIN = "begin";
	protected static final String TEXT_END = "end";

	protected static String getKey(String category, String field,
			String orientation) {
		return String.join(".", category, field, orientation);
	}

	public static ColorCodeProvider factory(FormatterType fType) {
		if (fType == FormatterType.BBCODE)
			return new BBCodeColorCodeProvider();

		return new ColorCodeProvider();
	}

	public void init(Configuration config) {
		
		// Do nothing - this provider does not use any codes

	}

	public void applyColorCodes(Transformer transformer) {
		
		// Do nothing - this provider does not use any codes

	}
}
