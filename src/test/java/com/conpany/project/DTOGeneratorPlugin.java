package com.conpany.project;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xuzefan  2019/7/12 16:30
 */
public class DTOGeneratorPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        DTOJavaGenerator repository = new DTOJavaGenerator();
        repository.setContext(context);
        repository.setIntrospectedTable(introspectedTable);
        List units = repository.getCompilationUnits();
        List generatedFile = new ArrayList();
        GeneratedJavaFile gif;
        for (Iterator iterator = units.iterator(); iterator.hasNext(); generatedFile.add(gif)) {
            CompilationUnit unit = (CompilationUnit) iterator.next();
            gif = new GeneratedJavaFile(unit,context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    context.getProperty("javaFileEncoding"),context.getJavaFormatter());
        }
        return generatedFile;
    }
}
