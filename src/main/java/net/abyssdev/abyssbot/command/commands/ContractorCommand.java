package net.abyssdev.abyssbot.command.commands;

import net.abyssdev.abyssbot.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public final class ContractorCommand extends Command {

    public ContractorCommand() {
        super("contractor", "Creates a contractor channel for the specified user");
        this.addOption(OptionType.USER, "user", "The contractor");
        this.addOption(OptionType.STRING, "role", "Example: Developer");
        this.addOption(OptionType.STRING, "topic", "The channels topic");
    }

    @Override
    public void onCommand(Member member, OptionMapping[] args, SlashCommandEvent event) {

        if (event.getGuild().getIdLong() != 951918604658544680L) {
            event.deferReply().addEmbeds(new EmbedBuilder()
                            .setTitle("AbyssDev | Wrong Discord")
                            .setDescription("You must execute this command in the Contractor Discord")
                            .setColor(Color.decode("#3ab4ff"))
                            .build())
                    .queue(msg -> msg.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
            return;
        }

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            event.deferReply().addEmbeds(new EmbedBuilder()
                            .setTitle("AbyssDev | Insufficient Permissions")
                            .setDescription("You need to have administrative permissions to do this.")
                            .setColor(Color.decode("#3ab4ff"))
                            .build())
                    .queue(msg -> msg.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
            return;
        }


        final String role = args[1].getAsString();
        final User user = args[0].getAsUser();

        event.getGuild().createTextChannel(role + "-" + user.getName(), event.getGuild().getCategoryById(951919951885795378L)).queue(textChannel -> {
            textChannel.createPermissionOverride(event.getGuild().getMember(user)).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE).queue();
            textChannel.getManager().setTopic(args[2].getAsString()).queue();
            textChannel.sendMessage(user.getAsMention()).queue(msg -> msg.delete().queueAfter(1, TimeUnit.SECONDS));
            textChannel.sendMessage(new EmbedBuilder()
                    .setTitle("AbyssDev | Contractor Channel")
                    .setDescription("Welcome to your contractor channel, " + user.getName()
                            + ".\n\nThis is where all of your AbyssDev related work will be handled." +
                            "\n\n*(( If you have any questions feel free to ping management. ))*")
                    .build()).queue();
        });

        event.deferReply().addEmbeds(new EmbedBuilder()
                        .setTitle("AbyssDev | Created Channel")
                        .setDescription("You created an contractor channel for " + user.getName())
                        .setColor(Color.decode("#3ab4ff"))
                        .build())
                .queue(msg -> msg.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
    }

}
