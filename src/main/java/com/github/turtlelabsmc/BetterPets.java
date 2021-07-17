package com.github.turtlelabsmc;

import com.github.turtlelabsmc.event.AssignGoalsEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterPets implements ModInitializer
{
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize()
    {
        LOGGER.info("Initializing BetterPets mod!");
        ServerEntityEvents.ENTITY_LOAD.register(AssignGoalsEvent::assignAllGoals);
    }

}
