package it.polimi.ingsw.Messages;

/**
 * This enum defines all the messages that are used by the server and the clients
 */
public enum MessageType {

    REQUEST_LOGIN,REPLY_LOGIN,
    REQUEST_PLAYER_NUM,REPLY_PLAYER_NUM,
    LOBBY,
    REQUEST_EXPERT_VARIANT,REPLY_EXPERT_VARIANT,
    PICK_ASSISTANT,
    MOVE_MOTHER_NATURE,
    MOVE_STUD_DINING,
    MOVE_STUD_ISLAND,
    WIN,LOSE,

    GAME_INIT,
    GENERAL_MESSAGE,
    ERROR,

    //TODO
}
