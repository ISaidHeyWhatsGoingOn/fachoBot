import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class fachoBot extends TelegramLongPollingBot {

    ArrayList<String> fachoList = new ArrayList<String>();
    ArrayList<String> macriPhotos = new ArrayList<>();
    File macriphotosdir = new File("src/main/resources/macriphotos");
    File[] macriphotos = macriphotosdir.listFiles();


    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            //Si el mensaje es un comando:
            if(update.getMessage().isCommand()) {
                if (update.getMessage().getText().equals(Commands.facho)) {
                    facho(update);
                }
                if (update.getMessage().getText().equals(Commands.macri)) {
                    macri(update);
                }
                if (update.getMessage().getText().equals(Commands.recomendarFrase)) {
                    recomendarFraseFacha(update);
                }
            }

            //Si el mensaje no es un comando:
            else { SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(("No te entiendo zurdito"));

                try {
                    sendMessage(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();

                }
            }


        }

    }






    public void facho(Update update) {

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId());

        try {

            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/frasesFachas.txt"));


            while ((currentLine = br.readLine()) != null) {
                fachoList.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rnd = new Random().nextInt(fachoList.size());

        message.setText(fachoList.get(rnd));

        //Envia el mensaje
        try {
            sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }

    }

    public void recomendarFraseFacha(Update update) {
        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Dale, lo agrego");
        fachoList.add(update.getMessage().getText());

        //Envia el mensaje
        try {
            sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }

    }


    public void macri(Update update) {

        Random rand = new Random();
        File file = macriphotos[rand.nextInt(macriphotos.length)];


        SendPhoto photo = new SendPhoto()
                .setChatId(update.getMessage().getChatId())
                .setPhoto("https://raw.githubusercontent.com/ISaidHeyWhatsGoingOn/fachoBot/master/src/main/resources/macriphotos/" + file.getName());


        //Envia el mensaje
        try {

            sendPhoto(photo); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }


    public String getBotUsername() {
        return "El Mauri";
    }

    @Override
    public String getBotToken() {
        return "431645665:AAFxaQidqLsFvIHLbHGQrj__iyARf0Lusnw";
    }
}
