package lt.jonas.homework.playsite.model.api.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record KidRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Min(value = 1, message = "Age must be at least 1")
        @Max(value = 16, message = "Age must not exceed 16")
        int age,

        @NotNull(message = "Ticket number must not be null")
        Long ticketNumber
) {}
