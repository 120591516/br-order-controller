package br.order.controller.suite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

 



import br.crm.common.utils.StringTransCodeUtil;
import br.crm.pojo.suite.OrganizationBranchSuite;
import br.crm.service.suite.OrgBranchSuiteService;
import br.crm.vo.suite.OrgExamSuiteQu;
import br.crm.vo.suite.OrgExamSuiteVo;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.vo.BrUserVo;

/**
 * 
* @ClassName: OrgBranchSuiteController 
* @Description: TODO  门店套餐
* @author kangting
* @date 2016年9月12日 上午11:42:30 
*
 */
@Controller
@RequestMapping("/orgBranchSuite")
public class OrgBranchSuiteController {
	@Autowired
	private OrgBranchSuiteService orgBranchSuiteService;

	@Autowired
	private HttpServletRequest request;
	/**
	 * Session
	 */
	@Autowired
	private CommonController commonController;
	/**
	 * 
	* @Title: getOrgBranchSuiteList 
	* @Description: TODO   门店绑定套餐信息查询
	* @param @param page   当前页
	* @param @param rows   每页显示条数
	* @param @param orgExamSuiteQu 查询条件
	* @param @return    设定文件
	* @return JSONObject   分页 门店绑定套餐信息
	 */
	@ApiOperation(value = "门店套餐信息查询", httpMethod = "GET", notes = "获取全部门店套餐信息")
	@RequestMapping("/getOrgBranchSuiteList")
	@ResponseBody
	public JSONObject getOrgBranchSuiteList(
			@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
			@ApiParam(required = true, name = "orgExamSuiteQu", value = "orgExamSuiteQu,查询条件") OrgExamSuiteQu orgExamSuiteQu) {
		JSONObject mObject = new JSONObject();
		try {
			OrgExamSuiteQu qu=(OrgExamSuiteQu)StringTransCodeUtil.transCode(orgExamSuiteQu);
			PageInfo<OrgExamSuiteVo> pageInfo = orgBranchSuiteService.getOrgBranchSuite(page, rows, qu);
			mObject.put("data", pageInfo);
			return InterfaceResultUtil.getReturnMapSuccess(mObject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(mObject);
	}
	/**
	 * 
	* @Title:               getOrgBranchSuiteById 
	* @Description: TODO    根据id查看门店套餐信息
	* @param @param 		orgBranchSuiteId
	* @param @return        设定文件
	* @return JSONObject    返回绑定套餐的详细信息   
	* @throws
	 */
	@ApiOperation(value = "根据id查看门店套餐信息", httpMethod = "GET", notes = "根据id查看门店套餐信息")
	@RequestMapping("/getOrgBranchSuiteById")
	@ResponseBody
	public JSONObject getOrgBranchSuiteById(
			@ApiParam(required = true, value = "门店套餐信息id", name = "orgBranchSuiteId") String orgBranchSuiteId) {
		JSONObject mJsonObject = new JSONObject();
		try {
			OrgExamSuiteVo orgExamSuiteVo = orgBranchSuiteService.getBranchSuiteById(orgBranchSuiteId);
			mJsonObject.put("data", orgExamSuiteVo);
			return InterfaceResultUtil.getReturnMapSuccess(mJsonObject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(mJsonObject);
	}
/**
 * 
* @Title: insertOrgBranchSuite 
* @Description: TODO   增加门店套餐绑定信息
* @param @param branchId  门店id
* @param @param suiteIds  绑定套餐id 可多个例如1,2
* @param @return       
* @return JSONObject     
* @throws
 */
	@ApiOperation(value = "增加门店套餐信息", httpMethod = "POST", notes = "添加门店信息")
	@RequestMapping("/insertOrgBranchSuite")
	@ResponseBody
	public JSONObject insertOrgBranchSuite(@ApiParam(value = "门店id", required = true, name = "branchId") String branchId,
			@ApiParam(value = "List门店id", required = true, name = "suiteIds") String suiteIds) {
		JSONObject mJsonObject = new JSONObject();
		try {
			BrUserVo brUser = commonController.getUserBySession();
			OrganizationBranchSuite orgBranchSuite = new OrganizationBranchSuite();
			orgBranchSuite.setBranchId(branchId);
			orgBranchSuite.setCreatetime(new Date());
			orgBranchSuite.setStatus(0);
			orgBranchSuite.setEdittime(orgBranchSuite.getCreatetime());
			orgBranchSuite.setEditPersonName(brUser.getUserName());
			/*orgBranchSuite.setEditPersonId(brUser.getUserId());*/
			orgBranchSuite.setEditPersonPlat(1);
			int r=orgBranchSuiteService.insertBranchSuite(orgBranchSuite,suiteIds);
			mJsonObject.put("data", r);
			return InterfaceResultUtil.getReturnMapSuccess(mJsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(mJsonObject);
	}
/**
 * 
* @Title: delectOrgBranchSuiteById 
* @Description: TODO   根据id删除门店套餐信息
* @param @param orgBranchSuiteId 门店套餐信息id
* @param @return     
* @return JSONObject    
* @throws
 */
	@ApiOperation(value = "根据id删除门店套餐信息", httpMethod = "GET", notes = "根据id删除门店套餐信息")
	@RequestMapping("/delectOrgBranchSuiteById")
	@ResponseBody
	public JSONObject delectOrgBranchSuiteById(
			@ApiParam(required = true, value = "门店套餐信息id", name = "orgBranchSuiteId") String orgBranchSuiteId) {
		JSONObject mJsonObject = new JSONObject();
		try {
			
			int r = orgBranchSuiteService.updateOrgBranchSuite(orgBranchSuiteId);
			mJsonObject.put("data", r);
			return InterfaceResultUtil.getReturnMapSuccess(mJsonObject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(mJsonObject);
	}
	
}
