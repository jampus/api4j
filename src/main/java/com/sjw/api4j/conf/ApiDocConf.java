package com.sjw.api4j.conf;

import com.google.common.collect.Maps;
import com.sjw.api4j.enums.ApiDocModeEnum;
import com.sjw.api4j.enums.ApiDocPrintTypeEnum;
import com.sjw.api4j.expection.ApiForjException;
import com.sjw.api4j.model.CustomMethodConfig;
import com.sjw.api4j.utils.StringPool;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author shijiawei
 * @version ApiDocConf.java -> v 1.0
 * @date 2019/7/28
 * 生成配置
 */
@Data
public class ApiDocConf {
    /**
     * 生成模式
     */
    private ApiDocModeEnum apiDocModeEnum = ApiDocModeEnum.CURRENT;
    /**
     * 指定root路径,如果不设置就是本项目的根路径
     */
    private String rootPath;
    /**
     * 公共前缀路径
     */
    private String commonPath;
    /**
     * 生成文档方式
     */
    private ApiDocPrintTypeEnum apiDocPrintTypeEnum;
    /**
     * 自定义生成模式指定的方法
     */
    private Map<String, CustomMethodConfig> customMethodConfigMap = Maps.newHashMap();

    public static ApiDocConf defaultConf() {
        return defaultConf(null);
    }

    public static ApiDocConf defaultConf(String commonPath) {
        ApiDocConf apiDocConf = new ApiDocConf();
        if (StringUtils.isNotBlank(commonPath)) {
            apiDocConf.setCommonPath(commonPath);
        }
        return apiDocConf;
    }

    public static ApiDocConf customConf(String rootPath, String commonPath) {
        if (StringUtils.isBlank(rootPath)) {
            throw ApiForjException.CUSTOM_SCAN_PATH_ERROR;
        }

        ApiDocConf apiDocConf = new ApiDocConf();
        if (StringUtils.isNotBlank(commonPath)) {
            apiDocConf.setCommonPath(commonPath);
        }
        apiDocConf.apiDocModeEnum = ApiDocModeEnum.CUSTOM;
        apiDocConf.rootPath = rootPath;
        return apiDocConf;
    }

    public void addMethod(String raw) {
        if (StringUtils.isBlank(raw)) {
            throw ApiForjException.METHOD_CUSTOM_ERROR;
        }
        if (!raw.contains(StringPool.DOT)) {
            throw ApiForjException.METHOD_CUSTOM_ERROR;
        }
        String[] spilt = raw.split(StringPool.REAL_DOT);
        if (2 != spilt.length) {
            throw ApiForjException.METHOD_CUSTOM_ERROR;
        }
        String className = spilt[0];
        String methodName = spilt[1];
        if (StringUtils.isBlank(className) || StringUtils.isBlank(methodName)) {
            throw ApiForjException.METHOD_CUSTOM_ERROR;
        }

        CustomMethodConfig customMethodConfig = customMethodConfigMap.get(className);
        if (null == customMethodConfig) {
            customMethodConfigMap.put(className, new CustomMethodConfig(className, methodName));
        } else {
            customMethodConfig.addMethod(methodName);
        }
    }

    /**
     * 直接指定整个类所有方法
     */
    public void addClass() {

    }

}
