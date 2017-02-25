package com.dark.controller.template;

import com.dark.model.TemplateConfig;
import com.dark.model.base.DtParam;
import com.dark.model.base.DtResponse;
import com.dark.service.base.JsonResult;
import com.dark.service.dbSource.CronSourceService;
import com.dark.service.template.TemplateConfigService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

/**
 * Created by jinwenzhang on 17/2/11.
 */
@Controller
@RequestMapping("templateConfig")
public class TemplateConfigController {
    private Logger logger = LoggerFactory.getLogger(TemplateConfigController.class);

    @Autowired
    private TemplateConfigService templateConfigService;

    @Autowired
    private CronSourceService cronSourceService;

    /**
     * 数据源页面
     *
     * @return
     */
    @RequestMapping(value = "tcList",method = RequestMethod.GET)
    public String tcList() {
        return "template/tcList";
    }

    /**
     * 数据源信息
     *
     * @return
     */
    @RequestMapping(value = "sourceData", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult dbSourceData(DtParam dtParam) {
        PageInfo<TemplateConfig> results = null;
        try {
            int rows = dtParam.getLength();
            int page = dtParam.getStart()/rows + 1;//页数
            results = templateConfigService.queryPageListByWhere(page, rows, new TemplateConfig());
        } catch (Exception e) {
            logger.error("======error======e:",e);
        }
        return new JsonResult(DtResponse.of(results, dtParam));
    }

    /**
     * 新增或者编辑
     *
     * @return
     */
    @RequestMapping(value = "openInfo",method = RequestMethod.POST)
    public String contentEdit(Integer tcId, Model view) {
        if(tcId!=null){
            TemplateConfig rs = templateConfigService.queryById(tcId);
            view.addAttribute("templateConfig", rs);
        }
        //后面会变更，只能查看个人用户
        view.addAttribute("csList", cronSourceService.queryAll());
        return "template/tcEdit";
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "saveEdit",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveEdit(TemplateConfig templateConfig, Boolean typeOptions) {
        try {
            if(typeOptions!=null && !typeOptions){//如果是静态模板
                templateConfig.setSqlSourceId(null);
                templateConfig.setSqlSentence(null);
            }
            if (templateConfig.getTcId()==null){
                templateConfig.setGmtCreated(new Date());
                templateConfigService.save(templateConfig);
            }else{
                templateConfigService.updateSelective(templateConfig);
            }
        } catch (Exception e) {
            logger.error("============"+e);
            return new JsonResult(false,"保存信息错误");
        }
        return new JsonResult();
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "delInfo",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delInfo(Integer tcId) {
        try {
            templateConfigService.deleteById(tcId);
        } catch (Exception e) {
            logger.error("============"+e);
            return new JsonResult(false,"删除信息错误");
        }
        return new JsonResult();
    }


}
