package br.com.marcia.error.api.v1.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    @ApiModelProperty
    private Long id;

    @ApiModelProperty
    private String fullName;

    @ApiModelProperty
    private String username;

    @ApiModelProperty
    private String email;

}
