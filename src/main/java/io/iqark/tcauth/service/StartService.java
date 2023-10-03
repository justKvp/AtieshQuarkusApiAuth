package io.iqark.tcauth.service;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@Startup
public class StartService {
    @Inject
    AccessService accessService;
    void onStart(@Observes StartupEvent event) {
        accessService.fillAccountAccessList();
    }
}
