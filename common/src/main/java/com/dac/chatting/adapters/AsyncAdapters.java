package com.dac.chatting.adapters;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@SuppressWarnings("ResultOfMethodCallIgnored")
public interface AsyncAdapters {

    /**
     * Converts a RxJava Single into a Java Completion Stage
     *
     * @param single RxJava Single
     * @param <T>    Adapter value
     * @return new {@link CompletionStage} instance
     * @see Single
     * @see CompletionStage
     */
    @NotNull
    static <T> CompletionStage<T> adaptSingle(@NotNull Single<T> single) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        single.subscribe(future::complete, future::completeExceptionally);
        return future;
    }

    /**
     * Converts a Java Completion Stage into a RxJava Single
     *
     * @param completionStage Java Completion Stage
     * @param <T>             Adapter value
     * @return new {@link Single} instance
     * @see Single
     * @see CompletionStage
     */
    @NotNull
    static <T> Single<T> adaptCompletionStage(CompletionStage<T> completionStage) {
        return Single.create(subscriber ->
            completionStage.handle((t, throwable) -> {
                if (t != null)
                    subscriber.onSuccess(t);
                else
                    subscriber.onError(throwable);
                return t;
            })
        );
    }

    /**
     * Converts a RxJava Observable into a Java Completion Stage
     *
     * @param observable RxJava Observable
     * @param <T>        Adapter value
     * @return new {@link CompletionStage} instance
     */
    @NotNull
    static <T> CompletionStage<T> adaptObservable(@NotNull Observable<T> observable) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        observable.subscribe(future::complete, future::completeExceptionally);
        return future;
    }
}
