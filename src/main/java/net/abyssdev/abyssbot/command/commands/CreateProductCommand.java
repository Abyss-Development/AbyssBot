package net.abyssdev.abyssbot.command.commands;

import net.abyssdev.abyssbot.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public final class CreateProductCommand extends Command {

    public CreateProductCommand() {
        super("createproduct", "Creates a product in the client discord");
        this.addOption(OptionType.STRING, "productName", "The product");
        this.addOption(OptionType.STRING, "roleID", "Example: Abyss Collectors ($15)");
    }

    @Override
    public void onCommand(Member member, OptionMapping[] args, SlashCommandEvent event) {

        if (event.getGuild().getIdLong() != 959894376128061481L) {
            event.deferReply().addEmbeds(new EmbedBuilder()
                            .setTitle("AbyssDev | Wrong Discord")
                            .setDescription("You must execute this command in the Client Discord")
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

        final Guild guild = event.getGuild();

        final String product = args[0].getAsString();
        final Role role = guild.getRoleById(args[1].getAsString());
        final Role support = guild.getRoleById(960262713701961780L);
        final Role dev = guild.getRoleById(960262161882550322L);

        if (role == null) {
            event.deferReply().addEmbeds(new EmbedBuilder()
                            .setTitle("AbyssDev | Invalid Role")
                            .setDescription("Enter a valid role id")
                            .setColor(Color.decode("#3ab4ff"))
                            .build())
                    .queue(msg -> msg.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
            return;
        }

        guild.createCategory(product).queue(category -> {

            category.createTextChannel("chat").queue(channel -> {
                channel.createPermissionOverride(role)
                        .setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .setDeny(Permission.MESSAGE_MENTION_EVERYONE)
                        .queue();

                channel.createPermissionOverride(support).setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(dev).setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();
            });

            category.createTextChannel("downloads").queue(channel -> {
                channel.createPermissionOverride(role)
                        .setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(support).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(dev).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();
            });

            category.createTextChannel("tickets").queue(channel -> {
                channel.createPermissionOverride(role)
                        .setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(support).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(dev).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();
            });

            category.createTextChannel("changelog").queue(channel -> {
                channel.createPermissionOverride(role)
                        .setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(support).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();

                channel.createPermissionOverride(dev).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY)
                        .queue();
            });

        });
    }

}
