package br.com.marcia.error.api.v1.dto.request;

import br.com.marcia.error.enumeration.LevelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

    @NotNull(message = "The level field is mandatory.")
    @ApiModelProperty(required = true)
    private LevelEnum level;

    @NotEmpty(message = "The description field is mandatory.")
    @ApiModelProperty(required = true)
    private String description;

    @NotEmpty(message = "The log field is mandatory.")
    @ApiModelProperty(required = true)
    private String log;

    @NotEmpty(message = "The origin field is mandatory.")
    @ApiModelProperty(required = true)
    private String origin;

}
