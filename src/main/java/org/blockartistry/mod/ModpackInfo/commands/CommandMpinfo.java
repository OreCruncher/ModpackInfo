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

package org.blockartistry.mod.ModpackInfo.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import org.blockartistry.mod.ModpackInfo.PackInfo;
import org.blockartistry.mod.ModpackInfo.PlayerContext;
import org.blockartistry.mod.ModpackInfo.Player.PlayerEntityHelper;
import org.blockartistry.mod.ModpackInfo.localization.LanguagePack;
import org.blockartistry.mod.ModpackInfo.localization.TextBuilder;

/**
 * 
 * Implementation of the /mpinfo command.
 *
 */
public class CommandMpinfo implements ICommand {

	private String getMpinfoMessage(LanguagePack lang) {

		TextBuilder builder = new TextBuilder(lang);

		if (PackInfo.hasValidName()) {

			builder.append("mpinfo.name.text", PackInfo.getName());

			if (PackInfo.hasValidVersion()) {
				builder.append("mpinfo.version.text", PackInfo.getVersion());
			}

			if (PackInfo.hasValidWebsite()) {
				builder.append("mpinfo.website.text", PackInfo.getWebsite());
			}

			if (PackInfo.hasValidAuthors()) {
				builder.append("mpinfo.authors.text", PackInfo.getAuthors());
			}

		} else {
			builder.append("mpinfo.noinfo.text");
		}

		return builder.toString();
	}

	private static final List<String> aliases = new ArrayList<String>();

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "modpackinfo";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/modpackinfo";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] parms) {

		PlayerContext ctx = new PlayerContext(sender);
		PlayerEntityHelper.sendChatMessage(ctx,
				getMpinfoMessage(ctx.getLanguagePack()));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		PlayerContext ctx = new PlayerContext(sender);
		return !ctx.senderIsFakePlayer();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] a) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] a, int b) {
		return false;
	}

	static {

		aliases.add("mpinfo");
		aliases.add("modpackinfo");
	}
}
