package com.admeliora.briefbot.user.api.doc;

import com.admeliora.briefbot.user.api.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get all users",
        description = "Retrieves a list of all registered users.",
        operationId = "getAllUsers"
)
@ApiResponse(
        responseCode = "200",
        description = "Successful retrieval of user list",
        content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
        )
)
public @interface GetAllUsersOperation {
}