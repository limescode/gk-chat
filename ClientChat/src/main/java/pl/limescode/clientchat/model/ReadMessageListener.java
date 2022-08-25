package pl.limescode.clientchat.model;

import pl.limescode.clientchat.commands.Command;

public interface ReadMessageListener {

    void processReceivedCommand(Command command);

}
