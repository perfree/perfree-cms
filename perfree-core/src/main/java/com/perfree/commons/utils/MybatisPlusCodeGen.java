package com.perfree.commons.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.sql.Types;
import java.util.Collections;

/**
 * @author Perfree
 * @description MybatisPlus代码生成
 * @date 15:32 2023/9/28
 */
public class MybatisPlusCodeGen {
    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://127.0.0.1:3306/perfree?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true",
                        "root", "215607..")
                .globalConfig(builder -> {
                    builder.author("perfree") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.entity("model").parent("com.perfree") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("p_"); // 设置过滤表前缀
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                })
                .execute();

    }
}
