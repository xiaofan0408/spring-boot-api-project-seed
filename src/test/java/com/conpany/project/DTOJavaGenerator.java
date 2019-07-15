package com.conpany.project;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuzefan  2019/7/12 16:34
 */
public class DTOJavaGenerator extends AbstractJavaGenerator {

    private Field createField() {
        Field f = new Field();
        f.setVisibility(JavaVisibility.PRIVATE);
        f.setStatic(true);
        f.setFinal(true);
        f.setType(new FullyQualifiedJavaType("long"));
        f.setName("serialVersionUID = 1L");
        return f;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<CompilationUnit> getCompilationUnits() {
        String pack = context.getProperty("dtoTargetPackage");
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(genDto(pack));
        answer.add(genVo(pack));
        return answer;
    }

    private TopLevelClass genDto(String pack) {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        Plugin plugins = this.context.getPlugins();
        CommentGenerator commentGenerator = this.context.getCommentGenerator();

        String recordType = this.introspectedTable.getBaseRecordType();

        String pkg = recordType.substring(0, recordType.lastIndexOf("."));
        String entity = recordType.substring(recordType.lastIndexOf(".") + 1) + "DTO";

        pkg = pkg.substring(0, pkg.lastIndexOf(".") + 1) + "dto";

        String dtoType = pkg + "." + entity;

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(dtoType);

        TopLevelClass topLevelClass = new TopLevelClass(type);


        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));
        topLevelClass.addField(createField());

        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
//        topLevelClass.addImportedType("lombok.Getter");
//        topLevelClass.addImportedType("lombok.Setter");


        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        FullyQualifiedJavaType superClass = getSuperClass();
        if (superClass != null);

        commentGenerator.addModelClassComment(topLevelClass, this.introspectedTable);

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        if (this.introspectedTable.isConstructorBased()) {
            addParameterizedConstructor(topLevelClass);

            if (!this.introspectedTable.isImmutable()) {
                addDefaultConstructor(topLevelClass);
            }
        }
        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, this.warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }
            Field field = getJavaBeansField(introspectedColumn, this.context, this.introspectedTable);
            field.addAnnotation(String.format("@ApiModelProperty(value = \"%s\", required = true)", new Object[] { getSimpleType(introspectedColumn.getFullyQualifiedJavaType()), introspectedColumn.getRemarks() }));
            if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }
        }

        return topLevelClass;
    }

    private TopLevelClass genVo(String pack) {
        Plugin plugins = this.context.getPlugins();
        CommentGenerator commentGenerator = this.context.getCommentGenerator();

        String recordType = this.introspectedTable.getBaseRecordType();

        String pkg = recordType.substring(0, recordType.lastIndexOf("."));
        String entity = recordType.substring(recordType.lastIndexOf(".") + 1) + "VO";

        pkg = pkg.substring(0, pkg.lastIndexOf(".") + 1) + "vo";

        String dtoType = pkg + "." + entity;

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(dtoType);

        TopLevelClass topLevelClass = new TopLevelClass(type);


        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));
        topLevelClass.addField(createField());

        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
//        topLevelClass.addImportedType("lombok.Getter");
//        topLevelClass.addImportedType("lombok.Setter");


        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        FullyQualifiedJavaType superClass = getSuperClass();
        if (superClass != null);

        commentGenerator.addModelClassComment(topLevelClass, this.introspectedTable);

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        if (this.introspectedTable.isConstructorBased()) {
            addParameterizedConstructor(topLevelClass);

            if (!this.introspectedTable.isImmutable()) {
                addDefaultConstructor(topLevelClass);
            }
        }
        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, this.warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }
            Field field = getJavaBeansField(introspectedColumn, this.context, this.introspectedTable);
            field.addAnnotation(String.format("@ApiModelProperty(value = \"%s\", required = true)", new Object[] { getSimpleType(introspectedColumn.getFullyQualifiedJavaType()), introspectedColumn.getRemarks() }));
            if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }
        }

        return topLevelClass;
    }

    private String getSimpleType(FullyQualifiedJavaType javaType) {
        String shortName = javaType.getShortName();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Boolean", "boolean");
        map.put("Byte", "byte");
        map.put("Char", "char");
        map.put("Integer", "int");
        map.put("Float", "float");
        map.put("Long", "long");
        map.put("Double", "double");
        String result = (String)map.get(shortName);
        if (result == null) {
            return shortName.toLowerCase();
        }
        return result;
    }

    private FullyQualifiedJavaType getSuperClass() {
        FullyQualifiedJavaType superClass;
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {

            superClass = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
        } else {
            String rootClass = getRootClass();
            if (rootClass != null) {
                superClass = new FullyQualifiedJavaType(rootClass);
            } else {
                superClass = null;
            }
        }

        return superClass;
    }

    private boolean includePrimaryKeyColumns() {
        return (!this.introspectedTable.getRules().generatePrimaryKeyClass() && this.introspectedTable
                .hasPrimaryKeyColumns());
    }

    private boolean includeBLOBColumns() {
        return (!this.introspectedTable.getRules().generateRecordWithBLOBsClass() && this.introspectedTable
                .hasBLOBColumns());
    }

    private void addParameterizedConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);



        List<IntrospectedColumn> constructorColumns = includeBLOBColumns() ? this.introspectedTable.getAllColumns() : this.introspectedTable.getNonBLOBColumns();

        for (IntrospectedColumn introspectedColumn : constructorColumns) {
            method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn
                    .getJavaProperty()));
            topLevelClass.addImportedType(introspectedColumn.getFullyQualifiedJavaType());
        }

        StringBuilder sb = new StringBuilder();
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
            boolean comma = false;
            sb.append("super(");
            for (IntrospectedColumn introspectedColumn : this.introspectedTable
                    .getPrimaryKeyColumns()) {
                if (comma) {
                    sb.append(", ");
                } else {
                    comma = true;
                }
                sb.append(introspectedColumn.getJavaProperty());
            }
            sb.append(");");
            method.addBodyLine(sb.toString());
        }

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            sb.setLength(0);
            sb.append("this.");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" = ");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(';');
            method.addBodyLine(sb.toString());
        }

        topLevelClass.addMethod(method);
    }

    private List<IntrospectedColumn> getColumnsInThisClass() {
        List<IntrospectedColumn> introspectedColumns;
        if (includePrimaryKeyColumns()) {
            if (includeBLOBColumns()) {
                introspectedColumns = this.introspectedTable.getAllColumns();
            } else {
                introspectedColumns = this.introspectedTable.getNonBLOBColumns();
            }

        } else if (includeBLOBColumns()) {

            introspectedColumns = this.introspectedTable.getNonPrimaryKeyColumns();
        } else {
            introspectedColumns = this.introspectedTable.getBaseColumns();
        }


        return introspectedColumns;
    }

    public static Field getJavaBeansField(IntrospectedColumn introspectedColumn, Context context, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
        String property = introspectedColumn.getJavaProperty();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(fqjt);
        field.setName(property);
        return field;
    }
}
