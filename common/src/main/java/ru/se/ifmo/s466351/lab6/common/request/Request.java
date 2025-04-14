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
        @JsonSubTypes.Type(value = ClientMovieDataRequest.class, name = "movie")
})
public interface Request {
    public RequestStatus getStatus();
}
