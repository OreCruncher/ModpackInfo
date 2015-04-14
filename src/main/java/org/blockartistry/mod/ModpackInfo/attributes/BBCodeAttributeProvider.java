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

import java.util.HashMap;

import javax.xml.transform.Transformer;

import net.minecraftforge.common.config.Configuration;

/**
 * 
 * An instance of this class will provide color decoration to the generated
 * output file. By default it provides attributes based on Enjin's BBCode
 * standard. The attributes can be customized by modifying the configuration
 * file for the mod. Strictly speaking, the codes do not have to be BBCode -
 * they can match whatever platform is being used to publish the information.
 *
 */
public class BBCodeAttributeProvider extends AttributeProvider {

	private static final String CATEGORY_FORMATTING = "formatting.bbcode";

	private static final String DEFAULT_MODPACK_NAME_BEGIN_FORMAT = "[color=orange][size=6]";
	private static final String DEFAULT_MODPACK_NAME_END_FORMAT = "[/size][/color]";
	private static final String DEFAULT_MODPACK_VERSION_BEGIN_FORMAT = "[color=yellow][size=5]";
	private static final String DEFAULT_MODPACK_VERSION_END_FORMAT = "[/size][/color]";
	private static final String DEFAULT_MODPACK_WEBSITE_BEGIN_FORMAT = "[color=cyan]";
	private static final String DEFAULT_MODPACK_WEBSITE_END_FORMAT = "[/color]";
	private static final String DEFAULT_MODPACK_WEBSITE_URL_BEGIN_FORMAT = "[url]";
	private static final String DEFAULT_MODPACK_WEBSITE_URL_END_FORMAT = "[/url]";
	private static final String DEFAULT_MODPACK_DESCRIPTION_BEGIN_FORMAT = "[color=lightblue]";
	private static final String DEFAULT_MODPACK_DESCRIPTION_END_FORMAT = "[/color]";
	private static final String DEFAULT_MODPACK_AUTHORS_BEGIN_FORMAT = "[color=gold]";
	private static final String DEFAULT_MODPACK_AUTHORS_END_FORMAT = "[/color]";
	private static final String DEFAULT_MODPACK_CREDITS_BEGIN_FORMAT = "[color=gold]";
	private static final String DEFAULT_MODPACK_CREDITS_END_FORMAT = "[/color]";
	private static final String DEFAULT_MODPACK_ENVIRONMENT_BEGIN_FORMAT = "[color=lightgray]";
	private static final String DEFAULT_MODPACK_ENVIRONMENT_END_FORMAT = "[/color]";

	private static final String DEFAULT_MOD_NAME_BEGIN_FORMAT = "[u][color=orange][size=5]";
	private static final String DEFAULT_MOD_NAME_END_FORMAT = "[/size][/color]";
	private static final String DEFAULT_MOD_ID_BEGIN_FORMAT = "[color=orange]";
	private static final String DEFAULT_MOD_ID_END_FORMAT = "[/color]";
	private static final String DEFAULT_MOD_VERSION_BEGIN_FORMAT = "[color=yellow]";
	private static final String DEFAULT_MOD_VERSION_END_FORMAT = "[/color][/u]";
	private static final String DEFAULT_MOD_WEBSITE_BEGIN_FORMAT = "[color=cyan]";
	private static final String DEFAULT_MOD_WEBSITE_END_FORMAT = "[/color]";
	private static final String DEFAULT_MOD_WEBSITE_URL_BEGIN_FORMAT = "[url]";
	private static final String DEFAULT_MOD_WEBSITE_URL_END_FORMAT = "[/url]";
	private static final String DEFAULT_MOD_DESCRIPTION_BEGIN_FORMAT = "[color=lightblue]";
	private static final String DEFAULT_MOD_DESCRIPTION_END_FORMAT = "[/color]";
	private static final String DEFAULT_MOD_AUTHORS_BEGIN_FORMAT = "[color=gold]";
	private static final String DEFAULT_MOD_AUTHORS_END_FORMAT = "[/color]";
	private static final String DEFAULT_MOD_CREDITS_BEGIN_FORMAT = "[color=gold]";
	private static final String DEFAULT_MOD_CREDITS_END_FORMAT = "[/color]";

	private static HashMap<String, String> codes = new HashMap<String, String>();

