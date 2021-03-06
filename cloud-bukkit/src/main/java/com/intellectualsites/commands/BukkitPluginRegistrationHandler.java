//
// MIT License
//
// Copyright (c) 2020 IntellectualSites
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package com.intellectualsites.commands;

import com.intellectualsites.commands.components.CommandComponent;
import com.intellectualsites.commands.internal.CommandRegistrationHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

final class BukkitPluginRegistrationHandler implements CommandRegistrationHandler<BukkitCommandMeta> {

    private final Map<CommandComponent<?, ?>, org.bukkit.command.Command> registeredCommands = new HashMap<>();

    private Map<String, org.bukkit.command.Command> bukkitCommands;
    private BukkitCommandManager bukkitCommandManager;

    BukkitPluginRegistrationHandler() {
    }

    void initialize(@Nonnull final BukkitCommandManager bukkitCommandManager) throws Exception {
        final Method getCommandMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap");
        getCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) getCommandMap.invoke(Bukkit.getServer());
        final Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
        knownCommands.setAccessible(true);
        @SuppressWarnings("ALL")
        final Map<String, org.bukkit.command.Command> bukkitCommands = (Map<String, org.bukkit.command.Command>) knownCommands.get(
                commandMap);
        this.bukkitCommands = bukkitCommands;
        this.bukkitCommandManager = bukkitCommandManager;
    }

    @Override
    public boolean registerCommand(@Nonnull final Command<?, BukkitCommandMeta> command) {
        /* We only care about the root command component */
        final CommandComponent<?, ?> commandComponent = command.getComponents().get(0);
        if (this.registeredCommands.containsKey(commandComponent)) {
            return false;
        }
        final String label;
        if (bukkitCommands.containsKey(commandComponent.getName())) {
            label = String.format("%s:%s", this.bukkitCommandManager.getOwningPlugin().getName(), commandComponent.getName());
        } else {
            label = commandComponent.getName();
        }
        @SuppressWarnings("unchecked")
        final BukkitCommand bukkitCommand = new BukkitCommand((CommandComponent<BukkitCommandSender, ?>) commandComponent,
                                                              this.bukkitCommandManager);
        this.bukkitCommands.put(label, bukkitCommand);
        this.registeredCommands.put(commandComponent, bukkitCommand);
        return true;
    }

}
