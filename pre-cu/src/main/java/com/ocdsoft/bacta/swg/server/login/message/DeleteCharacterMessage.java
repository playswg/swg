package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      02 00 00 00 8B 04 00 00 01 00 00 00 00 A9 3A 

  SOECRC32.hashCode(DeleteCharacterMessage.class.getSimpleName()); // 0xe87ad031
  */
@Getter
@AllArgsConstructor
@Priority(0x3)
public final class DeleteCharacterMessage extends GameNetworkMessage {

    private final int clusterId;
    private final long characterId;

    public DeleteCharacterMessage(final ByteBuffer buffer) {
        clusterId = buffer.getInt();
        characterId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(clusterId);
        buffer.putLong(characterId);
    }
}
