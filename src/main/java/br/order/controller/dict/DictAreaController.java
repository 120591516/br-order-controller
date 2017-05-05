package br.order.controller.dict;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.dict.DictArea;
import br.crm.service.dict.DictAreaService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;

/**
 * 
 * @ClassName: DictAreaController
 * @Description: 区域字典表的相关信息维护
 * @author zxy
 * @date 2016年9月12日 上午11:34:56
 *
 */
@Controller
@RequestMapping("/dictArea")
public class DictAreaController {
	@Autowired
	private DictAreaService dictAreaService;
	@Autowired
	private CommonController commonController;

	/**
	 * 
	 * @Title: getAllProvince @Description: 获取所有省份列表 @param @return 设定文件 @return
	 * JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "获取所有省份列表", httpMethod = "GET", response = JSONObject.class, notes = "获取所有省份列表")
	@RequestMapping("/getAllProvince")
	@ResponseBody
	public JSONObject getAllProvince() {
		JSONObject message = new JSONObject();
		try {
			List<DictArea> list = dictAreaService.getAllProvince();
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getCityByProvinceId @Description: 根据省id获取所有市列表 @param @param
	 * provinceId 省id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据省id获取所有市列表", httpMethod = "GET", response = JSONObject.class, notes = "根据省id获取所有市列表")
	@RequestMapping("/getCityByProvinceId")
	@ResponseBody
	public JSONObject getCityByProvinceId(
			@ApiParam(required = true, name = "provinceId", value = "provinceId,省id") Integer provinceId) {
		JSONObject message = new JSONObject();
		try {
			List<DictArea> list = dictAreaService.getCityByProvinceId(provinceId);
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getDistrictByCityId @Description: 根据市id获取所有区县列表 @param @param
	 * cityId 市id @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据市id获取所有区县列表", httpMethod = "GET", response = JSONObject.class, notes = "根据市id获取所有区县列表")
	@RequestMapping("/getDistrictByCityId")
	@ResponseBody
	public JSONObject getDistrictByCityId(
			@ApiParam(required = true, name = "cityId", value = "cityId,市id") Integer cityId) {
		JSONObject message = new JSONObject();
		try {
			List<DictArea> list = dictAreaService.getDistrictByCityId(cityId);
			message.put("data", list);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: getAreaByPage @Description: 根据条件搜索地区 @param @param areaName
	 * 地区名称 @param @param page 当前页 @param @param rows 每页显示条数 @param @return
	 * 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "根据条件搜索地区", httpMethod = "GET", response = JSONObject.class, notes = "根据条件搜索地区")
	@RequestMapping("/getAreaByPage")
	@ResponseBody
	public JSONObject getAreaByPage(
			@ApiParam(required = false, name = "areaName", value = "areaName,地区名称") String areaName,
			@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
		JSONObject message = new JSONObject();
		try {
			if (areaName != null) {
				areaName = new String(areaName.getBytes("ISO8859-1"), "UTF-8");
			}
			PageInfo<DictArea> pageInfo = dictAreaService.getAreaByPage(areaName, page, rows);
			message.put("data", pageInfo);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: insertProvince @Description: 新增省 @param @param dictArea
	 * 新增省 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "新增省", httpMethod = "POST", response = JSONObject.class, notes = "新增省")
	@RequestMapping(value = "/insertArea", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertProvince(
			@ApiParam(required = true, name = "dictArea", value = "dictArea,新增省") DictArea dictArea) {
		JSONObject message = new JSONObject();
		try {
			dictArea.setCreateTime(new Date());
			dictArea.setStatus(0);
			dictArea.setAreaLevel(1);
			dictArea.setProvinceId(0);
			dictArea.setCreateUserId(String.valueOf(commonController.getUserBySession().getUserId()));
			// 插入数据
			int i = dictAreaService.insertProvince(dictArea);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: insertCity @Description: 新增市 @param @param dictArea
	 * 新增市 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "新增市", httpMethod = "POST", response = JSONObject.class, notes = "新增市")
	@RequestMapping(value = "/insertCity", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertCity(
			@ApiParam(required = true, name = "dictArea", value = "dictArea,新增市") DictArea dictArea) {
		JSONObject message = new JSONObject();
		try {
			dictArea.setCreateTime(new Date());
			dictArea.setStatus(0);
			dictArea.setAreaLevel(2);
			dictArea.setCityId(0);
			dictArea.setCreateUserId(String.valueOf(commonController.getUserBySession().getUserId()));
			// 插入数据
			int i = dictAreaService.insertCity(dictArea);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: insertDistrict @Description: 新增县 @param @param dictArea
	 * 新增县 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "新增县", httpMethod = "POST", response = JSONObject.class, notes = "新增县")
	@RequestMapping(value = "/insertDistrict", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertDistrict(
			@ApiParam(required = true, name = "dictArea", value = "dictArea,新增县") DictArea dictArea) {
		JSONObject message = new JSONObject();
		try {
			dictArea.setCreateTime(new Date());
			dictArea.setStatus(0);
			dictArea.setAreaLevel(3);
			dictArea.setCreateUserId(String.valueOf(commonController.getUserBySession().getUserId()));
			// 插入数据
			int i = dictAreaService.insertDistrict(dictArea);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
