package com.ocdsoft.bacta.swg.server.game.message.planet;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.server.game.message.MapLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crush on 5/28/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class GetMapLocationsResponseMessage extends GameNetworkMessage {
    private final String planetName;
    private final List<MapLocation> mapLocationsStatic;
    private final List<MapLocation> mapLocationsDynamic;
    private final List<MapLocation> mapLocationsPersist;
    private final int versionStatic;
    private final int versionDynamic;
    private final int versionPersist;

    public GetMapLocationsResponseMessage(final ByteBuffer buffer) {
        this.planetName = BufferUtil.getAscii(buffer);
        this.mapLocationsStatic = BufferUtil.getArrayList(buffer, MapLocation::new);
        this.mapLocationsDynamic = BufferUtil.getArrayList(buffer, MapLocation::new);
        this.mapLocationsPersist = BufferUtil.getArrayList(buffer, MapLocation::new);
        this.versionStatic = buffer.getInt();
        this.versionDynamic = buffer.getInt();
        this.versionPersist = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, planetName);
        BufferUtil.put(buffer, mapLocationsStatic);
        BufferUtil.put(buffer, mapLocationsDynamic);
        BufferUtil.put(buffer, mapLocationsPersist);
        BufferUtil.put(buffer, versionStatic);
        BufferUtil.put(buffer, versionDynamic);
        BufferUtil.put(buffer, versionPersist);
    }
}