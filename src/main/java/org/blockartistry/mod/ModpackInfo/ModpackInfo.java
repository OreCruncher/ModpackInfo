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

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.blockartistry.mod.ModpackInfo.commands.CommandHelper;
import org.w3c.dom.Document;

@Mod(modid = ModpackInfo.MOD_ID, useMetadata = true, dependencies = ModpackInfo.DEPENDENCIES, version = ModpackInfo.VERSION)
public final class ModpackInfo {

	@Instance
	public static ModpackInfo instance = new ModpackInfo();

	private static final String TEXT_OPTION_FORMATTER_TYPE = "FormatterType";
	private static final String TEXT_OPTION_FORMATTER_TYPE_COMMENT = "Formatter to use when generating output file ("
			+ FormatterType.getNameValueString() + ")";
	private static final String DEFAULT_FORMATTER_TYPE = FormatterType.TEXT
			.getFriendlyName();
	private static final String TEXT_OPTION_DISPLAY_GREETING = "Display Greeting";
	private static final String TEXT_OPTION_DISPLAY_GREETING_COMMENT = "Display greeting when player logs in (true|false)";
	private static final boolean DEFAULT_DISPLAY_GREETING = true;
	private static final String TEXT_OPTION_ENABLE_COMMANDS = "Enable Commands";
	private static final String TEXT_OPTION_ENABLE_COMMANDS_COMMENT = "Enable commands for player use (true|false)";
	private static final boolean DEFAULT_ENABLE_COMMANDS = true;

	public static final String MOD_ID = "mpinfo";
	public static final String MOD_NAME = "ModpackInfo";
	public static final String VERSION = "0.0.1";
	public static final String DEPENDENCIES = "after:*";

	private static final String FILE_NAME = "ModpackInfo";

	private File mcDir;
	private Logger log;

	protected PackInfo info = new PackInfo();

	public PackInfo getPackInfo() {
		return info;
	}

	private FormatterType fType = FormatterType.TEXT;
	private boolean displayLoginGreeting = true;
	private boolean enableCommands = false;

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

			info.init(config);

			fType = FormatterType.getValueByName(config.get(
					Configuration.CATEGORY_GENERAL, TEXT_OPTION_FORMATTER_TYPE,
					DEFAULT_FORMATTER_TYPE, TEXT_OPTION_FORMATTER_TYPE_COMMENT)
					.getString());

			if (fType == null) {
				log.warn(
						"Unknown FormatterType in configuration; defaulting to '%s'",
						DEFAULT_FORMATTER_TYPE);
				fType = FormatterType.getValueByName(DEFAULT_FORMATTER_TYPE);
			}

			displayLoginGreeting = config.get(Configuration.CATEGORY_GENERAL,
					TEXT_OPTION_DISPLAY_GREETING, DEFAULT_DISPLAY_GREETING,
					TEXT_OPTION_DISPLAY_GREETING_COMMENT).getBoolean();

			enableCommands = config.get(Configuration.CATEGORY_GENERAL,
					TEXT_OPTION_ENABLE_COMMANDS, DEFAULT_ENABLE_COMMANDS,
					TEXT_OPTION_ENABLE_COMMANDS_COMMENT).getBoolean();

		} catch (Exception e) {
			log.log(Level.WARN, "Unable to read config file!", e);
			e.printStackTrace();
			fType = FormatterType.TEXT;
		}

		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// Register our greeting handler if it is configured
		if (displayLoginGreeting) {
			log.info("Registering for player login events");
			FMLCommonHandler.instance().bus()
					.register(new PlayerEventHandler());
		} else {
			log.info("Not registering for player login events");
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		try {
			// Get our modlist XML
			Document doc = XmlConverter.toXml(info, Loader.instance()
					.getModList());

			if (doc == null) {
				log.log(Level.WARN,
						"Unable to generate XML document for conversion!");
				return;
			}

			// Make our filename
			String fileName = String.format("%s%s%s", FILE_NAME, ".",
					fType.getFileNameExtension());

			// Prep for transformation!
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates template = factory.newTemplates(new StreamSource(Assets
					.getTransformSheet(fType)));
			Transformer xformer = template.newTransformer();
			Source source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(mcDir, fileName));

			// Transform!
			xformer.transform(source, result);

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