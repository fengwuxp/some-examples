package com.oak.codegen.controller;

import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.dao.annotation.Like;
import com.levin.commons.service.domain.Desc;
import com.levin.commons.service.domain.InjectDomain;
import com.levin.commons.service.domain.Secured;
import com.wuxp.basic.enums.DescriptiveEnum;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.*;

public final class ServiceModelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServiceModelUtil.class);
    private static Set<Class> baseTypes = new HashSet<>();
    private static Set<Class> collectionsTypes = new HashSet<>();
    private static Set<String> notUpdateNames = new HashSet<>();

    static {
        baseTypes.add(Integer.class);
        baseTypes.add(Long.class);
        baseTypes.add(Boolean.class);
        baseTypes.add(Short.class);
        baseTypes.add(Byte.class);
        baseTypes.add(String.class);
        baseTypes.add(Double.class);
        baseTypes.add(Float.class);
        baseTypes.add(Date.class);

        collectionsTypes.add(List.class);
        collectionsTypes.add(Set.class);
        collectionsTypes.add(Map.class);

        notUpdateNames.add("addTime");
        notUpdateNames.add("updateTime");
        notUpdateNames.add("sn");
    }

    /**
     * ?????????????????????
     *
     * @param entityClass ?????????
     * @param target      ????????????
     */
    public static void entity2ServiceModel(Class entityClass, Map<String, Class> entityMapping, String basePackageName, String target) throws Exception {
        basePackageName = basePackageName.toLowerCase();
        List<FieldModel> fields = buildFieldModel(entityClass, entityMapping, true);

        String desc = entityClass.isAnnotationPresent(Schema.class)
                ? ((Schema) entityClass.getAnnotation(Schema.class)).description()
                : entityClass.getSimpleName();

        String serviceTarget = target + File.separator + "services";
        String serviceTestTarget = target + File.separator + "test";
        String controllerTarget = target + File.separator + "controller";

        buildInfo(entityClass, desc, basePackageName, fields, serviceTarget);
        buildEvt(entityClass, desc, basePackageName, fields, serviceTarget);
        buildService(entityClass, desc, basePackageName, fields, serviceTarget);
        buildServiceTest(entityClass, desc, basePackageName, fields, serviceTestTarget);
        buildController(entityClass, desc, basePackageName, fields, controllerTarget);

    }


    private static void buildInfo(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {

        String className = MessageFormat.format("{0}Info", entityClass.getSimpleName());
        String packageName = MessageFormat.format("{0}.info", basePackageName);

        FieldModel pkField = getPkField(entityClass, fields);


        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);
        params.put("className", className);
        params.put("packageName", packageName);
        params.put("entityName", entityClass.getSimpleName());
        //params.put("serialVersionUID", serialVersionUID);
        params.put("fields", fields);
        params.put("pkField", pkField);

        String dir = MessageFormat.format("{0}/{1}/", target, packageName.replace(".", "/")).toLowerCase();
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }
        String fileName = dir + className + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("bean.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

    }

    private static FieldModel getPkField(Class entityClass, List<FieldModel> fields) {

        for (FieldModel field : fields) {
            if (field.getPk()) {
                return field;
            }
        }

        return null;
    }

    private static void buildEvt(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {

        String packageName = basePackageName + ".req";

        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        //params.put("serialVersionUID", serialVersionUID);
        params.put("entityName", entityClass.getSimpleName());

        params.put("pkField", pkField);


        String dir = target + "/" + (packageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //??????
        params.put("fields", filter(fields, "id", "createTime", "updateTime"));
        String className = "Create" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        String fileName = dir + className + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("create_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

//        //??????
//        params.put("fields", fields);
//
//        className = "Find" + entityClass.getSimpleName() + "Req";
//        params.put("className", className);
//        fileName = dir + className + ".java";
//        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
//        getTemplate("find_evt.ftl").process(params, hWriter);
//        System.out.println("--------------------" + fileName);

        //??????
        params.put("fields", filter(fields, "createTime", "updateTime"));
        className = "Edit" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("edit_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        //??????
        className = "Delete" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("del_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        //??????
        params.put("fields", fields);
        className = "Query" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("query_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

    }


    private static List<FieldModel> filter(List<FieldModel> old, String... ignoreNames) {


        ArrayList<FieldModel> fieldModels = new ArrayList<>(old.size());


        for (FieldModel fieldModel : old) {

            boolean isFiltered = false;

            for (String fieldName : ignoreNames) {
                if (fieldModel.name.equals(fieldName)) {
                    isFiltered = true;
                    break;
                }
            }

            if (!isFiltered) {
                fieldModels.add(fieldModel);
            }

        }

        return fieldModels;

    }


    private static void buildService(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {
        String packageName = basePackageName;

        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        //params.put("serialVersionUID", serialVersionUID);
        params.put("entityName", entityClass.getSimpleName());
        params.put("entityClassName", entityClass.getPackage().getName() + "." + entityClass.getSimpleName());
        params.put("fields", fields);
        params.put("pkField", pkField);

        String dir = target + "/" + (packageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //??????????????????
        String serviceName = entityClass.getSimpleName() + "Service";
        //packageName.substring(packageName.lastIndexOf(".") + 1);
//        serviceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        params.put("className", serviceName);
        String fileName = dir + serviceName + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("service.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        params.put("serviceName", serviceName);
        //??????????????????
        serviceName = serviceName + "Impl";
        params.put("className", serviceName);
        fileName = dir + serviceName + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("service_impl.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);
    }

    private static void buildServiceTest(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {
        String packageName = basePackageName;

        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        //params.put("serialVersionUID", serialVersionUID);
        params.put("entityName", entityClass.getSimpleName());
        params.put("entityClassName", entityClass.getPackage().getName() + "." + entityClass.getSimpleName());
        params.put("fields", filter(fields, "createTime"));
        params.put("pkField", pkField);

        String dir = target + "/" + (packageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //?????????????????????
       /* String serviceName = entityClass.getSimpleName() + "Service";//packageName.substring(packageName.lastIndexOf(".") + 1);
//        serviceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        params.put("className", serviceName);
        String fileName = dir + serviceName + "Test.java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("service_test.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);*/

    }

    private static void buildController(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {

        String packageName = basePackageName;

        String controllerPackageName = basePackageName.replace(".services", ".admin.controller");

        controllerPackageName = controllerPackageName.replace("provide.", "");


        //  String serviceName = packageName.substring(packageName.lastIndexOf(".") + 1);
        //  serviceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        String serviceName = entityClass.getSimpleName() + "Service";
        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        params.put("serviceName", serviceName);
        params.put("serviceFullName", packageName + "." + serviceName);
        params.put("controllerPackageName", controllerPackageName);
        params.put("entityName", entityClass.getSimpleName());
        params.put("entityClassName", entityClass.getPackage().getName() + "." + entityClass.getSimpleName());
        params.put("fields", fields);
        params.put("pkField", pkField);

        String dir = target + "/" + (controllerPackageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //???????????????
        String className = entityClass.getSimpleName() + "Controller";
        params.put("className", className);
        String fileName = dir + className + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("controller.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);
    }

    private static List<FieldModel> buildFieldModel(Class clzss, Map<String, Class> entityMapping, boolean excess/*??????????????????????????????????????????????????????Desc???????????????*/) throws Exception {

        Object obj = clzss.newInstance();

        List<FieldModel> list = new ArrayList<>();

        final List<Field> declaredFields = new LinkedList<>();


        ResolvableType resolvableTypeForClass = ResolvableType.forClass(clzss);


        //  System.out.println("found " + clzss + " : " + field);
        ReflectionUtils.doWithFields(clzss, declaredFields::add);


        // Field.setAccessible(declaredFields, true);

        for (Field field : declaredFields) {

            field.setAccessible(true);


            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            Class<?> fieldType = ResolvableType.forField(field, resolvableTypeForClass).resolve(field.getType());


            if (field.getType() != fieldType) {
                System.out.println("*** " + clzss + " ?????????????????? : " + field + " --> " + fieldType);
            }


            if (collectionsTypes.contains(fieldType)) {
                //????????????????????????
                continue;
            }


            FieldModel fieldModel = new FieldModel();
            fieldModel.setName(field.getName());
            fieldModel.setLength(field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).length() : -1);

            fieldModel.setType(fieldType.getSimpleName());

            fieldModel.setClassType(fieldType);

            fieldModel.setBaseType(baseTypes.contains(fieldType));

            fieldModel.setEnums(fieldType.isEnum());
            fieldModel.setCollections(collectionsTypes.contains(fieldType));

            fieldModel.setComplex(!fieldType.isPrimitive()
                    && !fieldModel.getBaseType()
                    && !fieldModel.getEnums()
                    && !fieldModel.getCollections());

            if (fieldModel.getComplex()) {
                //???????????? com.oaknt.udf.entities - com.oaknt.udf.servicess.sample.info;

                String typePackageName = fieldType.getPackage().getName();

                typePackageName = typePackageName.replace("entities", "services") + "."
                        + fieldType.getSimpleName().toLowerCase() + ".info";

                fieldModel.setComplexClassPackageName(typePackageName);

                //  fieldModel.getImports().add(typePackageName);


                //  fieldModel.infoClassName =  typePackageName + "." + fieldType.getSimpleName() + "Info";

            }

            boolean hasSchema = field.isAnnotationPresent(Schema.class);
            Schema schema = field.getAnnotation(Schema.class);
            fieldModel.setDesc(hasSchema ? schema.description() : field.getName());
            fieldModel.setDescDetail(hasSchema ? schema.description() : "");
            if (!hasSchema) {
                boolean isDesc = field.isAnnotationPresent(Desc.class);
                Desc desc = field.getAnnotation(Desc.class);
                fieldModel.setDesc(isDesc ? desc.value() : field.getName());
                fieldModel.setDescDetail(isDesc ? desc.detail() : "");
            }

            fieldModel.setPk(field.isAnnotationPresent(Id.class));
            fieldModel.setLike(field.isAnnotationPresent(Like.class));
            fieldModel.setNotUpdate(fieldModel.getPk() || notUpdateNames.contains(fieldModel.getName()) || fieldModel.getComplex());
            if (fieldModel.getPk()) {
                fieldModel.setRequired(true);
                fieldModel.setIdentity(field.isAnnotationPresent(GeneratedValue.class)
                        && !field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.AUTO));
            } else {
                fieldModel.setUk(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).unique());
                fieldModel.setRequired(field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable());
            }
            if (field.isAnnotationPresent(ManyToOne.class) ||
                    field.isAnnotationPresent(OneToOne.class)) {
                fieldModel.setComplex(true);
                if (field.isAnnotationPresent(ManyToOne.class)) {
                    fieldModel.setLazy(field.getAnnotation(ManyToOne.class).fetch().equals(FetchType.LAZY));
                } else if (field.isAnnotationPresent(OneToOne.class)) {
                    fieldModel.setLazy(field.getAnnotation(OneToOne.class).fetch().equals(FetchType.LAZY));
                }
                Class aClass = entityMapping.get(field.getName());
                if (aClass != null) {
                    fieldModel.setInfoClassName(aClass.getPackage().getName() + "." + aClass.getSimpleName());
                }
                fieldModel.setTestValue("null");
            }

            //????????????
            ArrayList<String> annotations = new ArrayList<>();

            if (fieldModel.getRequired()) {
                annotations.add("@NotNull");
            }

            //
            if (field.isAnnotationPresent(InjectDomain.class)) {
                annotations.add("@" + InjectDomain.class.getSimpleName() + "");
                fieldModel.getImports().add(InjectDomain.class.getName());
            }

            if (field.isAnnotationPresent(Secured.class)) {
                annotations.add("@" + Secured.class.getSimpleName());
                fieldModel.getImports().add(Secured.class.getName());
            }


            if (fieldModel.getClassType().equals(String.class)
                    && fieldModel.getLength() != -1
                    && !fieldModel.getName().endsWith("Body")) {
                boolean isLob = field.isAnnotationPresent(Lob.class);
                if (isLob) {
                    fieldModel.setLength(4000);
                    fieldModel.setTestValue("\"?????????????????????\"");
                }
                if (fieldModel.getLength() != 255) {
                    annotations.add("@Size(max = " + fieldModel.getLength() + ")");
                    fieldModel.setTestValue("\"????????????" + fieldModel.getLength() + "\"");
                }
            }
            //????????????
            if (fieldModel.getName().endsWith("Pct")) {
                annotations.add("@Min(0)");
                annotations.add("@Max(100)");
                fieldModel.setTestValue("50");
            } else if (fieldModel.getName().endsWith("Ppt")) {
                annotations.add("@Min(0)");
                annotations.add("@Max(1000)");
                fieldModel.setTestValue("500");
            } else if (field.isAnnotationPresent(Pattern.class)) {
                String regexp = field.getAnnotation(Pattern.class).regexp();
                if (!StringUtils.isEmpty(regexp)) {
                    regexp = regexp.replace("\\", "\\\\");
                    annotations.add("@Pattern(regexp = \"" + regexp + "\")");
                }
            } else if (field.isAnnotationPresent(Size.class)) {
                annotations.add("@Size(min = " + field.getAnnotation(Size.class).min() + " , max = " + field.getAnnotation(Size.class).max() + ")");
            } else if (field.isAnnotationPresent(Min.class)) {
                annotations.add("@Min(" + field.getAnnotation(Min.class).value() + ")");
                fieldModel.setTestValue(field.getAnnotation(Min.class).value() + "");
            } else if (field.isAnnotationPresent(Max.class)) {
                annotations.add("@Max(" + field.getAnnotation(Max.class).value() + ")");
                fieldModel.setTestValue(field.getAnnotation(Max.class).value() + "");
            }

            fieldModel.setAnnotations(annotations);

            if (excess) {
                buildExcess(fieldModel);
            }

            String fieldValue = getFieldValue(field.getName(), obj);
            if (fieldValue != null) {
                fieldModel.setHasDefValue(true);
                fieldModel.setTestValue(fieldValue);
            }

            if (fieldModel.getTestValue() == null) {
                if (fieldModel.getName().equals("sn")) {
                    String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
                    fieldModel.setTestValue("\"" + sn + "\"");
                } else if (fieldModel.getName().equals("areaId")) {
                    fieldModel.setTestValue("\"1\"");
                } else if (fieldModel.enums) {
                    fieldModel.setTestValue(fieldType.getSimpleName() + "." + getEnumByVal(fieldType, 0).name());
                } else if (fieldModel.getClassType().equals(Boolean.class)) {
                    fieldModel.setTestValue("true");
                } else if (fieldModel.getClassType().equals(String.class)) {
                    fieldModel.setTestValue("\"" + fieldModel.getDesc() + "_1\"");
                } else if (fieldModel.getClassType().equals(Integer.class) || fieldModel.getClassType().equals(Long.class)) {
                    fieldModel.setTestValue(fieldModel.getName().endsWith("Id")
                            ? "null" : ("1" + (fieldModel.getClassType().equals(Long.class) ? "L" : "")));
                } else if (fieldModel.getClassType().equals(Double.class)) {
                    fieldModel.setTestValue("0.1d");
                } else if (fieldModel.getClassType().equals(Float.class)) {
                    fieldModel.setTestValue("0.1f");
                } else if (fieldModel.getClassType().equals(Date.class)) {
                    fieldModel.setTestValue("new Date()");
                }
            }

            list.add(fieldModel);
        }
        return list;
    }


    public static String getFieldValue(String fieldName, Object obj) {
        if (fieldName == null || obj == null) {
            return null;
        }
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        assert field != null;
        Object value = ReflectionUtils.getField(field, obj);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    private static void buildExcess(FieldModel fieldModel) {

        String name = fieldModel.getName();
        Class type = fieldModel.getClassType();

        if (fieldModel.getEnums()
                && DescriptiveEnum.class.isAssignableFrom(type)) {
            //????????????
            fieldModel.setExcessSuffix("Desc");
            fieldModel.setExcessReturnType("String");
            fieldModel.setExcessReturn("return " + name + " != null ? " + name + ".getDesc() : \"\";");
        } else if ((type.equals(Integer.class) || type.equals(Long.class))
                && name.endsWith("Fen")) {
            //?????????
            fieldModel.setExcessSuffix("2Yuan");
            fieldModel.setExcessReturnType("Double");
            fieldModel.setExcessReturn("return " + name + " != null ? new java.math.BigDecimal(" + name + ")\n" +
                    "                .divide(new java.math.BigDecimal(100), 2, java.math.BigDecimal.ROUND_HALF_UP)\n" +
                    "                .doubleValue() : null;");
        } else if ((type.equals(Integer.class) || type.equals(Long.class))
                && name.endsWith("Ppt")) {
            //?????????????????????
            fieldModel.setExcessSuffix("2Pct");
            fieldModel.setExcessReturnType("Double");
            fieldModel.setExcessReturn("return " + name + " != null ? new java.math.BigDecimal(" + name + ")\n" +
                    "                .divide(new java.math.BigDecimal(10), 1, java.math.BigDecimal.ROUND_HALF_UP)\n" +
                    "                .doubleValue() : null;");
        } else if (fieldModel.getComplex()) {
            String returnName = type.getSimpleName().substring(0, 1).toUpperCase() + type.getSimpleName().substring(1)
                    + "Info";
            String complexName = name.substring(0, 1).toUpperCase() + name.substring(1)
                    + "Info";

            fieldModel.setExcessSuffix("Info");
            fieldModel.setExcessReturnType(returnName);
            fieldModel.setExcessReturn("return " + name + " != null ? " + name + ".get" + complexName + "() : null;");
        }

    }

    private static Template getTemplate(String templatePath) throws Exception {
        //?????????????????????Configuration??????
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        DefaultObjectWrapper objectWrapper = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28).build();
        configuration.setObjectWrapper(objectWrapper);
        //??????????????????????????????????????????????????? ?????????
        configuration.setDefaultEncoding("UTF-8");
        //?????????jar???????????????
        configuration.setClassForTemplateLoading(ServiceModelUtil.class, "/");
        //?????????????????????
        return configuration.getTemplate(MessageFormat.format("/template/{0}", templatePath));
    }

    private static Enum getEnumByVal(Class ec, int i) {
        Iterator iter = EnumSet.allOf(ec).iterator();

        Enum e;
        do {
            if (!iter.hasNext()) {
                return null;
            }
            e = (Enum) iter.next();
        } while (e.ordinal() != i);

        return e;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = "name")
    @ToString()
    @Accessors(chain = true)
    public static class FieldModel {

        private String name;

        String prefix;

        private String type;

        private Integer length = -1;

        private Class classType;

        private String desc;

        private String descDetail;

        private Set<String> imports = new LinkedHashSet<>();

        private List<String> annotations = new ArrayList<>();

        private Boolean pk = false;//??????????????????

        private Boolean uk = false;//???????????????

        private Boolean baseType = true;//??????????????????

        private Boolean enums = false;//??????enum

        private Boolean complex = false;//??????????????????

        private String complexClassPackageName;//??????????????????

        private Boolean collections = false;//????????????

        private Boolean required = false;//????????????

        private Boolean identity; //????????????????????????

        private Boolean notUpdate = false;//?????????????????????

        private Boolean hasDefValue = false;//??????????????????

        private Boolean lazy = false;//??????lazy

        private String excessSuffix;//???????????????????????????

        private String excessReturnType;//???????????????????????????

        private String excessReturn;//?????????????????????

        private String infoClassName;

        private String testValue;

        private Boolean like;//????????????????????????


    }

}
