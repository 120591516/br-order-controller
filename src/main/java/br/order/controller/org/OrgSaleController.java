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
import br.crm.pojo.org.OrganizationSale;
import br.crm.service.org.OrgSaleService;
import br.order.common.utils.InterfaceResultUtil;

/**
 * @ClassName: OrgSaleController
 * @Description: 体检机构销售信息controller
 * @author admin
 * @date 2016年9月12日 下午4:32:17
 */
@Controller
@RequestMapping("/orgSale")
public class OrgSaleController {
	@Autowired
	private OrgSaleService orgSaleService;
	
	/** 
	* @Title: insertOrgSale 
	* @Description: 新增体检机构销售信息
	* @param organizationSale
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="新增体检机构销售信息",httpMethod="POST",response=JSONObject.class,notes="新增体检机构销售信息")
	@RequestMapping(value="/insertOrgSale",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject insertOrgSale(@ApiParam(required=true,name="organizationConn",value="organizationConn,新增机构联系人对象")OrganizationSale organizationSale){
		JSONObject message = new JSONObject();
		try {
			organizationSale.setOrgSaleStatus(0);
			organizationSale.setOrgSaleCreateTime(new Date());
			organizationSale.setOrgSaleEditTime(organizationSale.getOrgSaleCreateTime());
			int i = orgSaleService.insertOrgSale(organizationSale);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: getOrgSaleByOrgId 
	* @Description: 根据机构id查询机构销售信息
	* @param orgId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="根据机构id查询机构销售信息",httpMethod="GET",response=JSONObject.class,notes="根据机构id查询机构销售信息")
	@RequestMapping("/getOrgSaleByOrgId")
	@ResponseBody
	public JSONObject getOrgSaleByOrgId(@ApiParam(required=true,name="orgId",value="orgId,机构id")String orgId){
		JSONObject message = new JSONObject();
		try {
			List<OrganizationSale> list = orgSaleService.getOrgSaleByOrgId(orgId);
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: getOrgSaleBySaleId 
	* @Description: 根据销售id查询机构销售信息
	* @param orgSaleId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="根据销售id查询机构销售信息",httpMethod="GET",response=JSONObject.class,notes="根据销售id查询机构销售信息")
	@RequestMapping("/getOrgSaleBySaleId")
	@ResponseBody
	public JSONObject getOrgSaleBySaleId(@ApiParam(required=true,name="orgSaleId",value="orgSaleId,机构销售id")Long orgSaleId){
		JSONObject message = new JSONObject();
		try {
			OrganizationSale organizationSale = orgSaleService.getOrgSaleBySaleId(orgSaleId);
			message.put("data", organizationSale);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: updateOrgSale 
	* @Description: 修改机构销售信息
	* @param organizationSale
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="修改机构销售信息",httpMethod="POST",response=JSONObject.class,notes="修改机构销售信息")
	@RequestMapping("/updateOrgSale")
	@ResponseBody
	public JSONObject updateOrgSale(@ApiParam(required=true,name="organizationSale",value="organizationSale,改后机构销售信息")OrganizationSale organizationSale){
		JSONObject message = new JSONObject();
		try {
			organizationSale.setOrgSaleEditTime(new Date());
			int i = orgSaleService.updateOrgSale(organizationSale);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: deleteOrgSale 
	* @Description: 删除机构销售信息
	* @param orgSaleId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="删除机构销售信息",httpMethod="GET",response=JSONObject.class,notes="删除机构销售信息")
	@RequestMapping("/deleteOrgSale")
	@ResponseBody
	public JSONObject deleteOrgSale(@ApiParam(required=true,name="orgSaleId",value="orgSaleId,机构销售信息id")Long orgSaleId){
		JSONObject message = new JSONObject();
		try {
			OrganizationSale organizationSale = new OrganizationSale();
			organizationSale.setOrgSaleId(orgSaleId);
			organizationSale.setOrgSaleStatus(1);
			organizationSale.setOrgSaleEditTime(new Date());
			int i = orgSaleService.updateOrgSale(organizationSale);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
