package net.abyssdev.abyssbot.command.maps;

import net.abyssdev.abyssbot.AbyssBot;
import net.abyssdev.abyssbot.command.Command;
import net.abyssdev.abyssbot.listeners.CommandListener;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.HashMap;
import java.util.Map;

public final class CommandMap {

    private final Map<String, Command> commandCache = new HashMap<>();

    /**
     * Create a new command map
     */
    public CommandMap() {
        AbyssBot.getJDA().addEventListener(new CommandListener(this));
        updateCommands();
    }

    /**
     * Check if the command is registered
     *
     * @param id The command id
     * @return If its registered
     */
    public boolean hasCommand(String id) {
        return this.commandCache.containsKey(id.toLowerCase());
    }

    /**
     * Get a command by id
     *
     * @param id The command id
     * @return The command
     */
    public Command getCommand(String id) {
        return this.commandCache.get(id.toLowerCase());
    }

    /**
     * Register a new command
     *
     * @param command The command
     */
    public void registerCommand(Command command) {
        this.commandCache.put(
                command.getName().toLowerCase(),
                command
        );
    }

    public void registerCommand(Command... commands) {
        for (Command command : commands) {
            this.commandCache.put(command.getName().toLowerCase(), command);
        }
    }

    /**
     * Unregister a command
     *
     * @param name The command name
     */
    public void unregisterCommand(String name) {
        this.commandCache.remove(name.toLowerCase());
    }

    /**
     * Unregister a command
     *
     * @param command The command object
     */
    public void unregisterCommand(Command command) {
        this.unregisterCommand(command.getName());
    }

    /**
     * Update the discord commands
     */
    public void updateCommands() {
        for (Guild guild : AbyssBot.getJDA().getGuilds()) {
            updateCommands(guild);
        }
    }

    /**
     * Update commands for a specific guild
     *
     * @param guild The guild to update
     */
    @SuppressWarnings("all")
    public void updateCommands(Guild guild) {

        CommandListUpdateAction updateAction = guild.updateCommands();

        for (Command command : this.commandCache.values()) {
            updateAction.addCommands(command.getCommandData());
        }

        updateAction.queue();

    }
}
