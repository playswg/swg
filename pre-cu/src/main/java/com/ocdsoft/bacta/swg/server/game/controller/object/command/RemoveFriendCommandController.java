package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.StringUtil;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@QueuesCommand("removeFriend")
public final class RemoveFriendCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(RemoveFriendCommandController.class);

    private final GameChatService chatService;

    @Inject
    public RemoveFriendCommandController(final GameChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        final String firstName = StringUtil.getFirstWord(params);
        chatService.removeFriend(chatService.constructChatAvatarId(actor).getFullName(), firstName);
    }
}