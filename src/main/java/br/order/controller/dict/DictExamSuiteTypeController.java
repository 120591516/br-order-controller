package br.order.controller.dict;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.crm.pojo.org.DictExamSuiteType;
import br.crm.service.dict.DictExamSuiteTypeService;
import br.order.common.utils.InterfaceResultUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * @ClassName: DictExamSuiteTypeController
 * @Description: 体检套餐字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午2:01:12
 *
 */
@RequestMapping("/examSuiteType")
@Controller
public class DictExamSuiteTypeController {
	@Autowired
	private DictExamSuiteTypeService dictExamSuiteTypeService;

	/**
	 * 
	 * @Title: getExamSuiteTypeList @Description: 获取套餐类型名称 @param @param page
	 * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	@ApiOperation(value = "获取套餐类型名称", httpMethod = "GET", notes = "获取套餐类型")
	@RequestMapping("/getExamSuiteTypeList")
	@ResponseBody
	public JSONObject getExamSuiteTypeList(
			@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
		JSONObject message = new JSONObject();
		try {
			PageInfo<DictExamSuiteType> pageInfo = dictExamSuiteTypeService.getExamSuiteTypeByPage(page, rows);
			message.put("data", pageInfo);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getExamSuiteTypes @Description: 获取套餐类型名称 @param @return
	 * 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "获取套餐类型名称", httpMethod = "GET", notes = "获取套餐类型")
	@RequestMapping("/getExamSuiteTypes")
	@ResponseBody
	public JSONObject getExamSuiteTypes() {
		JSONObject message = new JSONObject();
		try {
			List<DictExamSuiteType> list = dictExamSuiteTypeService.getExamSuiteTypes();
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getExamSuiteTypeById @Description: 根据套餐类型id查询基本信息 @param @param
	 * idExamSuiteType 机构id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据套餐类型id查询基本信息", httpMethod = "GET", response = JSONObject.class, notes = "根据套餐类型id查询信息")
	@RequestMapping("/getExamSuiteTypeById")
	@ResponseBody
	public JSONObject getExamSuiteTypeById(
			@ApiParam(required = true, name = "idExamSuiteType", value = "idExamSuiteType,机构id") String idExamSuiteType) {
		JSONObject message = new JSONObject();
		try {
			DictExamSuiteType dictExamSuiteType = dictExamSuiteTypeService.getDictExamSuiteTypeById(idExamSuiteType);
			message.put("data", dictExamSuiteType);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: insertDictExamSuiteType @Description: 新增套餐类型基本信息 @param @param
	 * dictExamSuiteType 新增套餐类型 @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	@ApiOperation(value = "新增套餐类型基本信息", httpMethod = "POST", response = JSONObject.class, notes = "新增套餐类型基本信息")
	@RequestMapping(value = "/insertDictExamSuiteType", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertDictExamSuiteType(
			@ApiParam(required = true, name = "dictExamSuiteType", value = "dictExamSuiteType,新增套餐类型") DictExamSuiteType dictExamSuiteType) {
		JSONObject message = new JSONObject();
		try {
			dictExamSuiteType.setExamTypeStatus(0);
			dictExamSuiteType.setExamTypeCreateTime(new Date());
			dictExamSuiteType.setExamTypeEditTime(dictExamSuiteType.getExamTypeCreateTime());
			int i = dictExamSuiteTypeService.insertExamSuiteType(dictExamSuiteType);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: updateExamSuiteType @Description: 修改套餐类型 @param @param
	 * dictExamSuiteType 修改 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "修改套餐类型", httpMethod = "POST", notes = "修改套餐类型")
	@RequestMapping(value = "/updateExamSuiteType", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateExamSuiteType(
			@ApiParam(required = true, value = "ExamSuiteType,修改", name = "examSuiteType") DictExamSuiteType dictExamSuiteType) {
		JSONObject message = new JSONObject();
		try {
			dictExamSuiteType.setExamTypeEditTime(new Date());
			int i = dictExamSuiteTypeService.updateExamSuiteType(dictExamSuiteType);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: deleteExamSuiteType @Description: 删除套餐类型 @param @param
	 * examSuiteTypeId 套餐类型id @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	@ApiOperation(value = "删除套餐类型", httpMethod = "GET", response = JSONObject.class, notes = "删除套餐类型")
	@RequestMapping("/deleteExamSuiteType")
	@ResponseBody
	public JSONObject deleteExamSuiteType(
			@ApiParam(required = true, name = "examSuiteTypeId", value = "examSuiteTypeId,套餐类型id") String examSuiteTypeId) {
		JSONObject message = new JSONObject();
		try {
			DictExamSuiteType est = new DictExamSuiteType();
			est.setExamTypeId(examSuiteTypeId);
			est.setExamTypeEditTime(new Date());
			est.setExamTypeStatus(1);
			int i = dictExamSuiteTypeService.updateExamSuiteType(est);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

}
