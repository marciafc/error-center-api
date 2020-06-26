package br.com.marcia.error.api.v1.dto.response;

import br.com.marcia.error.enumeration.LevelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseInListDTO {

    @ApiModelProperty
    private Long id;

    @ApiModelProperty
    private LevelEnum level;

    @ApiModelProperty
    private String description;

    @ApiModelProperty
    private String origin;

    @ApiModelProperty
    private Integer quantity;
}
