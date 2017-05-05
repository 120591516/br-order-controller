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
import br.crm.pojo.dict.Dictinformway;
import br.crm.service.dict.DictinFormWayService;

/**
 * 
 * @ClassName: DictinformwayController
 * @Description: 通知方式字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:31:16
 *
 */
@Controller
@RequestMapping("/informwayManage")
public class DictinformwayController {

	@Autowired
	private DictinFormWayService dictinFormWayService;

	/**
	 * 
	 * @Title: getInformwayList @Description: 分页查询通知方式 @param @param page
	 * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	@ApiOperation(value = "分页查询通知方式", httpMethod = "GET", response = JSONObject.class, notes = "分页查询通知方式")
	@RequestMapping("/getInformwayList")
	@ResponseBody
	public JSONObject getInformwayList(
			@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
		JSONObject message = new JSONObject();
		if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
			message.put("message", "页码为空");
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			PageInfo<Dictinformway> pageInfo = dictinFormWayService.getInformwayList(page, rows);
			message.put("data", pageInfo);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: addInformway @Description: 添加通知方式 @param @param dictinformway
	 * 新增通知对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "添加通知方式", httpMethod = "POST", response = JSONObject.class, notes = "添加通知方式")
	@RequestMapping("/addInformway")
	@ResponseBody
	public JSONObject addInformway(
			@ApiParam(required = true, name = "dictinformway", value = "新增通知对象") Dictinformway dictinformway) {
		JSONObject message = new JSONObject();
		dictinformway.setInformwayCreatetime(new Date());
		dictinformway.setInformwayStatus(0);
		dictinformway.setInformwayUpdatetime(dictinformway.getInformwayCreatetime());
		try {
			int i = dictinFormWayService.addInformway(dictinformway);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getInformwayById @Description: 根据方式Id查询详细信息 @param @param
	 * idInformway 通知方式Id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据方式Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据方式Id查询详细信息")
	@RequestMapping("/getInformwayById")
	@ResponseBody
	public JSONObject getInformwayById(
			@ApiParam(required = true, name = "idInformway", value = "通知方式Id") Long idInformway) {
		JSONObject message = new JSONObject();
		if (null == idInformway || "".equals(idInformway)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictinformway dictinformway = dictinFormWayService.getInformwayById(idInformway);
			message.put("data", dictinformway);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: updateInformway @Description: 修改通知信息 @param @param dictinformway
	 * 修改教育对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "修改通知信息", httpMethod = "POST", response = JSONObject.class, notes = "修改通知信息")
	@RequestMapping("/updateInformway")
	@ResponseBody
	public JSONObject updateInformway(
			@ApiParam(required = true, name = "dicteducation", value = "修改教育对象") Dictinformway dictinformway) {
		JSONObject message = new JSONObject();
		if (StringUtils.isEmpty(dictinformway.getInformwayName())) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			int i = dictinFormWayService.updateInformway(dictinformway);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: deleteInformway @Description: 删除通知信息 @param @param idInformway
	 * 通知id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "删除通知信息", httpMethod = "GET", response = JSONObject.class, notes = "删除通知信息")
	@RequestMapping("/deleteInformway")
	@ResponseBody
	public JSONObject deleteInformway(
			@ApiParam(required = true, name = "idInformway", value = "通知id") Long idInformway) {
		JSONObject message = new JSONObject();
		if (null == idInformway || "".equals(idInformway)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictinformway dictinformway = dictinFormWayService.getInformwayById(idInformway);
			dictinformway.setInformwayStatus(1);
			int i = dictinFormWayService.updateInformway(dictinformway);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
