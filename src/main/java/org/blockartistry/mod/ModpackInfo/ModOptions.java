/*
 * This file is part of Restructured, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
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

import org.blockartistry.mod.ModpackInfo.attributes.AttributeProvider;

import net.minecraftforge.common.config.Configuration;

public final class ModOptions {

	private static final String CATEGORY_GENERAL = "general";
	private static final String OPTION_OUTPUT_TYPE = "Output Type";
	private static final String OPTION_OUTPUT_TYPE_COMMENT = "Type of output to generate ("
			+ OutputType.getNameValueString() + ")";
	private static final String DEFAULT_OUTPUT_TYPE = OutputType.TEXT.getFriendlyName();
	private static final String OPTION_DISPLAY_GREETING = "Display Greeting";
	private static final String OPTION_DISPLAY_GREETING_COMMENT = "Display greeting when player logs in (true|false)";
	private static final String OPTION_ENABLE_COMMANDS = "Enable Commands";
	private static final String OPTION_ENABLE_COMMANDS_COMMENT = "Enable commands for player use (true|false)";
	private static final String OPTION_ENABLE_VERSION_CHECK = "Enable Online Version Check";
	
	private static final String CATEGORY_LOGGING = "logging";
	private static final String OPTION_ENABLE_DEBUG_LOGGING = "Enable Debug Logging";

	private static AttributeProvider cc;
	private static OutputType fType = OutputType.TEXT;
	private static boolean displayLoginGreeting = true;
	private static boolean enableCommands = true;
	private static boolean enableOnlineVersionCheck = true;
	private static boolean enableDebugLogging = false;

	public static void load(Configuration config) {
		try {

			fType = OutputType.getValueByName(config.get(CATEGORY_GENERAL, OPTION_OUTPUT_TYPE,
					DEFAULT_OUTPUT_TYPE, OPTION_OUTPUT_TYPE_COMMENT).getString());

			if (fType == null) {
				ModLog.warn("Unknown OutputType in configuration; defaulting to '%s'", DEFAULT_OUTPUT_TYPE);
				fType = OutputType.getValueByName(DEFAULT_OUTPUT_TYPE);
			}

			PackInfo.init(config);

			cc = fType.getAttributeProviderType().getProvider(config);

			displayLoginGreeting = config.get(CATEGORY_GENERAL, OPTION_DISPLAY_GREETING,
					displayLoginGreeting, OPTION_DISPLAY_GREETING_COMMENT).getBoolean();

			enableCommands = config.get(CATEGORY_GENERAL, OPTION_ENABLE_COMMANDS,
					enableCommands, OPTION_ENABLE_COMMANDS_COMMENT).getBoolean();

			enableOnlineVersionCheck = config.get(CATEGORY_GENERAL, OPTION_ENABLE_VERSION_CHECK,
					enableOnlineVersionCheck, "Enable online version checking").getBoolean();

			enableDebugLogging = config.get(CATEGORY_LOGGING, OPTION_ENABLE_DEBUG_LOGGING,
					enableDebugLogging, "Enable debug logging").getBoolean();

		} catch (Exception e) {
			ModLog.warn("Unable to read config file!", e);
			e.printStackTrace();
			fType = OutputType.TEXT;
		}
	}

	public static boolean getEnableDebugLogging() {
		return enableDebugLogging;
	}

	public static boolean getOnlineVersionChecking() {
		return enableOnlineVersionCheck;
	}
	
	public static boolean getDisplayLoginGreeting() {
		return displayLoginGreeting;
	}
	
	public static boolean getEnableCommands() {
		return enableCommands;
	}
	
	public static AttributeProvider getAttributeProvider() {
		return cc;
	}
	
	public static OutputType getOutputType() {
		return fType;
	}
}
