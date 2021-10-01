package zone.jiefei.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("最近20条报到数据封装")
public class RecentRegisterDataDto implements Serializable {
    @ApiModelProperty(value = "姓名")
    private String name;
    @JsonFormat(pattern = "MM-dd HH:mm")
    @ApiModelProperty(value = "时间")
    @TableField("created_at")
    private Date createdAt;
    @ApiModelProperty(value =  "班级")
    private String classes;
}
