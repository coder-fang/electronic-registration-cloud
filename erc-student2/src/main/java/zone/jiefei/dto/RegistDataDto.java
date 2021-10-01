package zone.jiefei.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("报道数据统计")
public class RegistDataDto implements Serializable {
    @ApiModelProperty(value = "学院/专业/班级 名称（tt）")
    private String  name;
    @ApiModelProperty(value = "学院/专业/班级 男/女/总 录取人数")
    private Integer all_num = 0;
    @ApiModelProperty(value = "学院/专业/班级 男/女/总 报到人数")
    private Integer register_num = 0;
    @ApiModelProperty(value = "学院/专业/班级 男/女/总 报道率")
    private Integer register_rate = 0;
    @ApiModelProperty(value = "学院简称")
    private String academyShortName ;
    @ApiModelProperty(value = "专业简称")
    private String majorShortName;
    @ApiModelProperty(value = "班级名")
    //为了获取专业简称 无其他作用
    private String classes;
    //获取学院简称
    public String getAcademyShortName() {
        String [] academyNames={
                "生命科技学院","经济与管理学院","机电学院",
                "食品学院","动物科技学院","园艺园林学院",
                "资源与环境学院","信息工程学院","化学化工学院",
                "艺术学院","服装学院","文法学院",
                "教育科学学院","数学科学学院","外国语学院",
                "体育学院","人工智能学院","国际教育学院"
        };
        String [] academyShortNames = {
                "生 科", "经 管", "机 电",
                "食 品", "动 科", "园 林",
                "资 环", "信 工", "化 工",
                "艺 术", "服 装", "文 法",
                "教 科", "数 学", "外 语",
                "体 育","智 能","国 教"
        };
        for (int i = 0; i < academyNames.length; i++) {
            if(this.name!=null){
                String newName = name.replace(" ","");
                if(newName.equals(academyNames[i])){
                    this.academyShortName = academyShortNames[i];
                }
            }
        }
        return academyShortName;
    }
//    获取专业简称
    public String getMajorShortName() {
        if(name!=null&&!"".equals(name)&&classes!=null&&!"".equals(classes)) {
            try{
                String classSub = classes.substring(0, classes.length()-3);
                this.majorShortName = name.endsWith("对")?classSub+"(对)":classSub;
            }catch (StringIndexOutOfBoundsException e){
                return majorShortName;
            }
        }
        return majorShortName;
    }
    //如果报到率大于0且不到1%，则按1%算

    public Integer getRegister_rate() {
        if(register_num>0&&register_rate<1) {
            register_rate = 1;
        }
        return register_rate;
    }
}
