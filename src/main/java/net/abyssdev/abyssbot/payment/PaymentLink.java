package net.abyssdev.abyssbot.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class PaymentLink {

    private final String formattedName, link, price, versions;

}
