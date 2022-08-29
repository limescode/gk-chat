package pl.limescode.server.chat.auth;

import pl.limescode.server.db.DatabaseManager;

public class AuthService {

    private final DatabaseManager manager;

    public AuthService() {
        manager = DatabaseManager.getDatabaseManager();
    }

    public String getUsernameByLoginAndPassword(String login, String password) {
        if (manager.authenticateUser(login, password))
            return login;
        else
            return null;
    }
}
