package br.order.controller.org;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.OrganizationLevel;
import br.crm.service.org.OrgLevelService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: OrgLevelController
 * @Description: 体检机构等级controller
 * @author admin
 * @date 2016年9月12日 下午4:30:15
 */
@Controller
@RequestMapping("/orgLevel")
public class OrgLevelController {

    @Autowired
    private OrgLevelService orgLevelService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * @Title: insertOrgLevel
     * @Description: 新增体检机构等级
     * @param organizationLevel
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "新增体检机构等级", httpMethod = "POST", response = JSONObject.class, notes = "新增体检机构等级")
    @RequestMapping(value = "/insertOrgLevel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgLevel(@ApiParam(required = true, name = "organizationLevel", value = "organizationLevel,新增机构等级对象") OrganizationLevel organizationLevel) {
        JSONObject message = new JSONObject();
        try {
            organizationLevel.setOrgLevelCreatetime(new Date());
            organizationLevel.setOrgLevelEdittime(new Date());
            organizationLevel.setOrgLevelStatus(0);
            int i = orgLevelService.insertOrgLevel(organizationLevel);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: getAllOrgLevel
     * @Description: 查看所有机构等级信息
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "查看所有等级信息", httpMethod = "GET", response = JSONObject.class, notes = "查看所有机构等级信息")
    @RequestMapping("/getAllOrgLevel")
    @ResponseBody
    public JSONObject getAllOrgLevel() {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationLevel> list = orgLevelService.getAllOrgLevel();
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgLevel");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据id查询等级信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询等级信息")
    @RequestMapping("/geOrgLevelById")
    @ResponseBody
    public JSONObject geOrgLevelById(String orgLevelId) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(orgLevelId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            OrganizationLevel organizationLevel = orgLevelService.geOrgLevelById(orgLevelId);
            message.put("data", organizationLevel);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: deleteOrgLevel
     * @Description: 删除机构等级
     * @param orgLevelId
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "删除机构等级", httpMethod = "GET", response = JSONObject.class, notes = "删除机构等级")
    @RequestMapping("/deleteOrgLevel")
    @ResponseBody
    public JSONObject deleteOrgLevel(@ApiParam(required = true, name = "orgLevelId", value = "orgLevelId,机构等级id") Integer orgLevelId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationLevel organizationLevel = orgLevelService.geOrgLevelById(orgLevelId.toString());
            organizationLevel.setOrgLevelStatus(1);
            int i = orgLevelService.updateOrgLevel(organizationLevel);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改机构等级", httpMethod = "POST", response = JSONObject.class, notes = "修改机构等级")
    @RequestMapping("/updateOrgLevel")
    @ResponseBody
    public JSONObject updateOrgLevel(OrganizationLevel organizationLevel) {
        JSONObject message = new JSONObject();
        try {
            int i = orgLevelService.updateOrgLevel(organizationLevel);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
