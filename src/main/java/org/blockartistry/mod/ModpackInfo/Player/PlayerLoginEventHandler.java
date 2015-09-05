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

import org.blockartistry.mod.ModpackInfo.PackInfo;
import org.blockartistry.mod.ModpackInfo.PlayerContext;
import org.blockartistry.mod.ModpackInfo.localization.LanguagePack;
import org.blockartistry.mod.ModpackInfo.localization.TextBuilder;

import com.google.common.base.Preconditions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

/**
 * 
 * Event handler for detecting player login. When detected the player will be
 * sent a greeting that is derived from the modpack information provided by the
 * modpack authors.
 *
 */
public final class PlayerLoginEventHandler {

	private String getLoginMessage(LanguagePack lang) {

		TextBuilder builder = new TextBuilder(lang);

		if (PackInfo.hasValidName()) {

			builder.append("mpinfo.greeting.text", PackInfo.getName());

			if (PackInfo.hasValidVersion()) {
				builder.append("mpinfo.version.text", PackInfo.getVersion());
			}

			if (PackInfo.hasValidWebsite()) {
				builder.append("mpinfo.website.text", PackInfo.getWebsite());
			}
		}

		return builder.toString();
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {

		Preconditions.checkNotNull(event);
		Preconditions.checkNotNull(event.player);

		PlayerContext ctx = new PlayerContext(event.player);

		if (!ctx.senderIsFakePlayer()) {
			PlayerEntityHelper.sendChatMessage(ctx,
					getLoginMessage(ctx.getLanguagePack()));
		}
	}
}
