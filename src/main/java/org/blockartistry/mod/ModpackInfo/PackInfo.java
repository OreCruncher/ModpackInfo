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

public class PackInfo {

	private static final String CATEGORY_MODPACK_INFO = "modpackinfo";

	private String modpackName;
	private String modpackVersion;
	private String modpackDescription;
	private String modpackCredits;
	private String modpackAuthors;
	private String modpackWebsite;

	public String getName() {
		return modpackName;
	}

	public void setName(String name) {
		modpackName = name;
	}

	public boolean hasValidName() {
		return modpackName != null && !modpackName.isEmpty();
	}

	public String getVersion() {
		return modpackVersion;
	}

	public void setVersion(String version) {
		modpackVersion = version;
	}

	public boolean hasValidVersion() {
		return modpackVersion != null && !modpackVersion.isEmpty();
	}

	public String getDescription() {
		return modpackDescription;
	}

	public void setDescription(String description) {
		modpackDescription = description;
	}

	public boolean hasValidDescription() {
		return modpackDescription != null && !modpackDescription.isEmpty();
	}

	public String getCredits() {
		return modpackCredits;
	}

	public void setCredits(String credits) {
		modpackCredits = credits;
	}

	public boolean hasValidCredits() {
		return modpackCredits != null && !modpackCredits.isEmpty();
	}

	public String getAuthors() {
		return modpackAuthors;
	}

	public void setAuthors(String[] authors) {
		modpackAuthors = String.join(", ", authors);
	}

	public boolean hasValidAuthors() {
		return modpackAuthors != null && !modpackAuthors.isEmpty();
	}

	public String getWebsite() {
		return modpackWebsite;
	}

	public void setWebsite(String website) {
		modpackWebsite = website;
	}

	public boolean hasValidWebsite() {
		return modpackWebsite != null && !modpackWebsite.isEmpty();
	}

	public PackInfo() {
		modpackName = null;
		modpackVersion = null;
		modpackDescription = null;
		modpackCredits = null;
		modpackAuthors = null;
		modpackWebsite = null;
	}

	public void init(Configuration config) {

		setName(config.get(CATEGORY_MODPACK_INFO, "name", "",
				"Name of the modpack").getString());
		setVersion(config.get(CATEGORY_MODPACK_INFO, "version", "",
				"Version of the modpack").getString());
		setDescription(config.get(CATEGORY_MODPACK_INFO, "description", "",
				"Description of the modpack").getString());
		setCredits(config.get(CATEGORY_MODPACK_INFO, "credits", "",
				"Credits of the modpack").getString());
		setAuthors(config.getStringList("authors", CATEGORY_MODPACK_INFO,
				new String[0], "Authors of the modpack"));
		setWebsite(config.get(CATEGORY_MODPACK_INFO, "website", "",
				"Modpack web address").getString());
	}
}
