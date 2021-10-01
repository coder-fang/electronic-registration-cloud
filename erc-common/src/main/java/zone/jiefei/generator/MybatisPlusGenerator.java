package zone.jiefei.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.Scanner;

/**
 * 功能描述：mybatis-plus的代码生成器
 *
 * @author RenShiWei
 * Date: 2020/7/5 15:16
 **/
public class MybatisPlusGenerator {

    private GlobalConfig gc;

    public static void main (String[] args ) {

        // 全局配置
        GlobalConfig gc = new GlobalConfig();



        //因为所有在java.io中的类都是将相对路径名解释为起始于用户的当前工作目录，所以应该清楚当前的目录。
        String projectPath = System.getProperty("user.dir");
        String modulePath = scanner("在项目指定模块下生成代码");
        //获取指定项目模块的路径，用于输出路径
        File file = new File(modulePath);
        projectPath = file.getAbsolutePath();
        gc.setOutputDir(projectPath + "/src/main/java")
                .setAuthor("Wangmingcan")
                .setOpen(false)
                .setIdType(IdType.AUTO)  //主键策略
                .setFileOverride(false) // 文件覆盖
                .setActiveRecord(true)  //设置支持ActiveRecord   继承Model
                .setSwagger2(true); //实体属性 Swagger2 注解

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://localhost:3306/electroic_registration_result?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("12345");

        // 包配置
        PackageConfig pc = new PackageConfig();
        String parentPath = scanner("父级包");
        String sonPath = scanner("生成在父级包下的指定文件夹（不输入代表直接在父级包下生成）");
        pc.setParent(parentPath)
                .setModuleName(sonPath);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略（下划线处理）
                .setColumnNaming(NamingStrategy.underline_to_camel)  // 数据库字段映射到实体的命名策略（下划线处理）
                .setCapitalMode(true)  // 全局大写命名

                .setEntityLombokModel(true)
                .setRestControllerStyle(true);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        // 代码生成器（将配置设置进代码生成器）
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc)
                .setDataSource(dsc)
                .setStrategy(strategy)
                .setTemplate(templateConfig)
                .setPackageInfo(pc);
        //执行
        mpg.execute();

    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner ( String tip ) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!"".equals(ipt) && ipt != null) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}

