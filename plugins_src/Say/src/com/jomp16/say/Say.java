/*
 * Copyright (C) 2013 jomp16
 *
 * This file is part of Say
 *
 * Say is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Say is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Say. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jomp16.say;

import com.jomp16.pircbotx.Main;
import com.jomp16.pircbotx.language.LanguageManager;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Say extends ListenerAdapter {
    private String prefix = Main.getPrefix();
    private LanguageManager languageManager;

    public Say() throws IOException {
        String fileNameToFormat = "/lang/%s_%s.lang",
                fileNameFormatted = String.format(fileNameToFormat, System.getProperty("user.language"), System.getProperty("user.country")),
                jarPath = URLDecoder.decode(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");

        URL url = new URL("jar:file:" + jarPath + "!" + fileNameFormatted);
        languageManager = new LanguageManager(url);
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        ArrayList<String> args = new ArrayList<>();
        Matcher matcher = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'").matcher(event.getMessage());
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Add double-quoted string without the quotes
                args.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                // Add single-quoted string without the quotes
                args.add(matcher.group(2));
            } else {
                // Add unquoted word
                args.add(matcher.group());
            }
        }

        if (args.get(0).toLowerCase().equals(prefix + "say")) {
            if (args.size() >= 3) {
                String channel = args.get(1);
                String message = args.get(2);
                event.getBot().sendIRC().message(channel, message);
                event.respond(languageManager.getString("MessageSended"));
            } else {
                event.respond(languageManager.getString("CommandSyntax", prefix));
            }
        }
        args.clear();
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        ArrayList<String> args = new ArrayList<>();
        Matcher matcher = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'").matcher(event.getMessage());
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Add double-quoted string without the quotes
                args.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                // Add single-quoted string without the quotes
                args.add(matcher.group(2));
            } else {
                // Add unquoted word
                args.add(matcher.group());
            }
        }

        if (args.get(0).toLowerCase().equals(prefix + "say")) {
            if (args.size() >= 3) {
                String channel = args.get(1);
                String message = args.get(2);
                event.getBot().sendIRC().message(channel, message);
                event.respond(languageManager.getString("MessageSended"));
            } else {
                event.respond(languageManager.getString("CommandSyntax", prefix));
            }
        }
        args.clear();
    }
}
