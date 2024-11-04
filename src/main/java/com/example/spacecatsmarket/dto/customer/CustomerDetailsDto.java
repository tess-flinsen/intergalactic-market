package com.example.spacecatsmarket.dto.customer;

import com.example.spacecatsmarket.dto.validation.ExtendedValidation;
import com.example.spacecatsmarket.dto.validation.ValidSpaceAddress;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ CustomerDetailsDto.class, ExtendedValidation.class})
public class CustomerDetailsDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    @ValidSpaceAddress(groups = ExtendedValidation.class)
    String address;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number must be valid")
    String phoneNumber;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    String email;

}