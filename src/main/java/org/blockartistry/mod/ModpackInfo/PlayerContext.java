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

import java.lang.reflect.Field;
import java.util.Locale;

import org.blockartistry.mod.ModpackInfo.localization.LanguagePack;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

/**
 * Brings data from several sources together into one entity for ease of use
 * when processing commands.
 *
 */
public class PlayerContext implements ICommandSender {

	// Hook or by crook we will get the locale string for the remote client
	private static Field epField;
	static {
		try {
			epField = EntityPlayerMP.class.getDeclaredField("translator");
			epField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	protected ICommandSender sender;
	protected String localeString;
	protected LanguagePack language;

	public PlayerContext(ICommandSender sender) {
		this.sender = sender;

		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP ep = (EntityPlayerMP) sender;
			try {
				localeString = epField.get(ep).toString();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if (localeString == null || localeString.isEmpty()) {
			localeString = Locale.US.toString();
		}

		language = LanguagePack.getLanguagePack(localeString);
	}

	/**
	 * @return LanguagePack based on the players locale information
	 */
	public LanguagePack getLanguagePack() {
		return language;
	}

	@Override
	public String getCommandSenderName() {
		return sender.getCommandSenderName();
	}

	@Override
	public IChatComponent func_145748_c_() {
		return sender.func_145748_c_();
	}

	@Override
	public void addChatMessage(IChatComponent p_145747_1_) {
		sender.addChatMessage(p_145747_1_);
	}

	@Override
	public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
		return sender.canCommandSenderUseCommand(p_70003_1_, p_70003_2_);
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return sender.getPlayerCoordinates();
	}

	@Override
	public World getEntityWorld() {
		return sender.getEntityWorld();
	}

}
