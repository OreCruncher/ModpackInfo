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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.blockartistry.mod.ModpackInfo.Assets;

import com.google.common.base.Splitter;

/**
 * LanguagePack manages the language translations for the mod. The strings for
 * multiple languages can be loaded at one time. Application code can look up a
 * translation for any supported language for whatever purpose they may have.
 * 
 * At the heart the logic uses MessageFormat to do the necessary processing of
 * localized strings. This means that the text in the lang files need to use a
 * different syntax for insertion points than the normal C like String format
 * codes. See en_US.lang for examples.
 *
 */
public class LanguagePack {

	public static final Locale LANGUAGE_PACK_DEFAULT = Locale.US;
	private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);

	private static final HashMap<String, LanguagePack> packs = new HashMap<String, LanguagePack>();

	protected HashMap<String, String> translations = new HashMap<String, String>();
	protected Locale locale;

	protected LanguagePack(HashMap<String, String> translations, Locale locale) {

		this.translations = translations;
		this.locale = locale;
	}

	/**
	 * Obtains a LanguagePack for a specific locale.
	 * 
	 * @param locale
	 *            Locale for which translations will be retrieved
	 * @return LanguagePack for the specified locale
	 */
	public static LanguagePack getLanguagePack(Locale locale) {

		String langToGet = locale.toString();

		// Get the pack from our cached list
		LanguagePack result = packs.get(langToGet);

		// If we didn't get a hit..
		if (result == null) {

			// ...try loading it from resources
			if (inject(locale)) {
				// Successful load - get it from our list
				result = packs.get(langToGet);
			} else {
				// Couldn't load it, so go with the default pack. We make a map
				// entry as to this fact so logic can avoid repeated attempts
				// at loading a language resource that is going to fail.
				result = packs.get(LANGUAGE_PACK_DEFAULT.toString());
				packs.put(langToGet, result);
			}
		}

		return result;
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

		String[] tokens = localeString.split("_");
		return getLanguagePack(new Locale(tokens[0], tokens[1]));
	}

	/**
	 * @return The Locale of the language pack
	 */
	public Locale getLanguagePackLocale() {
		return locale;
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
		return (new MessageFormat(s, locale)).format(parms);
	}

	private static HashMap<String, String> parseLangFile(InputStream inputstream) {
		HashMap<String, String> table = new HashMap<String, String>();
		try {
			Iterator<String> iterator = IOUtils.readLines(inputstream,
					Charsets.UTF_8).iterator();

			while (iterator.hasNext()) {
				String s = iterator.next();

				// The '#' is a comment
				if (!s.isEmpty() && s.charAt(0) != '#') {
					List<String> astring = equalSignSplitter.splitToList(s);

					// When processing the string value we need to make sure
					// that newline tokens are corrected.
					if (astring != null && astring.size() == 2) {
						table.put(astring.get(0),
								astring.get(1).replaceAll("\\\\r|\\\\n", "\n"));
					}
				}
			}

		} catch (Exception ioexception) {
			ioexception.printStackTrace();
		}
		
		return table;
	}

	private static boolean inject(Locale locale) {

		InputStream is = Assets.getLanguageFile(locale.toString());

		if (is == null)
			return false;
			
		HashMap<String, String> strings = parseLangFile(is);
		packs.put(locale.toString(), new LanguagePack(strings, locale));
		
		is = null;
		return true;
	}

	static {

		// The US locale is always present in the mod, so it gets loaded. This
		// becomes the goto locale if a string is not found in another language.
		inject(LANGUAGE_PACK_DEFAULT);
	}
}
