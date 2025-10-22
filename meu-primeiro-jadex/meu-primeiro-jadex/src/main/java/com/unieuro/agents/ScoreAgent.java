package com.unieuro.agents;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;

@Agent
public class ScoreAgent {

    @OnStart
    void start(IInternalAccess me) {
        // Placeholder for score tracking service
        me.createFuture(null);
    }
}
