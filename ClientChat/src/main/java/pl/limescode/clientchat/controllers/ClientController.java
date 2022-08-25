package pl.limescode.clientchat.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.limescode.clientchat.commands.ClientMessageCommandData;
import pl.limescode.clientchat.commands.Command;
import pl.limescode.clientchat.commands.CommandType;
import pl.limescode.clientchat.commands.UpdateUserListCommandData;
import pl.limescode.clientchat.dialogs.Dialogs;
import pl.limescode.clientchat.model.Network;
import pl.limescode.clientchat.model.ReadMessageListener;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class ClientController {

    @FXML
    public TextField messageTextArea;

    @FXML
    public Button sendMessageButton;

    @FXML
    public TextArea chatTextArea;

    @FXML
    public ListView userList;

    public void sendMessage() {
        String message = messageTextArea.getText();

        if (message.isEmpty()) {
            messageTextArea.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = userList.getSelectionModel().getSelectedItem().toString();
        }

        try {
            if (sender != null) {
                Network.getInstance().sendPrivateMessage(sender, message);
            } else {
                Network.getInstance().sendMessage(message);
            }

        } catch (IOException e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
        }

        appendMessageToChat("Я", message);
        requestFocusForTextArea();
        messageTextArea.clear();
    }

    public void appendMessageToChat(String sender, String message) {
        chatTextArea.appendText(DateFormat.getInstance().format(new Date()));
        chatTextArea.appendText(System.lineSeparator());

        if (sender != null) {
            chatTextArea.appendText(sender + ":");
            chatTextArea.appendText(System.lineSeparator());
        }

        chatTextArea.appendText(message);
        chatTextArea.appendText(System.lineSeparator());
        chatTextArea.appendText(System.lineSeparator());

    }

    private void requestFocusForTextArea() {
        Platform.runLater(() -> messageTextArea.requestFocus());
    }

    public void initializeMessageHandler() {
        Network.getInstance().addReadMessageListener(new ReadMessageListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    appendMessageToChat(data.getSender(), data.getMessage());
                } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                    UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(() -> {
                        userList.setItems(FXCollections.observableArrayList(data.getUsers()));
                    });
                }

            }
        });
    }
}
