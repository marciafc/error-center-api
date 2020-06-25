package br.com.marcia.error.api.v1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty(message = "The fullName field is mandatory.")
    @ApiModelProperty(required = true)
    private String fullName;

    @NotEmpty(message = "The username field is mandatory.")
    @ApiModelProperty(required = true)
    private String username;

    @Email
    @NotEmpty(message = "The email field is mandatory.")
    @ApiModelProperty(required = true)
    private String email;

    @NotEmpty(message = "The password field is mandatory.")
    @ApiModelProperty(required = true)
    private String password;
}
