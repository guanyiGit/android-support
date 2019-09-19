package mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class CodeGenerator {

    private static String path = System.getProperty("user.dir");

    private static String url = "jdbc:mysql://223.221.36.183:3306/dog_or_netword?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String username = "all@shl";
    private static String password = "all@@shl";
    private static String moduleName = "/";

    static String[] tbas = {
//            "t_platfrom",
//            "t_version_info",
            "t_dictionary",
            "t_dog_info",
            "t_dog_owner",
            "t_dog_owner_card",
            "t_immune_card",
            "t_vaccine_injection",
            "t_vaccine_register"
    };


    public static void main(String[] args) {
        boolean ifBag = false;

        String bag = "/codes";
        if (ifBag) {
            bag = "/src/main/java/";
        }
        // 代码生成器
        new AutoGenerator()
                .setGlobalConfig(globalConfig(path + "/" + moduleName + bag))
                .setDataSource(dataSourceConfig())
                .setStrategy(strategyConfig())
                .setPackageInfo(packageConfig())
                .setTemplateEngine(new VelocityTemplateEngine())
                .execute();


        System.out.println("success !!!");
    }


    private static GlobalConfig globalConfig(String path) {
        return new GlobalConfig().setActiveRecord(true)//ar
                .setAuthor("GuanY")
                .setOutputDir(path)
//                .setSwagger2(true)
                .setFileOverride(true)
                .setIdType(IdType.AUTO)
//                .setServiceName("%sService")
//                .setControllerName("%sController")
//                .setMapperName("%sMapper")
                .setBaseResultMap(true)
                .setOpen(false)
                .setBaseColumnList(true);

    }

    private static DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setDriverName(driver)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });

    }

    private static StrategyConfig strategyConfig() {
        return new StrategyConfig()
                .setCapitalMode(true)
//                .setDbColumnUnderline(true)//全局下划线命名
                .setEntityLombokModel(true)
//                .setSuperEntityClass("com.baomidou.ant.common.BaseEntity")
//                .setSuperControllerClass("com.baomidou.ant.common.BaseController")
                .setControllerMappingHyphenStyle(false)
                .setRestControllerStyle(true)
                .setNaming(NamingStrategy.underline_to_camel)
//                .setTablePrefix("")
                .setEntityBuilderModel(true)
                .setInclude(tbas);

    }

    private static PackageConfig packageConfig() {
        return new PackageConfig()
                .setParent("com.soholy")
//                .setModuleName(moduleName)
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setXml("xml");

    }


}