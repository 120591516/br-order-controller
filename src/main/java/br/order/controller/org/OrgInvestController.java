package br.order.controller.org;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.OrganizationInvest;
import br.crm.service.org.OrgInvestService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: OrgInvestController
 * @Description: 体检机构投资人controller
 * @author admin
 * @date 2016年9月12日 下午4:41:31
 */
@Controller
@RequestMapping("/orgInvest")
public class OrgInvestController {
    @Autowired
    private OrgInvestService orgInvestService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    //表单提交中时间转换方法
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /** 
    * @Title: insertOrgInvest 
    * @Description: 新增体检机构投资人
    * @param organizationInvest
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增体检机构投资人", httpMethod = "POST", response = JSONObject.class, notes = "新增体检机构投资人")
    @RequestMapping(value = "/insertOrgInvest", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgInvest(@ApiParam(required = true, name = "organizationInvest", value = "organizationInvest,新增机构投资人对象") OrganizationInvest organizationInvest) {
        JSONObject message = new JSONObject();
        try {
            organizationInvest.setOrgInvestCreateTime(new Date());
            organizationInvest.setOrgInvestEditTime(organizationInvest.getOrgInvestCreateTime());
            int i = orgInvestService.insertOrgInvest(organizationInvest);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getOrgInvestByOrgId 
    * @Description: 根据机构id查询机构投资人
    * @param orgId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据机构id查询机构投资人", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id查询机构投资人")
    @RequestMapping("/getOrgInvestByOrgId")
    @ResponseBody
    public JSONObject getOrgInvestByOrgId(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationInvest> list = orgInvestService.getOrgInvestByOrgId(orgId);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgInvest");
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
    * @Title: getOrgInvestByInvestId 
    * @Description: 根据投资人id查询机构投资人
    * @param orgInvestId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据投资人id查询机构投资人", httpMethod = "GET", response = JSONObject.class, notes = "根据投资人id查询机构投资人")
    @RequestMapping("/getOrgInvestByInvestId")
    @ResponseBody
    public JSONObject getOrgInvestByInvestId(@ApiParam(required = true, name = "orgInvestId", value = "orgInvestId,机构投资人id") Long orgInvestId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationInvest organizationInvest = orgInvestService.getOrgInvestByInvestId(orgInvestId);
            message.put("data", organizationInvest);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateOrgInvest 
    * @Description: 修改机构投资人信息
    * @param organizationInvest
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改机构投资人信息", httpMethod = "POST", response = JSONObject.class, notes = "修改机构投资人信息")
    @RequestMapping("/updateOrgInvest")
    @ResponseBody
    public JSONObject updateOrgInvest(@ApiParam(required = true, name = "organizationInvest", value = "organizationInvest,改后机投资人对象") OrganizationInvest organizationInvest) {
        JSONObject message = new JSONObject();
        try {
            organizationInvest.setOrgInvestEditTime(new Date());
            int i = orgInvestService.updateOrgInvest(organizationInvest);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteOrgInvest 
    * @Description: 删除机构投资人
    * @param orgInvestId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除机构投资人", httpMethod = "GET", response = JSONObject.class, notes = "删除机构投资人")
    @RequestMapping("/deleteOrgInvest")
    @ResponseBody
    public JSONObject deleteOrgInvest(@ApiParam(required = true, name = "orgInvestId", value = "orgInvestId,机构投资人id") Long orgInvestId) {
        JSONObject message = new JSONObject();
        try {
            int i = orgInvestService.deleteOrgInvest(orgInvestId);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
