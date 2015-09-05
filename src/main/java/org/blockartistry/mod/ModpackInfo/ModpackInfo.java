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

import org.apache.logging.log4j.LogManager;
import org.blockartistry.mod.ModpackInfo.Player.PlayerEntityHelper;
import org.blockartistry.mod.ModpackInfo.Xml.XmlHelpers;
import org.blockartistry.mod.ModpackInfo.commands.CommandHelper;
import org.blockartistry.mod.ModpackInfo.proxy.Proxy;
import org.w3c.dom.Document;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
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

	public static final String MOD_ID = "mpinfo";
	public static final String MOD_NAME = "ModpackInfo";
	public static final String VERSION = "0.1.0";
	public static final String DEPENDENCIES = "after:*";

	private static final String FILE_NAME = "ModpackInfo";

	private File mcDir;

	@SidedProxy(clientSide = "org.blockartistry.mod.ModpackInfo.proxy.ProxyClient", serverSide = "org.blockartistry.mod.ModpackInfo.proxy.Proxy")
	protected static Proxy proxy;

	public static Proxy proxy() {
		return proxy;
	}

	public ModpackInfo() {
		ModLog.setLogger(LogManager.getLogger(MOD_ID));
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// Need to get the root of the MC directory so we can save our output
		// file
		mcDir = event.getModConfigurationDirectory().getParentFile();

		// Load up our configuration
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();
		ModOptions.load(config);
		config.save();

		proxy.preInit(event, config);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// Register our greeting handler if it is configured
		if (ModOptions.getDisplayLoginGreeting()) {

			ModLog.info("Registering for player login events");
			PlayerEntityHelper.registerEventHandlers();
		} else {
			ModLog.info("Not registering for player login events");
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		try {
			// Get our modlist XML
			Document doc = XmlHelpers.toXml(Loader.instance().getModList());

			if (doc == null) {
				ModLog.warn("Unable to generate XML document for conversion!");
				return;
			}

			// Make our output filename
			String fileName = String.format("%s%s%s", FILE_NAME, ".",
					ModOptions.getOutputType().getFileNameExtension());

			// Transform!
			XmlHelpers.saveTo(doc, Assets.getTransformSheet(ModOptions.getOutputType()),
					ModOptions.getAttributeProvider(), new StreamResult(new File(mcDir, fileName)));

		} catch (TransformerException e) {
			ModLog.warn("Unable to transform XML document into mod listing!");
			e.printStackTrace();
		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		// Register our commands
		if (ModOptions.getEnableCommands()) {
			ModLog.info("Registering command handlers");
			CommandHelper.registerCommands(event);
		} else {
			ModLog.info("Not registering command handlers");
		}
	}

	@EventHandler
	public void serverUnload(FMLServerStoppingEvent event) {

	}
}
