package com.yufei.search.utils;

import com.yufei.search.annotation.ParamsAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author wangzhenqing
 * @date 2024-01-05 10:57:20
 * @description
 */
@Slf4j
public class ParamsUtils {

    private static final Map<Class<?>, Function<String, ?>> CONVERSION_MAP = new HashMap<>();

    static {
        CONVERSION_MAP.put(int.class, Integer::parseInt);

        CONVERSION_MAP.put(boolean.class, Boolean::parseBoolean);

        CONVERSION_MAP.put(long.class, Long::parseLong);

        CONVERSION_MAP.put(float.class, Float::parseFloat);

        CONVERSION_MAP.put(double.class, Double::parseDouble);

        CONVERSION_MAP.put(short.class, Short::parseShort);

        CONVERSION_MAP.put(byte.class, Byte::parseByte);

        CONVERSION_MAP.put(char.class, s -> {
            if (s.length() == 1) {
                return s.charAt(0);
            }
            return '\u0000';
        });
    }

    /**
     * 这个方法会校验参数，带有ParamsAnnotation的会设置默认值。并且只针对基本数据类型
     *
     * @param dto
     * @param <T>
     */
    public static <T> void validateDto(T dto) {
        Class<?> currentClass = dto.getClass();
        // 循环遍历当前类及其所有父类
        while (currentClass != null) {
            // 获取当前类的所有字段
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                // 判断字段上是否有 ParamsAnnotation 注解
                ParamsAnnotation fieldAnnotation = field.getAnnotation(ParamsAnnotation.class);
                if (fieldAnnotation != null) {
                    // 获取注解配置的默认值
                    String defaultValue = fieldAnnotation.defaultValue();
                    // 使用反射设置字段值
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(dto);
                        // 根据需要进行校验或其他操作
                        if (isPrimitiveDefault(field.getType(), fieldValue)) {
                            // 校验失败，执行相应的逻辑
                            field.set(dto, convert(defaultValue, field.getType()));
                        } else if (field.getType().getName().equals("java.lang.String") &&
                                (null == fieldValue || StringUtils.isBlank((String) fieldValue))) {
                            Object paramValue = field.getType().getConstructor(String.class).newInstance(defaultValue);
                            field.set(dto, paramValue);
                        }
                    } catch (Exception e) {
                        // 处理异常
                        log.error("field set defaultValue error", e);
                    }
                }
            }

            // 获取当前类的父类
            currentClass = currentClass.getSuperclass();
        }
    }

    private static boolean isPrimitiveDefault(Class<?> type, Object value) {
        switch (type.getName()) {
            case "byte":
            case "java.lang.Byte":
                return ((Byte) value) == 0;
            case "short":
            case "java.lang.Short":
                return ((Short) value) == 0;
            case "int":
            case "java.lang.Integer":
                return ((Integer) value) == 0;
            case "long":
            case "java.lang.Long":
                return ((Long) value) == 0L;
            case "float":
            case "java.lang.Float":
                return ((Float) value) == 0.0f;
            case "double":
            case "java.lang.Double":
                return ((Double) value) == 0.0;
            case "char":
            case "java.lang.Character":
                return ((Character) value) == '\u0000';
            case "boolean":
            case "java.lang.Boolean":
                return !((Boolean) value);
            default:
                //不是基本数据类型，就不set defaultValue了
                return false;
        }
    }

    public static <T> T convert(String value, Class<T> targetType) {
        if (value == null) {
            throw new IllegalArgumentException("Input value cannot be null");
        }

        if (!CONVERSION_MAP.containsKey(targetType)) {
            throw new IllegalArgumentException("Unsupported primitive type: " + targetType.getName());
        }

        return (T) CONVERSION_MAP.get(targetType).apply(value);
    }

    /**
     * 根据class 生成instance
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getT(Class<T> tClass) {
        try {
            // 获取类型的构造函数
            Constructor<T> constructor = tClass.getDeclaredConstructor();

            // 如果构造函数是私有的，需要设置可访问性为 true
            constructor.setAccessible(true);

            // 使用构造函数创建对象
            T instance = constructor.newInstance();

            // 假设有一个方法或者其他逻辑用于将 ResultSet 中的数据映射到对象
            // mapResultSetDataToInstance(resultSet, instance);

            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // 处理异常，这里简化为抛出运行时异常
            log.error("Error creating instance", e);
        }
        return null;
    }
}
