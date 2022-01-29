package bot.handlers;

import bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public class OtherHandlers extends AbstractMethods implements IBaseHandler {
    SendMessage sendMessage;

    @Override
    public void handle(Update update) {
        Bot bot = Bot.getInstance();
        if (update.getMyChatMember().getNewChatMember().getStatus().equals("member") && update.getMyChatMember().getNewChatMember().getUser().getUserName().equals("Emma_cbot")) {
            if(Objects.nonNull(update.getMyChatMember().getChat().getId()) && Objects.nonNull(update.getMyChatMember().getChat().getTitle()) ){
                g_repository.addGroup(update.getMyChatMember().getChat().getId(), update.getMyChatMember().getChat().getTitle());
                sendMessage = msgObject(update.getMyChatMember().getFrom().getId(), """
                        <b>Assalomu alaykum</b> 😊
                                            
                        <i>Siz botni <b>"%s"</b> guruhiga qo'shdingiz</i>""".formatted(update.getMyChatMember().getChat().getTitle()));
                bot.executeMessage(sendMessage);
            }
        } else if (update.getMyChatMember().getNewChatMember().getStatus().equals("left") && update.getMyChatMember().getNewChatMember().getUser().getUserName().equals("Emma_cbot")) {
            g_repository.delGroup(update.getMyChatMember().getChat().getId());
        }
    }

    private static final OtherHandlers instance = new OtherHandlers();

    public static OtherHandlers getInstance() {
        return instance;
    }
}
