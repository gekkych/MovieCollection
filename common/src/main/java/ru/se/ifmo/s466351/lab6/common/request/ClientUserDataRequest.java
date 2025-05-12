package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.se.ifmo.s466351.lab6.common.dto.UserDTO;

@JsonTypeName("user")
public record ClientUserDataRequest(RequestStatus status, UserDTO userData) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
