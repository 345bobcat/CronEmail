package com.dark.controller.template;

import com.dark.common.ObjectConvertUtil.ReflectBeanUtils;
import com.dark.model.EmailConfig;
import com.dark.model.base.DtParam;
import com.dark.model.base.DtResponse;
import com.dark.service.base.JsonResult;
import com.dark.service.template.EmailConfigService;
import com.dark.service.template.TemplateConfigService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jinwenzhang on 17/2/11.
 */
@Controller
@RequestMapping("emailConfig")
public class EmailConfigController {
    private Logger logger = LoggerFactory.getLogger(EmailConfigController.class);

    @Autowired
    private EmailConfigService emailConfigService;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Value("${email.sender}")
    private String sender;

    /**
     * 数据源页面
     *
     * @return
     */
    @RequestMapping(value = "ecList",method = RequestMethod.GET)
    public String ecList() {
        return "template/ecList";
    }

    /**
     * 数据源信息
     *
     * @return
     */
    @RequestMapping(value = "sourceData", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sourceData(DtParam dtParam) {
        PageInfo<EmailConfig> results = null;
        try {
            int rows = dtParam.getLength();
            int page = dtParam.getStart()/rows + 1;//页数
            results = emailConfigService.queryPageListByWhere(page, rows, new EmailConfig());
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
    public String openInfo(Integer ecId, Model view) {
        if(ecId!=null){
            EmailConfig rs = emailConfigService.queryById(ecId);
            view.addAttribute("emailConfig", rs);
        }
        view.addAttribute("sender", sender);
        //后面会变更，只能查看个人用户
        view.addAttribute("tcList", templateConfigService.queryAll());
        return "template/ecEdit";
    }

    /**
     * 根据id获取信息详情页面
     *
     * @return
     */
    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    public String getInfo(Model view, Integer ecId) {
        EmailConfig result = null;
        try {
            result = emailConfigService.queryById(ecId);
            view.addAttribute("emailConfig", ReflectBeanUtils.objectToMap(result));
        } catch (Exception e) {
            logger.error("======error======e:", e);
        }
        return "template/ecInfo";
    }

    /**
     * 根据id删除信息
     *
     * @return
     */
    @RequestMapping(value = "delById", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delById(Integer ecId) {
        try {
            emailConfigService.deleteById(ecId);
        } catch (Exception e) {
            logger.error("======error======e:", e);
            return new JsonResult(false,"");
        }
        return new JsonResult();
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "saveEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveEdit(EmailConfig ec) {
        try {
            if (ec.getEcId()==null){
                emailConfigService.save(ec);
            }else{
                emailConfigService.updateSelective(ec);
            }
        } catch (Exception e) {
            logger.error("======error======e:", e);
            return new JsonResult(false, "");
        }
        return new JsonResult();
    }
}
