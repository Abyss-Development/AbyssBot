package net.abyssdev.abyssbot;

import net.abyssdev.abyssbot.command.commands.ContractorCommand;
import net.abyssdev.abyssbot.command.commands.VerifyPanelCommand;
import net.abyssdev.abyssbot.command.maps.CommandMap;
import net.abyssdev.abyssbot.listeners.ButtonListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public final class AbyssBot {

    private static JDA JDA;
    private static Guild guild;
    private static CommandMap commandMap;

    public static void main(final String[] args) {
        AbyssBot.buildBot();

        AbyssBot.commandMap = new CommandMap();
        AbyssBot.commandMap.registerCommand(new VerifyPanelCommand(), new ContractorCommand());
        AbyssBot.commandMap.updateCommands();
    }

    private static void buildBot() {
        final JDABuilder builder = JDABuilder.createDefault("OTQ4Njg3MDQ0MjQ5NTE4MTEw.Yh_bqQ.Kraa8xQ1nEdx4OYeIORIgwNik3s");

        builder.enableIntents(Arrays.asList(GatewayIntent.values()));
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.watching("clients"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.addEventListeners(new ButtonListener());

        try {
            AbyssBot.JDA = builder.build().awaitReady();
            AbyssBot.guild = AbyssBot.JDA.getGuildById(948252269294518282L);
        } catch (final LoginException exception) {
            System.out.println("Abyss | Bot failed to load. Pasting stacktrace below.");
            exception.printStackTrace();
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static JDA getJDA() {
        return AbyssBot.JDA;
    }

    public static Guild getGuild() {
        return AbyssBot.guild;
    }

    public static CommandMap getCommandMap() {
        return AbyssBot.commandMap;
    }

}