	@Override
	public void init(Configuration config) {

		configHelper(config, getKey(TEXT_MODPACK, TEXT_NAME, TEXT_BEGIN),
				DEFAULT_MODPACK_NAME_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_NAME, TEXT_END),
				DEFAULT_MODPACK_NAME_END_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_VERSION, TEXT_BEGIN),
				DEFAULT_MODPACK_VERSION_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_VERSION, TEXT_END),
				DEFAULT_MODPACK_VERSION_END_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_WEBSITE, TEXT_BEGIN),
				DEFAULT_MODPACK_WEBSITE_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_WEBSITE, TEXT_END),
				DEFAULT_MODPACK_WEBSITE_END_FORMAT);
		configHelper(config,
				getKey(TEXT_MODPACK, TEXT_WEBSITE_URL, TEXT_BEGIN),
				DEFAULT_MODPACK_WEBSITE_URL_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_WEBSITE_URL, TEXT_END),
				DEFAULT_MODPACK_WEBSITE_URL_END_FORMAT);
		configHelper(config,
				getKey(TEXT_MODPACK, TEXT_DESCRIPTION, TEXT_BEGIN),
				DEFAULT_MODPACK_DESCRIPTION_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_DESCRIPTION, TEXT_END),
				DEFAULT_MODPACK_DESCRIPTION_END_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_AUTHORS, TEXT_BEGIN),
				DEFAULT_MODPACK_AUTHORS_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_AUTHORS, TEXT_END),
				DEFAULT_MODPACK_AUTHORS_END_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_CREDITS, TEXT_BEGIN),
				DEFAULT_MODPACK_CREDITS_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_CREDITS, TEXT_END),
				DEFAULT_MODPACK_CREDITS_END_FORMAT);
		configHelper(config,
				getKey(TEXT_MODPACK, TEXT_ENVIRONMENT, TEXT_BEGIN),
				DEFAULT_MODPACK_ENVIRONMENT_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MODPACK, TEXT_ENVIRONMENT, TEXT_END),
				DEFAULT_MODPACK_ENVIRONMENT_END_FORMAT);

		configHelper(config, getKey(TEXT_MOD, TEXT_NAME, TEXT_BEGIN),
				DEFAULT_MOD_NAME_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_NAME, TEXT_END),
				DEFAULT_MOD_NAME_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_ID, TEXT_BEGIN),
				DEFAULT_MOD_ID_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_ID, TEXT_END),
				DEFAULT_MOD_ID_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_VERSION, TEXT_BEGIN),
				DEFAULT_MOD_VERSION_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_VERSION, TEXT_END),
				DEFAULT_MOD_VERSION_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_WEBSITE, TEXT_BEGIN),
				DEFAULT_MOD_WEBSITE_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_WEBSITE, TEXT_END),
				DEFAULT_MOD_WEBSITE_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_WEBSITE_URL, TEXT_BEGIN),
				DEFAULT_MOD_WEBSITE_URL_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_WEBSITE_URL, TEXT_END),
				DEFAULT_MOD_WEBSITE_URL_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_DESCRIPTION, TEXT_BEGIN),
				DEFAULT_MOD_DESCRIPTION_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_DESCRIPTION, TEXT_END),
				DEFAULT_MOD_DESCRIPTION_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_AUTHORS, TEXT_BEGIN),
				DEFAULT_MOD_AUTHORS_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_AUTHORS, TEXT_END),
				DEFAULT_MOD_AUTHORS_END_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_CREDITS, TEXT_BEGIN),
				DEFAULT_MOD_CREDITS_BEGIN_FORMAT);
		configHelper(config, getKey(TEXT_MOD, TEXT_CREDITS, TEXT_END),
				DEFAULT_MOD_CREDITS_END_FORMAT);
	}

	public static void configHelper(Configuration config, String key,
			String defValue) {

		String val = config.get(CATEGORY_FORMATTING, key, defValue).getString();
		codes.put(key, val);
	}

	public void transformerHelper(Transformer transformer, String category,
			String field, String orientation) {

		String key = getKey(category, field, orientation);
		transformer.setParameter(key, codes.getOrDefault(key, TEXT_DEFAULT));
	}

	public void transformerHelper(Transformer transformer, String category,
			String field) {
		transformerHelper(transformer, category, field, TEXT_BEGIN);
		transformerHelper(transformer, category, field, TEXT_END);
	}

	@Override
	public void applyAttributeCodes(Transformer transformer) {

		transformerHelper(transformer, TEXT_MODPACK, TEXT_NAME);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_VERSION);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_WEBSITE);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_WEBSITE_URL);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_DESCRIPTION);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_AUTHORS);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_CREDITS);
		transformerHelper(transformer, TEXT_MODPACK, TEXT_ENVIRONMENT);

		transformerHelper(transformer, TEXT_MOD, TEXT_NAME);
		transformerHelper(transformer, TEXT_MOD, TEXT_ID);
		transformerHelper(transformer, TEXT_MOD, TEXT_VERSION);
		transformerHelper(transformer, TEXT_MOD, TEXT_WEBSITE);
		transformerHelper(transformer, TEXT_MOD, TEXT_WEBSITE_URL);
		transformerHelper(transformer, TEXT_MOD, TEXT_DESCRIPTION);
		transformerHelper(transformer, TEXT_MOD, TEXT_AUTHORS);
		transformerHelper(transformer, TEXT_MOD, TEXT_CREDITS);
	}
}
