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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import net.minecraft.util.StringTranslate;

import org.blockartistry.mod.ModpackInfo.Assets;

/**
 * LanguagePack manages the language translations for the mod. The strings for
 * multiple languages can be loaded at one time. Application code can look up a
 * translation for any supported language for whatever purpose they may have.
 *
 */
public class LanguagePack {

	private static final HashMap<String, LanguagePack> packs = new HashMap<String, LanguagePack>();

	protected HashMap<String, String> translations = new HashMap<String, String>();

	protected LanguagePack(HashMap<String, String> translations) {
		this.translations = translations;

		for (Entry<String, String> e : this.translations.entrySet()) {
			this.translations.put(e.getKey(),
					e.getValue().replaceAll("\\\\r|\\\\n", "\n"));
		}
	}

	/**
	 * Obtains a LanguagePack for a specific locale.
	 * 
	 * @param locale
	 *            Locale for which translations will be retrieved
	 * @return LanguagePack for the specified locale
	 */
	public static LanguagePack getLanguagePack(Locale locale) {

		return getLanguagePack(locale.toString());
	}

	/**
	 * Obtains a LanguagePack for a specific locale.
	 * 
	 * @param localeString
	 *            String containing the language for which translations will be
	 *            retrieved
	 * @return LanguagePack for the specified locale
	 */
	public static LanguagePack getLanguagePack(String localeString) {

		LanguagePack result = packs.get(localeString);
		if (result == null) {
			result = packs.get(Locale.US.toString());
		}

		return result;
	}
	
	/**
	 * @return The LanguagePack based on the locale of the local system
	 */
	public static LanguagePack getLocalLanguagePack() {
		return getLanguagePack(Locale.getDefault());
	}

	/**
	 * Attempts to find a string based on the format ID provided. The result
	 * string is for the specified locale. If it is not found then one is
	 * returned from the US language if present.
	 * 
	 * @param locale
	 *            The locale for the desired string
	 * @param formatId
	 *            The id of the string to locate
	 * @param parms
	 *            Optional parameters to complete the format request
	 * @return A string matching the request if found, or the format ID if not
	 */
	public static String translate(Locale locale, String formatId,
			Object... parms) {

		return getLanguagePack(locale).translate(formatId, parms);
	}

	/**
	 * Attempts to find a string based on the default locale of the VM. If it is
	 * not found then one is returned from the US language if present.
	 * 
	 * @param formatId
	 *            The id of the string to locate
	 * @param parms
	 *            Optional parameters to complete the format request
	 * @return A string matching the request if found, or the format ID if not
	 */
	public String translate(String formatId, Object... parms) {

		String s = translations.getOrDefault(formatId, formatId);
		return String.format(s, parms);
	}

	private static void inject(Locale locale) {

		InputStream is = Assets.getLanguageFile(locale);

		if (is != null) {
			HashMap<String, String> strings = StringTranslate.parseLangFile(is);
			packs.put(locale.toString(), new LanguagePack(strings));
		}
	}

	static {

		// The US locale is always present in the mod, so it gets loaded. This
		// becomes the goto locale if a string is not found in another language.
		inject(Locale.US);
	}
}
