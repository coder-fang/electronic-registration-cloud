package zone.jiefei.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zone.jiefei.utils.GetLatAndLngByAddressUtils;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("地址和经纬度")
public class AddressDataDto {

    @ApiModelProperty("全地址")
    private  String address;

    @ApiModelProperty("调用api返回json")
    private String coordinateJson;
    public String getCoordinateJson() {
        if(address!=null ){
            coordinateJson = GetLatAndLngByAddressUtils.getLatAndLngByAddress(address);
        }
        return  coordinateJson;
    }
}
