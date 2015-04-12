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

import org.blockartistry.mod.ModpackInfo.ModpackInfo;
import org.blockartistry.mod.ModpackInfo.PackInfo;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerEventHandler {

	/*
	 * Message to display to the player when they log in. An initial login will
	 * cause this message to be generated. Note that the message is split along
	 * newlines and cached for performance and memory reasons.
	 */
	protected String[] loginMessage = null;

	private String[] getLoginMessage() {

		if (loginMessage == null) {
			StringBuilder builder = new StringBuilder();
			PackInfo info = ModpackInfo.instance.getPackInfo();

			if (info.hasValidName()) {
				builder.append(EnumChatFormatting.AQUA + "You are playing "
						+ EnumChatFormatting.GOLD);
				builder.append(info.getName());

				if (info.hasValidVersion()) {
					builder.append(EnumChatFormatting.YELLOW + " Version ");
					builder.append(info.getVersion());
				}

				if (info.hasValidWebsite()) {
					builder.append("\n" + EnumChatFormatting.BLUE
							+ info.getWebsite());
				}
			}

			loginMessage = builder.toString().split("\\r?\\n");
		}

		return loginMessage;
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {

		if (event.player != null) {

			EntityPlayerMP player = (EntityPlayerMP) event.player;

			if (player != null) {
				PlayerEntityHelper.sendChatMessage(player, getLoginMessage());
			}
		}
	}
}
