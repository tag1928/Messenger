package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(
    @JsonProperty("receiver_username") String receiverUsername,
    @JsonProperty("message") String message)
{}