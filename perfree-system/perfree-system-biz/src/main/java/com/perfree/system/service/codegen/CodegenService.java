package com.perfree.system.service.codegen;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;

public interface CodegenService {
    List<TableInfo> getTableList();

}
