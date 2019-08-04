package com.sjw.api4j.model;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author shijiawei
 * @version ApiTagClass.java -> v 1.0
 * @date 2019/7/28
 */
@Data
public class ApiTagClass {

    private String controllerName;

    private String path;

    private String note;

    private List<ApiTagMethod> methods = Lists.newArrayList();

    public ApiTagClass(String path, String note, String controllerName) {
        this.path = path;
        this.note = note;
        this.controllerName = controllerName;
    }

    public void addMethods(ApiTagMethod apiTagMethod) {
        methods.add(apiTagMethod);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
