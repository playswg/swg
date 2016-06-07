package com.ocdsoft.bacta.soe.data.couchbase;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.object.NetworkObject;
import com.ocdsoft.bacta.swg.server.game.data.serialize.GameObjectByteSerializer;
import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.BaseSerializingTranscoder;
import net.spy.memcached.transcoders.Transcoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kburkhardt on 7/25/14.
 */

public class CouchbaseGameObjectTranscoder extends BaseSerializingTranscoder implements Transcoder<NetworkObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouchbaseGameObjectTranscoder.class);
    private final GameObjectByteSerializer serializer;

    @Inject
    public CouchbaseGameObjectTranscoder(final GameObjectByteSerializer serializer) {
        super(CachedData.MAX_SIZE);

        this.serializer = serializer;
    }

    @Override
    public CachedData encode(final NetworkObject networkObject) {
        LOGGER.trace("Serializing type: {}", networkObject.getClass());

        int flags = 0;
        byte[] data = serializer.serialize(networkObject);

        // Flags of some sort?

        return new CachedData(flags, data, CachedData.MAX_SIZE);
    }

    @Override
    public NetworkObject decode(CachedData d) {

        final NetworkObject object = serializer.deserialize(d.getData());

        LOGGER.trace("Deserializing type: {}", object.getClass());

        // Perhaps use flags of some sort?

        return object;
    }
}
