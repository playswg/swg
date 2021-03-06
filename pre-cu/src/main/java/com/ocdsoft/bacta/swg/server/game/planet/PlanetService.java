package com.ocdsoft.bacta.swg.server.game.planet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.universe.planet.PlanetObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by crush on 5/28/2016.
 *
 * Planet Service is a Singleton service that manages the loading and communication of all the planet objects.
 * It is the entry point where the world snapshots will be loaded, as planets are brought online.
 */
@Singleton
public class PlanetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetService.class);

    private final Map<String, PlanetObject> planetObjectList;

    @Inject
    public PlanetService() {
        this.planetObjectList = new ConcurrentHashMap<>(50);
    }

    public PlanetObject getPlanetObject(final String planetName) {
        final PlanetObject planetObject = planetObjectList.get(planetName);

        if (planetObject == null)
            LOGGER.error("Attempted to fetch planet object for {}, but it does not exist.", planetName);

        return planetObject;
    }
}
