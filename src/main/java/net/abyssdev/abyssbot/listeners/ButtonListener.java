package net.abyssdev.abyssbot.listeners;

import net.abyssdev.abyssbot.AbyssBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class ButtonListener extends ListenerAdapter {

    private Role role;

    @Override
    public void onButtonClick(@NotNull final ButtonClickEvent event) {

        final Button button = event.getButton();

        if (!button.getId().equalsIgnoreCase("verify")) {
            return;
        }

        final Guild guild = event.getGuild();
        final Member member = event.getMember();

        if (this.role == null) {
            this.role = AbyssBot.getGuild().getRoleById(948317987633516605L);
        }

        guild.addRoleToMember(member, this.role).queue();
        event.reply("You have successfully verified, " + member.getAsMention() + "!")
                .queue(message -> message.deleteOriginal().queueAfter(1L, TimeUnit.SECONDS));
    }
}
