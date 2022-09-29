package net.abyssdev.abyssbot.command.commands;

import net.abyssdev.abyssbot.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.managers.ChannelManager;

import java.awt.*;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public final class CreateProductCommand extends Command {

    public CreateProductCommand() {
        super("createproduct", "Creates a product in the client discord");
        this.addOption(OptionType.STRING, "product", "The product");
        this.addOption(OptionType.STRING, "roleid", "Example: Abyss Collectors ($15)");
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
        final Role everyone = guild.getPublicRole();

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
            category.createTextChannel("changelog").queue(channel -> {
                
                final ChannelManager manager = channel.getManager();
                
                manager.putPermissionOverride(role, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(support, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(dev, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(everyone, null, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE))
                        .queue();
            });

            category.createTextChannel("downloads").queue(channel -> {

                final ChannelManager manager = channel.getManager();

                manager.putPermissionOverride(role, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(support, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(dev, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(everyone, null, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE))
                        .queue();
            });

            category.createTextChannel("tickets").queue(channel -> {

                final ChannelManager manager = channel.getManager();

                manager.putPermissionOverride(role, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(support, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(dev, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(everyone, null, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE))
                        .queue();
            });

            category.createTextChannel("chat").queue(channel -> {

                final ChannelManager manager = channel.getManager();

                manager.putPermissionOverride(role, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(support, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(dev, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY), null)
                        .queue();

                manager.putPermissionOverride(everyone, null, EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_MENTION_EVERYONE))
                        .queue();
            });

        });

        event.deferReply(true).setContent("You have just created product " + product).queue();
    }

}
