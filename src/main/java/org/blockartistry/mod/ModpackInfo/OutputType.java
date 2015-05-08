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

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.ModpackInfo.attributes.AttributeProviderType;

import com.google.common.base.Preconditions;

/**
 *
 * Defines the various output file formats that are supported. For each value
 * there are two properties that can be specified:
 * 
 * xsl: the name of the translation sheet defined in resources that will be used
 * to perform a transformation when writing information to the output log.
 * Specifying null will default to "text".
 * 
 * ext: the filename extension that will be used when writing the output file to
 * disk. Specifying null will default to "txt".
 *
 */
public enum OutputType {

	/**
	 * TEXT causes the output to be generated in plain text
	 */
	TEXT("decorated", "txt", AttributeProviderType.TEXT),

	/**
	 * The output is generated with BBCode tags suitable for Internet forums
	 * that support BBCode.
	 */
	BBCODE("decorated", "txt", AttributeProviderType.BBCODE),

	/**
	 * The output is generated as XML. It is assumed that some other post
	 * process will pick up the file.
	 */
	XML("xml", "xml", AttributeProviderType.NONE),

	/**
	 * The output is generated as JSON.
	 */
	JSON("json", "json", AttributeProviderType.NONE);

	private static final HashMap<String, OutputType> nameMapping = new HashMap<String, OutputType>();
	private static final String nameString;

	private final String xsl;
	private final String ext;
	private final AttributeProviderType attributeProvider;

	/**
	 * @return The name of the Xsl transform sheet to use when transforming for
	 *         this output type.
	 */
	public String getXslFileName() {
		return xsl;
	}

	/**
	 * @return The filename extension to use when creating the output file on
	 *         disk.
	 */
	public String getFileNameExtension() {
		return ext;
	}

	/**
	 * @return The type of AttributeProvider to use when generating output for
	 *         this type
	 */
	public AttributeProviderType getAttributeProviderType() {
		return attributeProvider;
	}

	/**
	 * @return The friendly name of the type
	 */
	public String getFriendlyName() {
		return this.name().toLowerCase();
	}

	private OutputType() {
		this(null, null, AttributeProviderType.TEXT);
	}

	private OutputType(String xsl, String ext, AttributeProviderType ctype) {
		Preconditions.checkNotNull(ctype);

		this.xsl = xsl == null ? "text" : xsl;
		this.ext = ext == null ? "txt" : ext;
		this.attributeProvider = ctype;
	}

	/**
	 * Returns an OutputType based on the friendly name specified.
	 * 
	 * @param name
	 *            Friendly name to convert to OutputType
	 * @return OutputType that matches the friendly name
	 */
	public static OutputType getValueByName(String name) {
		return name == null ? null : nameMapping.get(name.toLowerCase());
	}

	/**
	 * @return Entire list of friendly names concatenated together.
	 */
	public static String getNameValueString() {
		return nameString;
	}

	static {
		for (OutputType ft : values()) {
			nameMapping.put(ft.getFriendlyName(), ft);
		}
		nameString = StringUtils.join("|", nameMapping.keySet());
	}
}
