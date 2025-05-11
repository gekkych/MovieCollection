package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientStatusRequest.class, name = "status"),
        @JsonSubTypes.Type(value = ClientCommandRequest.class, name = "command"),
        @JsonSubTypes.Type(value = ClientMovieDataRequest.class, name = "movie"),
        @JsonSubTypes.Type(value = ClientLoginOptionRequest.class, name = "loginOption"),
        @JsonSubTypes.Type(value = ClientAuthenticationRequest.class, name = "auth"),
        @JsonSubTypes.Type(value = ClientRegistrationRequest.class, name = "reg")
})
public interface Request {
    RequestStatus getStatus();
}
