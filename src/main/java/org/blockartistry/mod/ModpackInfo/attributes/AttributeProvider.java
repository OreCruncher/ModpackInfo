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

package org.blockartistry.mod.ModpackInfo.attributes;

import javax.xml.transform.Transformer;

import org.apache.commons.lang3.StringUtils;

import net.minecraftforge.common.config.Configuration;

/**
 *
 * This abstract class defines the base of any AttributeProvider.
 *
 */
public abstract class AttributeProvider {

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
		return StringUtils.join(".", category, field, orientation);
	}

	/**
	 * Initializes an AttributeProvider based on the content of the
	 * configuration file.
	 * 
	 * @param config
	 *            Reference to the configuration object containing parameter
	 *            information
	 */
	public abstract void init(Configuration config);

	/**
	 * Called by the generation logic to apply attributes to the provider
	 * Transformer.
	 * 
	 * @param transformer
	 *            Transformer to receive the attribute information.
	 */
	public abstract void applyAttributeCodes(Transformer transformer);
}
