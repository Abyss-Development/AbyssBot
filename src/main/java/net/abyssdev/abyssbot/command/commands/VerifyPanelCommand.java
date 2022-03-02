package net.abyssdev.abyssbot.command.commands;

import net.abyssdev.abyssbot.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public final class VerifyPanelCommand extends Command {

    public VerifyPanelCommand() {
        super("verifypanel", "Creates a verify panel");
    }

    @Override
    public void onCommand(final Member member, final OptionMapping[] args, final SlashCommandEvent event) {

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You need to be an administrator for this command!")
                    .queue(msg -> msg.deleteOriginal().queueAfter(2L, TimeUnit.SECONDS));
            return;
        }

        event.deferReply().addEmbeds(new EmbedBuilder()
                        .setTitle("Abyss | Verification")
                        .setDescription("Please click the button below to verify and get access to the discord server.")
                        .setColor(Color.decode("#3ab4ff"))
                        .build()).addActionRow(Button.primary("verify", "Verify")).queue();

    }

}
