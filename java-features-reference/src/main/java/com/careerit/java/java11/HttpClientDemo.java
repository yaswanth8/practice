package com.careerit.java.java11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * Java 11 - standard HttpClient (JEP 321).
 *
 * Replaces HttpURLConnection. Supports HTTP/1.1, HTTP/2, WebSocket,
 * sync + async, and reactive streams for bodies.
 *
 * The sample requests are commented out to avoid hitting the network during
 * offline builds; uncomment to try them.
 */
public class HttpClientDemo {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.com"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        System.out.println("HttpClient built: " + client.version());
        System.out.println("Request URI: " + request.uri());

        // Synchronous:
        // HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println("status=" + res.statusCode() + ", body chars=" + res.body().length());

        // Asynchronous:
        CompletableFuture<HttpResponse<String>> future =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        // future.thenAccept(r -> System.out.println("async status=" + r.statusCode())).join();
        System.out.println("async future created: " + (future != null));
    }
}
