package com.sjw.api4j;

import com.sjw.api4j.conf.ApiDocConf;
import com.sjw.api4j.enums.ApiDocPrintTypeEnum;
import com.sjw.api4j.helper.ApiDocHelper;
import com.sjw.api4j.model.ApiMethodInfo;
import com.sjw.api4j.model.ApiTagClass;
import com.sjw.api4j.utils.SysLogUtil;
import com.sjw.api4j.utils.print.ConsoleUtil;
import com.sjw.api4j.utils.print.MdDocUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author shijiawei
 * @version ApiDocUtil.java -> v 1.0
 * @date 2019/7/27
 * 主入口程序
 * 1:可以再控制台输出
 * 2:也可以输出到文件
 */
public class ApiDocUtil {

    public static void main(String[] args) {
//
        ApiDocConf apiDocConf = new ApiDocConf();
        apiDocConf.mdSet().setCommonPath("service-test");
        makeApiDoc(apiDocConf);
//        String customPath = "/Users/shijiawei/javaProject/beibei/product-service/product-service-api";
//        ApiDocConf conf = ApiDocConf.customConf(customPath,"serivce");
//        conf.addMethod("ProductStockService.getItemStocksInfo");
//        makeApiDoc(conf);
    }

    public static void makeApiDoc(ApiDocConf apiDocConf) {
        if (null == apiDocConf) {
            apiDocConf = new ApiDocConf();
        }
        doMakeApiDoc(apiDocConf);
    }

    public static void makeApiDoc() {
        doMakeApiDoc(new ApiDocConf());
    }

    private static void doMakeApiDoc(ApiDocConf apiDocConf) {
        SysLogUtil.sysStart();
        long start = System.currentTimeMillis();
        //获取方法集
        List<ApiTagClass> apiTagClassList = ApiDocHelper.getAllMethods(apiDocConf.getCommonPath(), apiDocConf.getApiDocModeEnum(),
                apiDocConf.getCustomMethodConfigMap(), apiDocConf.getRootPath());
        if (CollectionUtils.isEmpty(apiTagClassList)) {
            SysLogUtil.info("没有找到注解标记的需要生成文档的方法");
            return;
        }
        //获取输出模式
        ApiDocPrintTypeEnum printType = apiDocConf.getApiDocPrintTypeEnum();
        int classIndex = 1;
        //将包装method build成参数集
        for (ApiTagClass apiTagClass : apiTagClassList) {
            List<ApiMethodInfo> apiMethodInfos = ApiDocHelper.analyMethods(apiTagClass);
            switch (printType) {
                case CONSOLE:
                    ConsoleUtil.print(apiTagClass, apiMethodInfos);
                    break;
                case MD:
                    MdDocUtil.print(apiMethodInfos, classIndex, apiDocConf.getDocOutputPath());
                    break;
                default:
                    ConsoleUtil.print(apiTagClass, apiMethodInfos);
            }
            classIndex++;
        }
        SysLogUtil.sysEnd(start);
    }

}
