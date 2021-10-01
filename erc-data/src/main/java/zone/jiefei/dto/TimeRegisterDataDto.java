package zone.jiefei.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("每小时的报到率")
public class TimeRegisterDataDto implements Serializable {
    @ApiModelProperty(value = "小时")
    private String hour;
    @ApiModelProperty(value = "报到人数")
    private Integer registerNum;
    @ApiModelProperty(value = "批次")
    private Integer batch;
}
