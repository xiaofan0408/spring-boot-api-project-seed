package ${basePackage}.${packName}.service.impl;

import ${basePackage}.${packName}.dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.${packName}.model.entity.${modelNameUpperCamel};
import ${basePackage}.${packName}.service.${modelNameUpperCamel}Service;
import ${basePackage}.common.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
