package com.dac.chatting;

import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.stream.Materializer;
import com.dac.chatting.api.MainApi;

import java.util.Optional;

import static java.util.Objects.nonNull;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Server {

    private final LoggingAdapter logger;
    private final ActorSystem actorSystem;
    private final Integer port;
    private final Materializer materializer;
    private final MainApi api;

    public Server(final Integer port, final ActorSystem actorSystem, final Materializer materializer, final MainApi api) {
        this(port, actorSystem, materializer, api, Optional.empty());
    }

    public Server(final Integer port, final ActorSystem actorSystem, final Materializer materializer, final MainApi api, Optional<LoggingAdapter> logger) {
        this.port = port;
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.api = api;
        this.logger = logger.orElseGet(() -> Logging.getLogger(this.actorSystem, this.getClass()));
    }

    public void run() {
        Http
            .get(this.actorSystem)
            .bindAndHandle(
                this.api.mainRoute().flow(this.actorSystem, this.materializer),
                ConnectHttp.toHost("0.0.0.0", this.port), this.materializer
            );

        this.logger.info("Server online at http://localhost:" + this.port);

        this.actorSystem
            .getWhenTerminated()
            .toCompletableFuture()
            .handle((terminated, throwable) -> {
                if (nonNull(throwable))
                    this.logger.error("Failed Terminating process.", throwable);
                else
                    this.logger.info("Process Terminated.");
                return terminated;
            });
    }
}
