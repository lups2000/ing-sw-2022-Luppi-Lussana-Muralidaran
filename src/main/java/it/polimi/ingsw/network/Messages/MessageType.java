package it.polimi.ingsw.network.Messages;

/**
 * This enum defines all the messages that are used by the server and the clients
 */
public enum MessageType {

    REQUEST_LOGIN,REPLY_LOGIN,
    REQUEST_PLAYER_NUM,REPLY_PLAYER_NUM,
    LOBBY,
    REQUEST_EXPERT_VARIANT,REPLY_EXPERT_VARIANT,
    REQUEST_ASSISTANT_SEED,REPLY_ASSISTANT_SEED,
    REQUEST_ASSISTANT_CARD,REPLY_ASSISTANT_CARD,
    REQUEST_MOVE_MOTHER_NATURE,REPLY_MOVE_MOTHER_NATURE,
    REQUEST_MOVE_STUD_DINING,REPLY_MOVE_STUD_DINING,
    REQUEST_MOVE_STUD_ISLAND,REPLY_MOVE_STUD_ISLAND,
    REQUEST_CLOUD_TILE,REPLY_CLOUD_TILE,
    REQUEST_CHARACTER_CARD,REPLY_CHARACTER_CARD,
    REQUEST_ISLAND,REPLY_ISLAND,
    REQUEST_COLOR,REPLY_COLOR,
    REQUEST_STUDENT_OR_STOP,REPLY_STUDENT_OR_STOP,
    WIN,LOSE,
    INFO_MATCH,
    INFO_ISLANDS,
    GAME_BOARD,
    INFO_CLOUDS,
    MOVE_STUD,
    SCHOOLBOARD,
    SHOW_CHARACTER_CARD,
    DISCONNECTION,
    UPDATEFX,

    GENERIC_MESSAGE,
    ERROR,
    PING,
}
