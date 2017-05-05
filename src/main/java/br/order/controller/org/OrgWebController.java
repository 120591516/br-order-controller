package br.order.controller.org;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.OrganizationWeb;
import br.crm.service.org.OrgWebService;
import br.order.common.utils.InterfaceResultUtil;

/**
 * @ClassName: OrgWebController
 * @Description: 体检机构web服务controller
 * @author admin
 * @date 2016年9月12日 下午4:38:16
 */
@Controller
@RequestMapping("/orgWeb")
public class OrgWebController {
	@Autowired
	private OrgWebService orgWebService;
	
	//表单提交中时间转换方法
	/** 
	* @Title: insertOrgWeb 
	* @Description: 新增体检机构web服务
	* @param organizationWeb
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="新增体检机构web服务",httpMethod="POST",response=JSONObject.class,notes="新增体检机构web服务")
	@RequestMapping(value="/insertOrgWeb",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject insertOrgWeb(@ApiParam(required=true,name="organizationWeb",value="organizationWeb,新增机构web服务")OrganizationWeb organizationWeb){
		JSONObject message = new JSONObject();
		try {
			organizationWeb.setOrgWebCreateTime(new Date());
			organizationWeb.setOrgWebEditTime(organizationWeb.getOrgWebCreateTime());
			int i = orgWebService.insertOrgWeb(organizationWeb);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: getOrgWebByOrgId 
	* @Description: 根据机构id查询机构web服务
	* @param orgId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="根据机构id查询机构web服务",httpMethod="GET",response=JSONObject.class,notes="根据机构id查询机构web服务")
	@RequestMapping("/getOrgWebByOrgId")
	@ResponseBody
	public JSONObject getOrgWebByOrgId(@ApiParam(required=true,name="orgId",value="orgId,机构id")String orgId){
		JSONObject message = new JSONObject();
		try {
			List<OrganizationWeb> list = orgWebService.getOrgWebByOrgId(orgId);
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: updateOrgWeb 
	* @Description: 修改机构web服务
	* @param organizationWeb
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="修改机构web服务",httpMethod="POST",response=JSONObject.class,notes="修改机构web服务")
	@RequestMapping(value="/updateOrgWeb",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject updateOrgWeb(
	        @ApiParam(required=true,name="organizationWeb",value="organizationWeb,改后机构联系人对象") OrganizationWeb organizationWeb){
		JSONObject message = new JSONObject();
		try {
		    //判断主键是否存在
		    int i=0;
		    if(organizationWeb.getOrgWebId()!=null){
		        organizationWeb.setOrgWebEditTime(new Date());
		        i = orgWebService.updateOrgWeb(organizationWeb);
		        
		    }else{
		        organizationWeb.setOrgWebCreateTime(new Date());
	            organizationWeb.setOrgWebEditTime(organizationWeb.getOrgWebCreateTime());
	            i = orgWebService.insertOrgWeb(organizationWeb);
		    }
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: deleteOrgWeb 
	* @Description: 删除机构web服务
	* @param orgWebId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="删除机构web服务",httpMethod="GET",response=JSONObject.class,notes="删除机构web服务")
	@RequestMapping("/deleteOrgWeb")
	@ResponseBody
	public JSONObject deleteOrgWeb(@ApiParam(required=true,name="orgWebId",value="orgWebId,机构服务id")Long orgWebId){
		JSONObject message = new JSONObject();
		try {
			int i = orgWebService.deleteOrgWeb(orgWebId);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
