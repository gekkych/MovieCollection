package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(ClientCommandRequest.class),
        @JsonSubTypes.Type(ClientMovieDataRequest.class),
        @JsonSubTypes.Type(ClientErrorStatusRequest.class)
})
public interface Request {
    public RequestStatus getStatus();
}
