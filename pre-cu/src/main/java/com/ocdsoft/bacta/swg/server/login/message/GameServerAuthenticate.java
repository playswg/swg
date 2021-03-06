package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/24/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public class GameServerAuthenticate extends GameNetworkMessage {

    private final byte[] serverName;
    private final byte[] serverKey;

    public GameServerAuthenticate(final ByteBuffer buffer) {
        this.serverName = new byte[buffer.getShort()];
        buffer.get(this.serverName);

        this.serverKey = new byte[buffer.getShort()];
        buffer.get(this.serverKey);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putShort((short) serverName.length);
        buffer.put(serverName);

        buffer.putShort((short) serverKey.length);
        buffer.put(serverKey);
    }
}