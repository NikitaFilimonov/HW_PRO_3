package client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class History {
    private static final Integer LAST_MESSAGES = 100;
    private static PrintWriter out;

    private static String getHistoryFileByLogin(String login) {
        return "history/history_" + login + ".txt";
    }

    public static void start(String login) {
        try {
            out = new PrintWriter(new FileOutputStream(getHistoryFileByLogin(login), true), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (out != null) {
            out.close();
        }
    }

    public static void writeMessage(String msg) {
        out.println(msg);
    }

    public static String getLastMessages(String login) {
        if (!Files.exists(Paths.get(getHistoryFileByLogin(login)))) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            List<String> historyMessages = Files.readAllLines(Paths.get(getHistoryFileByLogin(login)));
            int startPosition = 0;
            if (historyMessages.size() > LAST_MESSAGES) {
                startPosition = historyMessages.size() - LAST_MESSAGES;
            }
            for (int i = startPosition; i < historyMessages.size(); i++) {
                sb.append(historyMessages.get(i)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}




