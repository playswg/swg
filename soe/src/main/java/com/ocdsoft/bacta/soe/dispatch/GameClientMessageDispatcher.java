package com.ocdsoft.bacta.soe.dispatch;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.GameClientMessageController;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import gnu.trove.map.TIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
public final class GameClientMessageDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameClientMessageDispatcher.class);

    private final TIntObjectMap<ControllerData<GameClientMessageController>> controllers;
    private final ServerState serverState;

    @Inject
    public GameClientMessageDispatcher(final ClasspathControllerLoader controllerLoader,
                                       final ServerState serverState) {

        LOGGER.debug("Loading");

        this.controllers = controllerLoader.getControllers(GameClientMessageController.class);
        this.serverState = serverState;

        controllers.forEachEntry((key, data) -> {
            LOGGER.debug("Loaded GameClientMessageController {} for messageType {}", data.getController().getClass().getSimpleName(), key);
            return true;
        });
    }

    public void dispatch(final long[] distributionList, final boolean reliable, final int messageType, final GameNetworkMessage message, final SoeUdpConnection connection) {
        final ControllerData<GameClientMessageController> controllerData = controllers.get(messageType);

        if (controllerData != null) {
            if (!controllerData.containsRoles(connection.getRoles())) {
                LOGGER.error("{} Controller security blocked access: {}", serverState.getServerType(), controllerData.getController().getClass().getName());
                LOGGER.error("Connection: " + connection.toString());
                return;
            }

            try {
                final GameClientMessageController controller = controllerData.getController();

                LOGGER.debug("Dispatching GameClientMessage with client message {} to {} clients.",
                        message.getClass().getSimpleName(),
                        distributionList.length);

                controller.handleIncoming(distributionList, reliable, message, connection);

            } catch (Exception e) {
                LOGGER.error("SWG Message Handling {}", controllerData.getClass(), e);
            }
        } else {
            LOGGER.trace("No loaded controller for GameClientMessage with messageType {}. Silently failing.", messageType);
        }
    }
}
