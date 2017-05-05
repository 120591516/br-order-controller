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

import br.crm.pojo.org.OrganizationCooperation;
import br.crm.service.org.OrgCooperationService;
import br.order.common.utils.InterfaceResultUtil;

/**
 * @ClassName: OrgCooperationController
 * @Description: 体检机构合作意向controller
 * @author admin
 * @date 2016年9月12日 下午4:40:42
 */
@Controller
@RequestMapping("/orgCooperation")
public class OrgCooperationController {
	@Autowired
	private OrgCooperationService orgCooperationService;
	
	/** 
	* @Title: insertOrgCooperation 
	* @Description: 新增合作意向 
	* @param orgCooperation
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="新增合作意向",httpMethod="POST",response=JSONObject.class,notes="新增合作意向")
	@RequestMapping(value="/insertOrgCooperation",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject insertOrgCooperation(@ApiParam(required=true,name="orgCooperation",value="orgCooperation,新增合作意向对象")OrganizationCooperation orgCooperation){
		JSONObject message = new JSONObject();
		try {
			orgCooperation.setOrgCooperationCreateTime(new Date());
			orgCooperation.setOrgCooperationEditTime(orgCooperation.getOrgCooperationCreateTime());;
			int i = orgCooperationService.insertOrgCooperation(orgCooperation);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: getOrgCooperationByOrgId 
	* @Description: 根据机构id查询合作意向信息
	* @param orgId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="根据机构id查询合作意向信息",httpMethod="GET",response=JSONObject.class,notes="根据机构id查询合作意向信息")
	@RequestMapping("/getOrgCooperationByOrgId")
	@ResponseBody
	public JSONObject getOrgCooperationByOrgId(@ApiParam(required=true,name="orgId",value="orgId,机构id")String orgId){
		JSONObject message = new JSONObject();
		try {
			List<OrganizationCooperation> list = orgCooperationService.getOrgCooperationByOrgId(orgId);
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: updateOrgCooperation 
	* @Description: 修改机构合作意向信息
	* @param organizationCooperation
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="修改机构合作意向信息",httpMethod="POST",response=JSONObject.class,notes="修改机构合作意向信息")
	@RequestMapping("/updateOrgCooperation")
	@ResponseBody
	public JSONObject updateOrgCooperation(@ApiParam(required=true,name="organizationCooperation",value="organizationCooperation,改后机构合作意向对象")OrganizationCooperation organizationCooperation){
		JSONObject message = new JSONObject();
		try {
		    organizationCooperation.setOrgCooperationEditTime(new Date());;
		    int i=0;
		    if(organizationCooperation.getOrgCooperationId()!=null){
		        i = orgCooperationService.updateOrgCooperation(organizationCooperation);
		    }else{
		        organizationCooperation.setOrgCooperationCreateTime(new Date());
		        organizationCooperation.setOrgCooperationEditTime(organizationCooperation.getOrgCooperationCreateTime());;
	            i = orgCooperationService.insertOrgCooperation(organizationCooperation);
		    }
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: deleteOrgCooperation 
	* @Description: 删除机构合作意向
	* @param orgCooperationId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="删除机构合作意向",httpMethod="GET",response=JSONObject.class,notes="删除机构合作意向")
	@RequestMapping("/deleteOrgCooperation")
	@ResponseBody
	public JSONObject deleteOrgCooperation(@ApiParam(required=true,name="orgCooperationId",value="orgCooperationId,机构合作意向id")Long orgCooperationId){
		JSONObject message = new JSONObject();
		try {
			int i = orgCooperationService.deleteOrgCooperation(orgCooperationId);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
