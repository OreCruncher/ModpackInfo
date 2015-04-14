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

import net.minecraftforge.common.config.Configuration;

/**
 *
 * These are the possible attribute provider types that can be used to generate
 * output log files. Typically attributes provide text coloring and visual
 * adjustments, though the can be anything based upon the service where the log
 * file is published.
 * 
 */
public enum AttributeProviderType {

	/**
	 * No formatting attributes are applied during transformation
	 */
	NONE(NullAttributeProvider.class),

	/**
	 * Formatting attributes suitable for pure text are applied
	 */
	TEXT(NullAttributeProvider.class),

	/**
	 * Formatting attributes suitable for BBCode are applied
	 */
	BBCODE(BBCodeAttributeProvider.class);

	private final Class<? extends AttributeProvider> attributeProviderFactory;

	private AttributeProviderType(Class<? extends AttributeProvider> factory) {

		this.attributeProviderFactory = factory;

	}

	/**
	 * Returns an appropriate provider for the AttributeProviderType that has
	 * been initialized from the Configuration instance provided.
	 * 
	 * @return Provider that contains formatting information from Configuration
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AttributeProvider getProvider(Configuration config)
			throws InstantiationException, IllegalAccessException {
		AttributeProvider ap = attributeProviderFactory.newInstance();

		if (config != null) {
			ap.init(config);
		}
		return ap;
	}

	/**
	 * Returns an appropriate provider for the AttributeProviderType. The
	 * instance data will be default based on the implementation of the
	 * provider.
	 * 
	 * @return Provider initialized to it's defaults
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AttributeProvider getProvider() throws InstantiationException,
			IllegalAccessException {
		return getProvider(null);
	}

}
