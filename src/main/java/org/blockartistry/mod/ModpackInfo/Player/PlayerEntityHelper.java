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

package org.blockartistry.mod.ModpackInfo.Player;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.FMLCommonHandler;

/**
 *
 * Helper methods that facilitate access to the Forge player entity.
 * 
 */
public final class PlayerEntityHelper {

	/**
	 * Sends one or more messages to the specified player. It is assumed that
	 * the messages are split at newline boundaries.
	 * 
	 * @param player
	 *            Player that is to receive the messages.
	 * @param lines
	 *            The messages to send.
	 */
	public static void sendChatMessage(ICommandSender player, String[] lines) {
		if (player == null || lines == null || lines.length == 0)
			return;

		for (String m : lines) {
			player.addChatMessage(new ChatComponentText(m));
		}
	}

	/**
	 * Sends a message to the specified player. The routine will split the
	 * message at newline boundaries of needed.
	 * 
	 * @param player
	 *            Player that is to receive the message.
	 * @param message
	 *            The message to send.
	 */
	public static void sendChatMessage(ICommandSender player, String message) {

		if (player == null || message == null || message.isEmpty())
			return;

		sendChatMessage(player, message.split("\\r?\\n"));
	}

	/**
	 * Registers Forge/FML event handlers for the Player entity
	 */
	public static void registerEventHandlers() {

		FMLCommonHandler.instance().bus()
				.register(new PlayerLoginEventHandler());
	}
}
