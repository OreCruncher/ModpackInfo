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

package org.blockartistry.mod.ModpackInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.minecraft.launchwrapper.Launch;

import com.google.common.base.Preconditions;

/**
 * 
 * A set of helper methods that facilitates access to resource information used
 * by the mod. It handles the differences of accessing resources between the
 * development environment and the jar file.
 *
 */
public final class Assets {

	private static final String PATH_SEP = "/";
	private static final String ASSET_ROOT = "/assets";
	private static final String ASSET_ROOT_DEV = "/";
	private static final String SHEET_RESOURCE_PATH = "xlatesheets";
	private static final String SHEET_RESOURCE_EXTENSION = ".xsl";
	private static final String LANG_RESOURCE_PATH = "lang";
	private static final String LANG_RESOURCE_EXTENSION = ".lang";

	private static final boolean developmentEnvironment = (Boolean) Launch.blackboard
			.get("fml.deobfuscatedEnvironment");

	private static String assetPath = null;

	protected static String combine(String... frags) {

		if (frags.length == 1) {
			return frags[0];
		}

		StringBuilder builder = new StringBuilder();
		boolean sawOne = false;
		boolean endInSlash = false;

		for (String f : frags) {

			if (f != null && !f.isEmpty()) {
				if (sawOne)
					if (!endInSlash)
						builder.append(PATH_SEP);

				sawOne = true;

				builder.append(f);
				endInSlash = f.endsWith(PATH_SEP);
			}
		}

		return builder.toString();
	}

	/**
	 * Indicates if the mod is running in a development environment (e.g. an
	 * IDE).
	 * 
	 * @return true if the mod is running in a development environment, false
	 *         otherwise.
	 */
	public static boolean runningAsDevelopment() {
		return developmentEnvironment;
	}

	/**
	 * Determines the root of the asset resource path to use. It takes into
	 * account whether or not the mod is running in a development environment.
	 * 
	 * @return Root path to assets for the mod
	 */
	public static String getAssetsRootPath() {

		if (assetPath == null) {
			if (developmentEnvironment)
				assetPath = ASSET_ROOT_DEV;
			else
				assetPath = combine(ASSET_ROOT, ModpackInfo.MOD_ID);
		}
		return assetPath;
	}

	/**
	 * Helper method that calculates a path to the specified asset. This method
	 * assumes that the assetFolder is off the asset root path.
	 * 
	 * @param assetFolder
	 *            The path fragment where the resource can be found
	 * @param asset
	 *            The name of the asset that is of interest
	 * @return A fully specified path to the asset from the asset root
	 */
	public static String getAssetPath(String assetFolder, String asset) {
		Preconditions.checkNotNull(assetFolder);
		Preconditions.checkNotNull(asset);

		if (runningAsDevelopment())
			return combine(getAssetsRootPath(), asset);
		return combine(getAssetsRootPath(), assetFolder, asset);
	}

	/**
	 * Returns a reader for the Xsl translation sheet associated with the
	 * specified formatter.
	 * 
	 * @param formatter
	 *            Formatter that is of interest
	 * @return BufferedReader for the associated Xsl
	 */
	public static BufferedReader getTransformSheet(OutputType formatter) {
		Preconditions.checkNotNull(formatter);

		String assetPath = getAssetPath(SHEET_RESOURCE_PATH,
				formatter.getXslFileName() + SHEET_RESOURCE_EXTENSION);

		InputStream in = ModpackInfo.instance.getClass().getResourceAsStream(
				assetPath);

		if (in == null)
			return null;

		return new BufferedReader(new InputStreamReader(in));
	}

	public static InputStream getLanguageFile(String locale) {
		Preconditions.checkNotNull(locale);

		String assetPath = getAssetPath(LANG_RESOURCE_PATH, locale
				+ LANG_RESOURCE_EXTENSION);

		InputStream in = ModpackInfo.instance.getClass().getResourceAsStream(
				assetPath);

		return in;
	}
}
