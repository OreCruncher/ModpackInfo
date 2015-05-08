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

import java.io.File;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.blockartistry.mod.ModpackInfo.Player.PlayerEntityHelper;
import org.blockartistry.mod.ModpackInfo.Xml.XmlHelpers;
import org.blockartistry.mod.ModpackInfo.attributes.AttributeProvider;
import org.blockartistry.mod.ModpackInfo.commands.CommandHelper;
import org.w3c.dom.Document;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

/**
 * 
 * Main class of the mod. Handles the necessary "glue" into the Forge framework
 * as well as performing the high level tasks of generating the modpack
 * information to a log file on disk.
 * 
 */
@Mod(modid = ModpackInfo.MOD_ID, useMetadata = true, dependencies = ModpackInfo.DEPENDENCIES, version = ModpackInfo.VERSION, acceptableRemoteVersions = "*")
public class ModpackInfo {

	@Instance
	public static ModpackInfo instance = new ModpackInfo();

	private static final String TEXT_OPTION_OUTPUT_TYPE = "Output Type";
	private static final String TEXT_OPTION_OUTPUT_TYPE_COMMENT = "Type of output to generate ("
			+ OutputType.getNameValueString() + ")";
	private static final String DEFAULT_OUTPUT_TYPE = OutputType.TEXT
			.getFriendlyName();
	private static final String TEXT_OPTION_DISPLAY_GREETING = "Display Greeting";
	private static final String TEXT_OPTION_DISPLAY_GREETING_COMMENT = "Display greeting when player logs in (true|false)";
	private static final boolean DEFAULT_DISPLAY_GREETING = true;
	private static final String TEXT_OPTION_ENABLE_COMMANDS = "Enable Commands";
	private static final String TEXT_OPTION_ENABLE_COMMANDS_COMMENT = "Enable commands for player use (true|false)";
	private static final boolean DEFAULT_ENABLE_COMMANDS = true;

	public static final String MOD_ID = "mpinfo";
	public static final String MOD_NAME = "ModpackInfo";
	public static final String VERSION = "0.1.0";
	public static final String DEPENDENCIES = "after:*";

	private static final String FILE_NAME = "ModpackInfo";

	private File mcDir;
	private Logger log;

	protected PackInfo info;
	protected AttributeProvider cc;

	private OutputType fType = OutputType.TEXT;
	private boolean displayLoginGreeting = DEFAULT_DISPLAY_GREETING;
	private boolean enableCommands = DEFAULT_ENABLE_COMMANDS;

	/**
	 * @return Information about the modpack as indicated in the config file.
	 */
	public PackInfo getPackInfo() {
		return info;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// Need to get the root of the MC directory so we can save our output
		// file
		mcDir = event.getModConfigurationDirectory().getParentFile();
		log = event.getModLog();

		// Load up our configuration
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());

		config.load();

		try {

			fType = OutputType.getValueByName(config.get(
					Configuration.CATEGORY_GENERAL, TEXT_OPTION_OUTPUT_TYPE,
					DEFAULT_OUTPUT_TYPE, TEXT_OPTION_OUTPUT_TYPE_COMMENT)
					.getString());

			if (fType == null) {
				log.warn(
						"Unknown OutputType in configuration; defaulting to '%s'",
						DEFAULT_OUTPUT_TYPE);
				fType = OutputType.getValueByName(DEFAULT_OUTPUT_TYPE);
			}

			info = new PackInfo();
			PackInfo.init(config);

			cc = fType.getAttributeProviderType().getProvider(config);

			displayLoginGreeting = config.get(Configuration.CATEGORY_GENERAL,
					TEXT_OPTION_DISPLAY_GREETING, DEFAULT_DISPLAY_GREETING,
					TEXT_OPTION_DISPLAY_GREETING_COMMENT).getBoolean();

			enableCommands = config.get(Configuration.CATEGORY_GENERAL,
					TEXT_OPTION_ENABLE_COMMANDS, DEFAULT_ENABLE_COMMANDS,
					TEXT_OPTION_ENABLE_COMMANDS_COMMENT).getBoolean();

		} catch (Exception e) {
			log.log(Level.WARN, "Unable to read config file!", e);
			e.printStackTrace();
			fType = OutputType.TEXT;
		}

		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// Register our greeting handler if it is configured
		if (displayLoginGreeting) {

			log.info("Registering for player login events");
			PlayerEntityHelper.registerEventHandlers();
		} else {
			log.info("Not registering for player login events");
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		try {
			// Get our modlist XML
			Document doc = XmlHelpers.toXml(info, Loader.instance()
					.getModList());

			if (doc == null) {
				log.log(Level.WARN,
						"Unable to generate XML document for conversion!");
				return;
			}

			// Make our output filename
			String fileName = String.format("%s%s%s", FILE_NAME, ".",
					fType.getFileNameExtension());

			// Transform!
			XmlHelpers.saveTo(doc, Assets.getTransformSheet(fType), cc,
					new StreamResult(new File(mcDir, fileName)));

		} catch (TransformerException e) {
			log.log(Level.WARN,
					"Unable to transform XML document into mod listing!");
			e.printStackTrace();
		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		// Register our commands
		if (enableCommands) {
			log.info("Registering command handlers");
			CommandHelper.registerCommands(event);
		} else {
			log.info("Not registering command handlers");
		}
	}

	@EventHandler
	public void serverUnload(FMLServerStoppingEvent event) {

	}
}
