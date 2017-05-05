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
import br.crm.pojo.dict.Dictoccupation;
import br.crm.service.dict.DictOccupationService;

/**
 * 
 * @ClassName: DictOccupationController
 * @Description: 职业字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:44:46
 *
 */
@Controller
@RequestMapping("/occupationManage")
public class DictOccupationController {

	@Autowired
	private DictOccupationService dictOccupationService;

	/**
	 * 
	 * @Title: getOccupationList @Description: 分页查询职业列表 @param @param page
	 * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	@ApiOperation(value = "分页查询职业列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询职业列表")
	@RequestMapping("/getOccupationList")
	@ResponseBody
	public JSONObject getOccupationList(
			@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
		JSONObject message = new JSONObject();
		if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
			message.put("message", "页码为空");
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {

			PageInfo<Dictoccupation> pageInfo = dictOccupationService.getOccupationList(page, rows);
			message.put("data", pageInfo);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: addOccupation @Description: 新增职业 @param @param dictoccupation
	 * 新增职业对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "新增职业", httpMethod = "POST", response = JSONObject.class, notes = "新增职业")
	@RequestMapping("/addOccupation")
	@ResponseBody
	public JSONObject addOccupation(
			@ApiParam(required = true, name = "dictoccupation", value = "新增职业对象") Dictoccupation dictoccupation) {
		JSONObject message = new JSONObject();
		dictoccupation.setOccupationCreatetime(new Date());
		dictoccupation.setOccupationUpdatetime(dictoccupation.getOccupationCreatetime());
		dictoccupation.setOccupationStatus(0);
		try {
			int i = dictOccupationService.addOccupation(dictoccupation);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getOccupationById @Description: 根据职业Id查询详细信息 @param @param
	 * idOccupation 职业Id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据职业Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据职业Id查询详细信息")
	@RequestMapping("/getOccupationById")
	@ResponseBody
	public JSONObject getOccupationById(
			@ApiParam(required = true, name = "idOccupation", value = "职业Id") Long idOccupation) {
		JSONObject message = new JSONObject();
		if (null == idOccupation || "".equals(idOccupation)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictoccupation dictoccupation = dictOccupationService.getOccupationById(idOccupation);
			message.put("data", dictoccupation);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: updateOccupation @Description: 修改职业信息 @param @param
	 * dictoccupation 修改职业对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "修改职业信息", httpMethod = "POST", response = JSONObject.class, notes = "修改职业信息")
	@RequestMapping("/updateOccupation")
	@ResponseBody
	public JSONObject updateOccupation(
			@ApiParam(required = true, name = "dictoccupation", value = "修改职业对象") Dictoccupation dictoccupation) {
		JSONObject message = new JSONObject();
		if (StringUtils.isEmpty(dictoccupation.getOccupationName())) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			int i = dictOccupationService.updateOccupation(dictoccupation);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: deleteOccupation @Description: 删除职业 @param @param idOccupation
	 * 血型id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "删除职业", httpMethod = "GET", response = JSONObject.class, notes = "删除职业")
	@RequestMapping("/deleteOccupation")
	@ResponseBody
	public JSONObject deleteOccupation(
			@ApiParam(required = true, name = "idBloodtype", value = "血型id") Long idOccupation) {
		JSONObject message = new JSONObject();
		if (null == idOccupation || "".equals(idOccupation)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictoccupation dictoccupation = dictOccupationService.getOccupationById(idOccupation);
			dictoccupation.setOccupationStatus(1);
			int i = dictOccupationService.updateOccupation(dictoccupation);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
