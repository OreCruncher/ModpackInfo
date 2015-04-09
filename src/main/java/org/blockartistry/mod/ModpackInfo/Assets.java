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

public class Assets {

	private static final boolean developmentEnvironment = (Boolean) Launch.blackboard
			.get("fml.deobfuscatedEnvironment");

	private static String assetPath = null;

	protected static String combine(String... frags) {
		StringBuilder builder = new StringBuilder();
		boolean sawOne = false;
		boolean endInSlash = false;

		for (String f : frags) {

			if (f != null && !f.isEmpty()) {
				if (sawOne)
					if (!endInSlash)
						builder.append("/");

				sawOne = true;

				builder.append(f);
				endInSlash = f.endsWith("/");
			}
		}

		return builder.toString();
	}

	public static boolean runningAsDevelopment() {
		return developmentEnvironment;
	}

	public static String getAssetsRootPath() {

		if (assetPath == null) {
			if (developmentEnvironment)
				assetPath = "/";
			else
				assetPath = combine("/assets", ModpackInfo.MOD_ID);
		}
		return assetPath;
	}

	public static String getAssetPath(String assetFolder, String asset) {
		if (runningAsDevelopment())
			return combine(getAssetsRootPath(), asset);
		return combine(getAssetsRootPath(), assetFolder, asset);
	}

	public static BufferedReader getTransformSheet(FormatterType formatter) {

		String assetPath = getAssetPath("xlatesheets",
				formatter.getXslFileName() + ".xsl");

		InputStream in = ModpackInfo.instance.getClass().getResourceAsStream(
				assetPath);

		if (in == null)
			return null;

		return new BufferedReader(new InputStreamReader(in));
	}
}
