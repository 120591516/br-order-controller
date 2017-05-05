package br.order.controller.dict;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictidentity;
import br.crm.service.dict.DictidentityService;

/**
 * 
 * @ClassName: DictidentityController
 * @Description: 身份字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:28:08
 *
 */
@Controller
@RequestMapping("/IdentityManage")
public class DictidentityController {

	@Autowired
	private DictidentityService dictidentityService;

	/**
	 * 
	 * @Title: getIdentityList @Description: 分页查询身份 @param @param page
	 * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	@ApiOperation(value = "分页查询身份", httpMethod = "GET", response = JSONObject.class, notes = "分页查询身份")
	@RequestMapping("/getIdentityList")
	@ResponseBody
	public JSONObject getIdentityList(
			@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
		JSONObject message = new JSONObject();
		if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
			message.put("message", "页码为空");
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			PageInfo<Dictidentity> pageInfo = dictidentityService.getIdentityList(page, rows);
			message.put("data", pageInfo);
			return InterfaceResultUtil.getReturnMapSuccess(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: addIdentity @Description: 新增身份 @param @param dictidentity
	 * 新增身份对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "新增身份", httpMethod = "POST", response = JSONObject.class, notes = "新增身份")
	@RequestMapping("/addIdentity")
	@ResponseBody
	public JSONObject addIdentity(
			@ApiParam(required = true, name = "dictidentity", value = "新增身份对象") Dictidentity dictidentity) {
		JSONObject message = new JSONObject();
		dictidentity.setIdIdentityCreatetime(new Date());
		dictidentity.setIdIdentityStatus(0);
		try {
			int i = dictidentityService.addIdentity(dictidentity);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getIdentityById @Description: 根据身份Id查询详细信息 @param @param
	 * idIdentity 身份Id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据身份Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据身份Id查询详细信息")
	@RequestMapping("/getIdentityById")
	@ResponseBody
	public JSONObject getIdentityById(@ApiParam(required = true, name = "idIdentity", value = "身份Id") Long idIdentity) {
		JSONObject message = new JSONObject();
		if (null == idIdentity || "".equals(idIdentity)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictidentity dictidentity = dictidentityService.getIdentityById(idIdentity);
			message.put("data", dictidentity);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: updateIdentity @Description: 修改身份信息 @param @param dictidentity
	 * 修改教育对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "修改身份信息", httpMethod = "POST", response = JSONObject.class, notes = "修改身份信息")
	@RequestMapping("/updateIdentity")
	@ResponseBody
	public JSONObject updateIdentity(
			@ApiParam(required = true, name = "dicteducation", value = "修改教育对象") Dictidentity dictidentity) {
		JSONObject message = new JSONObject();
		if (StringUtils.isEmpty(dictidentity.getIdentityName())) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			int i = dictidentityService.updateIdentity(dictidentity);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: deleteIdentity @Description: 删除身份信息 @param @param idIdentity
	 * 身份id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "删除身份信息", httpMethod = "GET", response = JSONObject.class, notes = "删除身份信息")
	@RequestMapping("/deleteIdentity")
	@ResponseBody
	public JSONObject deleteIdentity(@ApiParam(required = true, name = "idIdentity", value = "身份id") Long idIdentity) {
		JSONObject message = new JSONObject();
		if (null == idIdentity || "".equals(idIdentity)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictidentity dictidentity = dictidentityService.getIdentityById(idIdentity);
			dictidentity.setIdIdentityStatus(1);
			int i = dictidentityService.updateIdentity(dictidentity);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

}
