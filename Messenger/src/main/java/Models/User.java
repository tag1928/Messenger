package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
    @JsonProperty("username") String username,
    @JsonProperty("password") String password)
{}