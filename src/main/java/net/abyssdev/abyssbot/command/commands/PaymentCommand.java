package net.abyssdev.abyssbot.command.commands;

import net.abyssdev.abyssbot.command.Command;
import net.abyssdev.abyssbot.payment.PaymentLink;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class PaymentCommand extends Command {

    private final Map<String, PaymentLink> links = new HashMap<>();

    public PaymentCommand() {
        super("payment", "Sends a payment message");
        this.addOption(OptionType.STRING, "product", "The product to create a payment message for");

        this.links.put("expshop", new PaymentLink("AbyssEXPShop", "https://buy.stripe.com/4gwaIJb0AeIP0dW4gi", "$5", "1.8.x-1.18.x"));
        this.links.put("reactions", new PaymentLink("AbyssReactions", "https://buy.stripe.com/cN24klecM8krbWE149", "$10", "1.8.x-1.18.x"));
        this.links.put("vaults", new PaymentLink("AbyssVaults", "https://buy.stripe.com/5kAg33gkU6cj2m4aEI", "$10", "1.8.x-1.18.x"));
        this.links.put("playtime", new PaymentLink("AbyssPlaytime", "https://buy.stripe.com/cN2aIJgkU7gn1i0147", "$10", "1.8.x-1.18.x"));
        this.links.put("lootboxes", new PaymentLink("AbyssLootboxes", "https://buy.stripe.com/bIY3gh8SsgQXe4MaEK", "$10", "1.8.x-1.18.x"));
    }

    @Override
    public void onCommand(Member member, OptionMapping[] args, SlashCommandEvent event) {

        if (!this.links.containsKey(args[0].getAsString().toLowerCase())) {
            event.deferReply().setEphemeral(true).setContent("You have specified an invalid product!");
            return;
        }

        final PaymentLink link = this.links.get(args[0].getAsString().toLowerCase());

        event.deferReply().addEmbeds(new EmbedBuilder()
                .setTitle("AbyssDev | " + link.getFormattedName())
                .setDescription("\n**Product Information:**\nPrice: " + link.getPrice() + "\nVersions: " + link.getVersions() + "\n\n**Payment Information:**\nStripe: " + link.getLink() + "\nPayPal: https://paypal.me/sysdm\nBTC: bc1qc9xwjjt62cez6g5e3y608820r6kc8qnt0ctfgv\nETH: 0x26E12a3b79b00ceb5fDAD5BdbdA84CD1c8C95F9f")
                .setColor(Color.decode("#3ab4ff"))
                .build()).queue();
    }
}
