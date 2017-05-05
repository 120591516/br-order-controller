package br.order.controller.system;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;

import br.crm.common.utils.Base64;
import br.crm.common.utils.InterfaceResultUtil;
import br.crm.service.system.SystemConfigService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

@Controller
@RequestMapping("/orgSystem")
public class OrgSystemController {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    @ApiOperation(value = "查询系统设置", httpMethod = "GET", response = JSONObject.class, notes = "查询系统设置")
    @RequestMapping("/getSystem")
    @ResponseBody
    public JSONObject getSystem() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgSystem");
            jsonObject.put("operationList", operationList);
            Map<String, String> map = systemConfigService.getSystem();
            jsonObject.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    @ApiOperation(value = "修改缓冲系统设置", httpMethod = "GET", response = JSONObject.class, notes = "修改缓冲系统设置")
    @RequestMapping("/editRedisSystem")
    @ResponseBody
    public JSONObject editRedisSystem(String configRedis) {
        JSONObject jsonObject = new JSONObject();
        try {
            String newConfigRedis = null;
            if (StringUtils.isNotEmpty(configRedis)) {
                newConfigRedis = Base64.encode(configRedis.getBytes());
            }
            int flag = systemConfigService.editRedisSystem(newConfigRedis);
            jsonObject.put("data", flag);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    @ApiOperation(value = "修改搜索系统设置", httpMethod = "GET", response = JSONObject.class, notes = "修改搜索系统设置")
    @RequestMapping("/editSearchSystem")
    @ResponseBody
    public JSONObject editSearchSystem(String configSearch) {
        JSONObject jsonObject = new JSONObject();
        try {
            String newConfigSearch = null;
            if (StringUtils.isNotEmpty(configSearch)) {
                newConfigSearch = Base64.encode(configSearch.getBytes());
            }
            int flag = systemConfigService.editSearchSystem(newConfigSearch);
            jsonObject.put("data", flag);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }
}
