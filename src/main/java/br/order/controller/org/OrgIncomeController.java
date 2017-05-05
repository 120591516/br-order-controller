package br.order.controller.org;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.OrganizationConn;
import br.crm.pojo.org.OrganizationIncome;
import br.crm.service.org.OrgIncomeService;
import br.order.common.utils.InterfaceResultUtil;

/**
 * @ClassName: OrgIncomeController
 * @Description: 体检机构收入controller
 * @author admin
 * @date 2016年9月12日 下午4:41:11
 */
@Controller
@RequestMapping("/orgIncome")
public class OrgIncomeController {
	@Autowired
	private OrgIncomeService orgIncomeService;
	
	/** 
	* @Title: insertOrgIncome 
	* @Description: 新增体检机构收入
	* @param organizationIncome
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="新增体检机构收入",httpMethod="POST",response=JSONObject.class,notes="新增体检机构联系人")
	@RequestMapping(value="/insertOrgIncome",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject insertOrgIncome(@ApiParam(required=true,name="organizationConn",value="organizationConn,新增机构联系人对象")OrganizationIncome organizationIncome){
		JSONObject message = new JSONObject();
		try {
			organizationIncome.setOrgIncomeCreateTime(new Date());
			organizationIncome.setOrgIncomeEditTime(organizationIncome.getOrgIncomeCreateTime());
			int i = orgIncomeService.insertOrgIncome(organizationIncome);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	
	/** 
	* @Title: getOrgIncomeByOrgId 
	* @Description: 根据机构id查询收入
	* @param orgId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="根据机构id查询收入",httpMethod="GET",response=JSONObject.class,notes="根据机构id查询收入")
	@RequestMapping("/getOrgIncomeByOrgId")
	@ResponseBody
	public JSONObject getOrgIncomeByOrgId(@ApiParam(required=true,name="orgId",value="orgId,机构id")String orgId){
		JSONObject message = new JSONObject();
		try {
			List<OrganizationIncome> list = orgIncomeService.getOrgIncomeByOrgId(orgId);
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: getOrgIncomeByIncomeId 
	* @Description: 根据收入id查询收入
	* @param orgIncomeId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="根据收入id查询收入",httpMethod="GET",response=JSONObject.class,notes="根据机构id查询收入")
	@RequestMapping("/getOrgIncomeByIncomeId")
	@ResponseBody
	public JSONObject getOrgIncomeByIncomeId(@ApiParam(required=true,name="orgIncomeId",value="orgIncomeId,收入id")Long orgIncomeId){
		JSONObject message = new JSONObject();
		try {
			OrganizationIncome organizationIncome = orgIncomeService.getOrgIncomeByIncomeId(orgIncomeId);
			message.put("data", organizationIncome);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: updateOrgIncome 
	* @Description: 修改机构收入
	* @param organizationIncome
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="修改机构收入",httpMethod="POST",response=JSONObject.class,notes="修改机构收入")
	@RequestMapping("/updateOrgIncome")
	@ResponseBody
	public JSONObject updateOrgIncome(@ApiParam(required=true,name="organizationIncome",value="organizationIncome,改后机构收入")OrganizationIncome organizationIncome){
		JSONObject message = new JSONObject();
		try {
			organizationIncome.setOrgIncomeEditTime(new Date());
			int i = orgIncomeService.updateOrgIncome(organizationIncome);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: deleteOrgIncome 
	* @Description: 删除机构收入
	* @param orgIncomeId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="删除机构收入",httpMethod="GET",response=JSONObject.class,notes="删除机构收入")
	@RequestMapping("/deleteOrgIncome")
	@ResponseBody
	public JSONObject deleteOrgIncome(@ApiParam(required=true,name="orgIncomeId",value="orgIncomeId,收入id")Long orgIncomeId){
		JSONObject message = new JSONObject();
		try {
			int i = orgIncomeService.deleteOrgIncome(orgIncomeId);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
