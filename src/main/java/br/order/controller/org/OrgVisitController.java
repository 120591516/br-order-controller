package br.order.controller.org;

import java.text.SimpleDateFormat;
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

import br.crm.pojo.org.OrganizationVisit;
import br.crm.service.org.OrgVisitService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;
import br.order.vo.BrUserVo;

/**
 * @ClassName: OrgVisitController
 * @Description: 体检机构拜访信息controller
 * @author admin
 * @date 2016年9月12日 下午4:36:14
 */
@Controller
@RequestMapping("/orgVisit")
public class OrgVisitController {
    @Autowired
    private OrgVisitService orgVisitService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /** 
    * @Title: insertOrgVisit 
    * @Description: 新增体检机构拜访信息
    * @param organizationVisit
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增体检机构拜访信息", httpMethod = "POST", response = JSONObject.class, notes = "新增体检机构拜访信息")
    @RequestMapping(value = "/insertOrgVisit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgVisit(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId,
            @ApiParam(required = true, name = "orgVisitTime", value = "orgVisitTime,拜访时间") String orgVisitTime,
            @ApiParam(required = true, name = "orgVisitNextTime", value = "orgVisitNextTime,下次拜访时间") String orgVisitNextTime,
            @ApiParam(required = true, name = "orgVisitOpinion", value = "orgVisitOpinion,备注") String orgVisitOpinion) {
        JSONObject message = new JSONObject();
        try {
            OrganizationVisit organizationVisit = new OrganizationVisit();
            BrUserVo brUser = commonController.getUserBySession();
            organizationVisit.setOrgVisitTime(sdf.parse(orgVisitTime));
            organizationVisit.setOrgId(orgId);
            organizationVisit.setOrgVisitNextTime(sdf.parse(orgVisitNextTime));
            organizationVisit.setOrgVisitOpinion(orgVisitOpinion);
            organizationVisit.setOrgVisitCreateTime(new Date());
            organizationVisit.setOrgVisitPersonId(brUser.getUserId());
            organizationVisit.setOrgVisitPersonName(brUser.getUserName());
            organizationVisit.setOrgVisitEditTime(organizationVisit.getOrgVisitCreateTime());
            int i = orgVisitService.insertOrgVisit(organizationVisit);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getOrgVisitByOrgId 
    * @Description: 根据机构id查询机构拜访信息
    * @param orgId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据机构id查询机构拜访信息", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id查询机构拜访信息")
    @RequestMapping("/getOrgVisitByOrgId")
    @ResponseBody
    public JSONObject getOrgVisitByOrgId(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationVisit> list = orgVisitService.getOrgVisitByOrgId(orgId);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgVisit");
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
    * @Title: getOrgVisitByVisitId 
    * @Description: 根据拜访id查询机构拜访信息
    * @param orgVisitId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据拜访id查询机构拜访信息", httpMethod = "GET", response = JSONObject.class, notes = "根据拜访id查询机构拜访信息")
    @RequestMapping("/getOrgVisitByVisitId")
    @ResponseBody
    public JSONObject getOrgVisitByVisitId(@ApiParam(required = true, name = "orgVisitId", value = "orgVisitId,机构拜访id") Long orgVisitId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationVisit organizationVisit = orgVisitService.getOrgVisitByVisitId(orgVisitId);
            message.put("data", organizationVisit);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateOrgVisit 
    * @Description: 修改机构拜访信息
    * @param organizationVisit
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改机构拜访信息", httpMethod = "POST", response = JSONObject.class, notes = "修改机构拜访信息")
    @RequestMapping("/updateOrgVisit")
    @ResponseBody
    public JSONObject updateOrgVisit(@ApiParam(required = true, name = "orgVisitId", value = "orgVisitId,体检机构拜访信息id") Long orgVisitId,
            @ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId, @ApiParam(required = true, name = "orgVisitTime", value = "orgVisitTime,拜访时间") String orgVisitTime,
            @ApiParam(required = true, name = "orgVisitNextTime", value = "orgVisitNextTime,下次拜访时间") String orgVisitNextTime,
            @ApiParam(required = true, name = "orgVisitOpinion", value = "orgVisitOpinion,备注") String orgVisitOpinion) {
        JSONObject message = new JSONObject();
        try {
            OrganizationVisit organizationVisit = new OrganizationVisit();
            organizationVisit.setOrgVisitId(orgVisitId);
            organizationVisit.setOrgVisitTime(sdf.parse(orgVisitTime));
            organizationVisit.setOrgId(orgId);
            organizationVisit.setOrgVisitNextTime(sdf.parse(orgVisitNextTime));
            organizationVisit.setOrgVisitOpinion(orgVisitOpinion);
            organizationVisit.setOrgVisitEditTime(new Date());
            int i = orgVisitService.updateOrgVisit(organizationVisit);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteOrgVisit 
    * @Description: 删除机构拜访信息
    * @param orgVisitId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除机构拜访信息", httpMethod = "GET", response = JSONObject.class, notes = "删除机构拜访信息")
    @RequestMapping("/deleteOrgVisit")
    @ResponseBody
    public JSONObject deleteOrgVisit(@ApiParam(required = true, name = "orgVisitId", value = "orgVisitId,机构拜访信息id") Long orgVisitId) {
        JSONObject message = new JSONObject();
        try {
            int i = orgVisitService.deleteOrgVisit(orgVisitId);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
