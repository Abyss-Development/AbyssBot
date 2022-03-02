package net.abyssdev.abyssbot.listeners;

import net.abyssdev.abyssbot.command.Command;
import net.abyssdev.abyssbot.command.maps.CommandMap;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.List;

public final class CommandListener extends ListenerAdapter {

    private final CommandMap commandMap;

    /**
     * Create a new command listener
     *
     * @param commandMap The command map to use
     */
    public CommandListener(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Calls the command executed
     *
     * @param event The event to use
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        Command command = commandMap.getCommand(event.getName().toLowerCase());

        if (command == null) {
            return;
        }

        OptionMapping[] optionMappings = getArrayFromList(OptionMapping.class, event.getOptions());
        command.onCommand(event.getMember(), optionMappings, event);
    }

    /**
     * Get an array from a list
     *
     * @param objectClass The object class
     * @param list        The list
     * @param <T>         The type
     * @return The array
     */
    @SuppressWarnings("unchecked")
    private <T> T[] getArrayFromList(Class<T> objectClass, List<T> list) {
        return list.toArray((T[]) Array.newInstance(objectClass, 0));
    }

}
