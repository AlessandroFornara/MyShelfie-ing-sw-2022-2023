package it.polimi.ingsw.Network.messages.ErrorMessages;

import it.polimi.ingsw.Network.messages.Message;

/**
 * Message that is sent by the server to a client when the chosen username is not valid (already taken)
 */
public final class NotValidUsernameError extends Message {

    private final String s;
    public NotValidUsernameError() {
        super("NotValidUsername");
        this.s = "Select another username, this has been already selected.";
    }

    public String getS() {
        return s;
    }
}
