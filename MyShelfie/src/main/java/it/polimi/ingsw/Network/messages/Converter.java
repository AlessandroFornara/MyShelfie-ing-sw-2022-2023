package it.polimi.ingsw.Network.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Network.messages.Answers.MoveAnswer;
import it.polimi.ingsw.Network.messages.Answers.NumberOfPlayersAnswer;
import it.polimi.ingsw.Network.messages.Answers.UsernameAnswer;
import it.polimi.ingsw.Network.messages.ErrorMessages.*;

/**
 * This class is used to convert a {@link Message} using JSON
 * @author Alessandro Fornara
 */
public class Converter {

    /**
     * This method converts a message from a JSON string to a {@link Message}
     * @author Alessandro Fornara
     * @param message
     * @return a {@link Message} object
     */
    public Message convertFromJSON(String message){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String type = jsonObject.get("type").getAsString();
        switch(type) {
            case "FirstPlayer":
                return gson.fromJson(message, FirstPlayerMessage.class);
            case "Waiting":
                return gson.fromJson(message, WaitingMessage.class);
            case "NumberOfPlayers":
                return gson.fromJson(message, NumberOfPlayersAnswer.class);
            case "Lobby":
                return gson.fromJson(message, LobbyMessage.class);
            case "StartingGame":
                return gson.fromJson(message, StartingGameMessage.class);
            case "ChooseUsername":
                return gson.fromJson(message, ChooseUsernameMessage.class);
            case "GameInformation":
                return gson.fromJson(message, GameInformationMessage.class);
            case "UsernameAnswer":
                return gson.fromJson(message, UsernameAnswer.class);
            case "Move":
                return gson.fromJson(message, MoveAnswer.class);
            case "NotValidNumber":
                return gson.fromJson(message, NotValidNumberofPlayersMessage.class);
            case "NotValidUsername":
                return gson.fromJson(message, NotValidUsernameError.class);
            case "NotValidGameId":
                return gson.fromJson(message, NotValidGameIdError.class);
            case "GraphicalGameInformation":
                return gson.fromJson(message, GraphicalGameInfo.class);
            case "NotValidMove":
                return gson.fromJson(message, NotValidMoveError.class);
            case "ListOfLobbies":
                return gson.fromJson(message, ListOfLobbies.class);
            case "NotEnoughSpaceMove":
                return gson.fromJson(message, NotEnoughSpaceMoveError.class);
            case "InvalidColumn":
                return gson.fromJson(message, InvalidColumnError.class);
            case "EmptyPosition":
                return gson.fromJson(message, EmptyPositionError.class);
            case "NotAdjTiles":
                return gson.fromJson(message, NotAdjacTiles.class);
        }
        return null;
    }

    /**
     * This method converts a {@link Message} to a JSON string
     * @author Alessandro Fornara
     * @param m message
     * @return a string
     */
    public String convertToJSON(Message m){
        Gson gson = new Gson();
        return gson.toJson(m);
    }
}
