package pl.limescode.clientchat.model;

import javafx.application.Platform;
import pl.limescode.clientchat.ClientChat;
import pl.limescode.clientchat.dialogs.Dialogs;

import java.util.Date;
import java.util.TimerTask;

public class AuthTimeout extends TimerTask {

    @Override
    public void run() {
        try {
            Thread.sleep(120_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            if (!Network.getInstance().isConnected()) {
                Dialogs.AuthTimeout.AUTH_TIMEOUT.show();
                ClientChat.getInstance().getAuthStage().close();
                ClientChat.getInstance().getChatStage().close();
                System.out.println("Timer timeoutTask finished at: " + new Date());
            }
        });
    }

}
