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

import br.crm.pojo.org.OrganizationSoft;
import br.crm.service.org.OrgSoftService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: OrgSoftController
 * @Description: 体检机构软件controller
 * @author admin
 * @date 2016年9月12日 下午4:34:12
 */
@Controller
@RequestMapping("/orgSoft")
public class OrgSoftController {
    @Autowired
    private OrgSoftService orgSoftService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /** 
    * @Title: insertOrgSoft 
    * @Description: 新增体检机构软件
    * @param organizationSoft
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增体检机构软件", httpMethod = "POST", response = JSONObject.class, notes = "新增体检机构软件")
    @RequestMapping(value = "/insertOrgSoft", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgSoft(@ApiParam(required = true, name = "organizationSoft", value = "organizationSoft,新增机构软件") OrganizationSoft organizationSoft) {
        JSONObject message = new JSONObject();
        try {
            organizationSoft.setOrgSoftstatus(0);
            organizationSoft.setOrgSoftCreateTime(new Date());
            organizationSoft.setOrgSoftEditTime(organizationSoft.getOrgSoftCreateTime());
            int i = orgSoftService.insertOrgSoft(organizationSoft);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getOrgSoftByOrgId 
    * @Description: 根据机构id查询机构软件
    * @param orgId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据机构id查询机构软件", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id查询机构软件")
    @RequestMapping("/getOrgSoftByOrgId")
    @ResponseBody
    public JSONObject getOrgSoftByOrgId(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationSoft> list = orgSoftService.getOrgSoftByOrgId(orgId);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgSoft");
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
    * @Title: getOrgSoftBySoftId 
    * @Description: 根据软件id查询机构软件
    * @param orgSoftId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据软件id查询机构软件", httpMethod = "GET", response = JSONObject.class, notes = "根据软件id查询机构软件")
    @RequestMapping("/getOrgSoftBySoftId")
    @ResponseBody
    public JSONObject getOrgSoftBySoftId(@ApiParam(required = true, name = "orgSoftId", value = "orgSoftId,机构软件id") Long orgSoftId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationSoft organizationSoft = orgSoftService.getOrgSoftBySoftId(orgSoftId);
            message.put("data", organizationSoft);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateOrgSoft 
    * @Description: 修改机构软件
    * @param organizationSoft
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改机构软件", httpMethod = "POST", response = JSONObject.class, notes = "修改机构软件")
    @RequestMapping("/updateOrgSoft")
    @ResponseBody
    public JSONObject updateOrgSoft(@ApiParam(required = true, name = "organizationConn", value = "organizationConn,改后机构联系人对象") OrganizationSoft organizationSoft) {
        JSONObject message = new JSONObject();
        try {
            organizationSoft.setOrgSoftEditTime(new Date());
            int i = orgSoftService.updateOrgSoft(organizationSoft);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteOrgSoft 
    * @Description: 删除机构软件
    * @param orgSoftId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除机构软件", httpMethod = "GET", response = JSONObject.class, notes = "删除机构软件")
    @RequestMapping("/deleteOrgSoft")
    @ResponseBody
    public JSONObject deleteOrgSoft(@ApiParam(required = true, name = "orgSoftId", value = "orgSoftId,机构软件id") Long orgSoftId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationSoft organizationSoft = new OrganizationSoft();
            organizationSoft.setOrgSoftstatus(1);
            organizationSoft.setOrgSoftId(orgSoftId);
            organizationSoft.setOrgSoftEditTime(new Date());
            int i = orgSoftService.updateOrgSoft(organizationSoft);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
