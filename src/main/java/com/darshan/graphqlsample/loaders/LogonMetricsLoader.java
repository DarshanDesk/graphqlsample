package com.darshan.graphqlsample.loaders;

import com.darshan.graphqlsample.model.schema.LogonMetric;
import com.darshan.graphqlsample.services.LogonService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@DgsDataLoader(name = "logonMetrics")
public class LogonMetricsLoader implements MappedBatchLoader<String, List<LogonMetric>> {

    @Autowired
    private final LogonService logonService;

    public LogonMetricsLoader(LogonService logonService) {
        this.logonService = logonService;
    }

    @Override
    public CompletionStage<Map<String, List<LogonMetric>>> load(Set<String> channels) {
        log.info("Logon Metrics for following channels will be loaded: {}", channels);
        return CompletableFuture.supplyAsync(() -> logonService.getAllLogonMetrics(new ArrayList<>(channels)));
    }
}
