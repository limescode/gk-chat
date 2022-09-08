package pl.limescode.clientchat.history;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

    private static HistoryService instance;
    private static final int LINE_LIMIT_NUMBER = 100;

    public static HistoryService getInstance() {
        if (instance == null) {
            instance = new HistoryService();
        }
        return instance;
    }

    private HistoryService() {
        super();
    }

    public boolean saveHistory(String login, String text) {
        File history = new File("chat-" + login + ".txt");
        if (!history.exists()) {
            try {
                if (!history.createNewFile()) {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String filename = "chat-" + login + ".txt";
            File userHistory = new File(filename);
            PrintWriter writer = new PrintWriter(new FileWriter(userHistory, true));
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String loadHistory(String login) {
        StringBuilder sb = new StringBuilder();
        int maxLines = LINE_LIMIT_NUMBER;
        List<String> result = new ArrayList<>();
        try (var reader = new ReversedLinesFileReader(new File("chat-" + login + ".txt"), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null && result.size() < maxLines) {
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str : result) {
            sb.insert(0, str + "\n");
            maxLines--;
        }
        return sb.toString();
    }

}
