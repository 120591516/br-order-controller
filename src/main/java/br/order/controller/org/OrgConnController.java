package br.order.controller.org;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.OrganizationConn;
import br.crm.service.org.OrgConnService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: OrgConnController
 * @Description: 体检机构联系人controller
 * @author admin
 * @date 2016年9月12日 下午4:40:18
 */
@Controller
@RequestMapping("/orgConn")
public class OrgConnController {
    @Autowired
    private OrgConnService orgConnService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /** 
    * @Title: insertOrganizationConn 
    * @Description: 新增体检机构联系人 
    * @param organizationConn
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增体检机构联系人", httpMethod = "POST", response = JSONObject.class, notes = "新增体检机构联系人")
    @RequestMapping(value = "/insertOrganizationConn", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrganizationConn(@ApiParam(required = true, name = "organizationConn", value = "organizationConn,新增机构联系人对象") OrganizationConn organizationConn) {
        JSONObject message = new JSONObject();
        try {
            organizationConn.setOrgConnStatus(0);
            ;
            organizationConn.setOrgConnCreateTime(new Date());
            organizationConn.setOrgConnEditTime(organizationConn.getOrgConnCreateTime());
            int i = orgConnService.insertOrganizationConn(organizationConn);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getOrgConnByOrgId 
    * @Description: 根据机构id查询机构联系人信息 
    * @param orgId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据机构id查询机构联系人信息", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id查询机构联系人信息")
    @RequestMapping("/getOrgConnByOrgId")
    @ResponseBody
    public JSONObject getOrgConnByOrgId(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationConn> list = orgConnService.getOrgConnByOrgId(orgId);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgConn");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getOrgConnByOrgConnId 
    * @Description: 根据联系人id查询当前机构联系人信息 
    * @param orgConnId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据联系人id查询当前机构联系人信息", httpMethod = "GET", response = JSONObject.class, notes = "根据联系人id查询当前机构联系人信息")
    @RequestMapping("/getOrgConnByOrgConnId")
    @ResponseBody
    public JSONObject getOrgConnByOrgConnId(@ApiParam(required = true, name = "orgConnId", value = "orgConnId,机构联系人id") Long orgConnId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationConn organizationConn = orgConnService.getOrgConnByOrgConnId(orgConnId);
            message.put("data", organizationConn);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateOrgConn 
    * @Description: 修改机构联系人信息 
    * @param organizationConn
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改机构联系人信息", httpMethod = "POST", response = JSONObject.class, notes = "修改机构联系人信息")
    @RequestMapping("/updateOrgConn")
    @ResponseBody
    public JSONObject updateOrgConn(@ApiParam(required = true, name = "organizationConn", value = "organizationConn,改后机构联系人对象") OrganizationConn organizationConn) {
        JSONObject message = new JSONObject();
        try {
            organizationConn.setOrgConnEditTime(new Date());
            int i = orgConnService.updateOrgConn(organizationConn);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteOrgConn 
    * @Description: 删除机构联系人
    * @param orgConnId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除机构联系人", httpMethod = "GET", response = JSONObject.class, notes = "删除机构联系人")
    @RequestMapping("/deleteOrgConn")
    @ResponseBody
    public JSONObject deleteOrgConn(@ApiParam(required = true, name = "orgConnId", value = "orgConnId,机构联系人id") Long orgConnId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationConn organizationConn = new OrganizationConn();
            organizationConn.setOrgConnId(orgConnId);
            organizationConn.setOrgConnStatus(1);
            organizationConn.setOrgConnEditTime(new Date());
            int i = orgConnService.updateOrgConn(organizationConn);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
