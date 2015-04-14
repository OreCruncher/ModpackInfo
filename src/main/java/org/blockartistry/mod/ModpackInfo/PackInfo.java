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

import net.minecraftforge.common.config.Configuration;

import com.google.common.base.Preconditions;

/**
 * 
 * PackInfo will hold modpack description information obtained from the mod
 * configuration file. This data is specified by the modpack author.
 *
 */
public final class PackInfo {

	private static final String CATEGORY_MODPACK_INFO = "modpackinfo";

	private static String modpackName;
	private static String modpackVersion;
	private static String modpackDescription;
	private static String modpackCredits;
	private static String modpackAuthors;
	private static String modpackWebsite;

	/**
	 * @return The name of the modpack as defined in the configuration file
	 */
	public String getName() {
		return modpackName;
	}

	/**
	 * @return Indicates whether the modpack name is valid
	 */
	public boolean hasValidName() {
		return modpackName != null && !modpackName.isEmpty();
	}

	/**
	 * @return The version of the modpack as defined in the configuration file
	 */
	public String getVersion() {
		return modpackVersion;
	}

	/**
	 * @return Indicates whether the modpack version is valid
	 */
	public boolean hasValidVersion() {
		return modpackVersion != null && !modpackVersion.isEmpty();
	}

	/**
	 * @return The description of the modpack as defined in the configuration
	 *         file
	 */
	public String getDescription() {
		return modpackDescription;
	}

	/**
	 * @return Indicates whether the modpack description is valid
	 */
	public boolean hasValidDescription() {
		return modpackDescription != null && !modpackDescription.isEmpty();
	}

	/**
	 * @return The credits of the modpack as defined in the configuration file
	 */
	public String getCredits() {
		return modpackCredits;
	}

	/**
	 * @return Indicates whether the modpack credits is valid
	 */
	public boolean hasValidCredits() {
		return modpackCredits != null && !modpackCredits.isEmpty();
	}

	/**
	 * @return The authors of the modpack as defined in the configuration file
	 */
	public String getAuthors() {
		return modpackAuthors;
	}

	/**
	 * @return Indicates whether the modpack authors is valid
	 */
	public boolean hasValidAuthors() {
		return modpackAuthors != null && !modpackAuthors.isEmpty();
	}

	/**
	 * @return The website of the modpack as defined in the configuration file
	 */
	public String getWebsite() {
		return modpackWebsite;
	}

	/**
	 * @return Indicates whether the modpack website is valid
	 */
	public boolean hasValidWebsite() {
		return modpackWebsite != null && !modpackWebsite.isEmpty();
	}

	public PackInfo() {
	}

	/**
	 * Initializes a PackInfo instances from the specified configuration file.
	 * 
	 * @param config
	 */
	public static void init(Configuration config) {

		Preconditions.checkNotNull(config);

		modpackName = (config.get(CATEGORY_MODPACK_INFO, "name", "",
				"Name of the modpack").getString());
		modpackVersion = (config.get(CATEGORY_MODPACK_INFO, "version", "",
				"Version of the modpack").getString());
		modpackDescription = (config.get(CATEGORY_MODPACK_INFO, "description",
				"", "Description of the modpack").getString());
		modpackCredits = (config.get(CATEGORY_MODPACK_INFO, "credits", "",
				"Credits of the modpack").getString());
		modpackAuthors = String.join(",",
				(config.getStringList("authors", CATEGORY_MODPACK_INFO,
						new String[0], "Authors of the modpack")));
		modpackWebsite = (config.get(CATEGORY_MODPACK_INFO, "website", "",
				"Modpack web address").getString());
	}
}
