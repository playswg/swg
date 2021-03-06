package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 * <p>
 * Sends an instant message request from the game server to the chat server.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public class ChatInstantMessageFromGame extends GameNetworkMessage {
    private final String to;
    private final String from;
    private final String message; //Unicode
    private final String outOfBand; //Unicode

    public ChatInstantMessageFromGame(final ByteBuffer buffer) {
        this.to = BufferUtil.getAscii(buffer);
        this.from = BufferUtil.getAscii(buffer);
        this.message = BufferUtil.getUnicode(buffer);
        this.outOfBand = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, to);
        BufferUtil.putAscii(buffer, from);
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.putUnicode(buffer, outOfBand);
    }
}
