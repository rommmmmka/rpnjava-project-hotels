package com.kravets.hotels.rpnjava.bot;

import com.kravets.hotels.rpnjava.bot.BotConfig;
import com.kravets.hotels.rpnjava.data.entity.OrderEntity;
import com.kravets.hotels.rpnjava.data.entity.StatusEntity;
import com.kravets.hotels.rpnjava.exception.OrderAlreadyPayedException;
import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;

@Component
public class BotController extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final Services services;

    public BotController(BotConfig botConfig, Services services) {
        this.botConfig = botConfig;
        this.services = services;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public String getBotPaymentToken() {
        return botConfig.getPaymentToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                if (messageText.length() > 7 && messageText.startsWith("/start ")) {
                    startCommandReceived(messageText.substring(7), String.valueOf(chatId));
                }
            } else if (update.getMessage().hasSuccessfulPayment()) {
                successfulPaymentReceived(update.getMessage().getSuccessfulPayment(), chatId);
            }
        } else if (update.hasPreCheckoutQuery()) {
            preCheckoutQueryReceived(update.getPreCheckoutQuery());
        }
    }

    private void startCommandReceived(String parameter, String chatId) {
        try {
            long orderId = Long.parseLong(parameter);

            OrderEntity orderEntity = services.order.getOrderByIdOrElseThrow(orderId);
            if (orderEntity.getStatus().getId() != 2) {
                throw new OrderAlreadyPayedException();
            }

            execute(SendMessage.builder()
                    .chatId(chatId)
                    .parseMode("Markdown")
                    .text("""
                            Мінімальная сума аплаты - 3 рублі!
                            Для аплаты скарыстайцеся тэставай картай:
                            `4242424242424242`
                            Увядзіце любы валідны тэрмін дзеяння і CVC""")
                    .build()
            );

            String title = orderEntity.getRoom().getName() + " | " + orderEntity.getRoom().getHotel().getName();
            if (title.length() > 32) {
                title = orderEntity.getRoom().getName();
                if (title.length() > 32) {
                    title = title.substring(30) + "...";
                }
            }
            String description = orderEntity.getRoom().getDescription();
            if (description.length() > 255) {
                description = description.substring(253) + "...";
            }
            if (description.length() == 0) {
                description = "У гэтага нумара няма апісання";
            }
            Integer cost = Math.toIntExact(Math.max(orderEntity.getCost(), 3) * 100);

            execute(SendInvoice.builder()
                    .chatId(chatId)
                    .currency("BYN")
                    .providerToken(getBotPaymentToken())
                    .title(title)
                    .description(description)
                    .payload(parameter)
                    .startParameter(parameter)
                    .price(new LabeledPrice("Перадаплата пражывання", cost))
                    .build()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void successfulPaymentReceived(SuccessfulPayment successfulPayment, String chatId) {
        try {
            long orderId = Long.parseLong(successfulPayment.getInvoicePayload());
            OrderEntity orderEntity = services.order.getOrderByIdOrElseThrow(orderId);

            StatusEntity statusEntity = services.status.getStatusByIdOrElseThrow(3);
            orderEntity.setStatus(statusEntity);
            orderEntity.setExpireDateTime(null);
            services.order.editOrder(orderEntity);

            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text("Заказ паспяхова аплачаны!")
                    .build()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void preCheckoutQueryReceived(PreCheckoutQuery preCheckoutQuery) {
        try {
            execute(AnswerPreCheckoutQuery.builder()
                    .preCheckoutQueryId(preCheckoutQuery.getId())
                    .ok(true)
                    .build()

            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
