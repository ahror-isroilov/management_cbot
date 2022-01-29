package bot.states;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionState {
    DEFAULT("default"),
    SEND_MESSAGE("send_message"),
    ADD_WORKER("add_worker"),
    DELETE_WORKER("delete_worker"),
    CHOOSE_GROUP("choose_group");

    private final String code;
}
