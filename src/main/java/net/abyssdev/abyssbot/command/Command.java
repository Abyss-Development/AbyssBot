package net.abyssdev.abyssbot.command;

import lombok.Data;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Command {

    private final String name, description;
    private final List<OptionData> optionDataList = new ArrayList<>();

    /**
     * Create a new command
     * @param name The command name
     * @param description The command description
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Execute the command
     * @param member The member who used it
     * @param args The arguments and options
     * @param event The command event
     */
    public abstract void onCommand(Member member, OptionMapping[] args, SlashCommandEvent event);

    /**
     * Add an input option for the command
     * @param optionType The option type
     * @param name The name
     * @param description The description
     * @return this
     */
    public Command addOption(OptionType optionType, String name, String description) {
        return addOption(optionType, name, description, true);
    }

    /**
     * Add an input option for the command
     * @param optionType The option type
     * @param name The name
     * @param description The description
     * @param isRequired If its required
     * @return this
     */
    public Command addOption(OptionType optionType, String name, String description, boolean isRequired) {
        this.optionDataList.add(new OptionData(optionType, name, description, isRequired));
        return this;
    }

    /**
     * Get the discord Command Data
     * @return The command data
     */
    public CommandData getCommandData() {
        return new CommandData(name.toLowerCase(), description).addOptions(optionDataList);
    }
}
