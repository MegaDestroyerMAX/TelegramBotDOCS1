package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    //final private String BOT_TOKEN = "5768055802:AAFMowP49VVejd5l2P7ji3XSlBsZDyIpoFo";
    //final private String BOT_NAME = "BotikDOCS_bot";
    Storage storage;


    Bot()
    {
        storage = new Storage();
    }

    @Override
    public String getBotUsername() {
        return "BotikDOCS_bot";
    }

    @Override
    public String getBotToken() {
        return "5768055802:AAFMowP49VVejd5l2P7ji3XSlBsZDyIpoFo";
    }

    @Override
    public void onUpdateReceived(Update update){
        try{
            if(update.hasMessage() && update.getMessage().hasText()){
                Message inMess = update.getMessage();               //Извлекаем из обьекта сообщение пользователя
                String chatId = inMess.getChatId().toString();      //Достаем из inMess id чата пользователя
                String response = parseMessage(inMess.getText());   //Получаем текст сообщения пользователя, отправляем в написанный нами обраточик(parseMessage)
                SendMessage outMess = new SendMessage();            //Создаем обьект класса SendMessage - наш будущий ответ пользователю

                outMess.setChatId(chatId);
                outMess.setText(response);                          //Добавляем в наше сообщение Id чата а также наш ответ
                initKeyBoard(outMess);

                execute(outMess);                                   //Отправляем в чат (execute - выполняет любые команды и возвращает значение boolean: true - если команда возвращает набор строк (SELECT))
            }
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    public String parseMessage(String textMsg){
        String response;

        if(textMsg.equals("/start")){
            response = "Привет, я знаю полный бред! Жми /get, чтобы получить случайную из них";
        } else if (textMsg.equals("/get") || textMsg.equals("Покажи")) {
            response = storage.getRandQuote();
        } else
            response = "не пон >_<";
        return response;
    }

    void initKeyBoard(SendMessage message){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();        //Создаем список с рядами кнопок
        KeyboardRow keyboardRow = new KeyboardRow();                    //Создаем один ряд конопок и добавляем его в список
        keyboardRows.add(keyboardRow);
        keyboardRow.add(new KeyboardButton("Покажи"));             //Добавляем кнопку с текстом
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}
