package br.order.controller.dict;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictsection;
import br.crm.service.dict.DictSectionService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * @ClassName: DictSectionController
 * @Description: 总检科室字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午12:41:10
 *
 */
@Controller
@RequestMapping("/dictSection")
public class DictSectionController {

	@Autowired
	private DictSectionService dictSectionService;

	/**
	 * 
	 * @Title: getDictSection @Description: 查询总检科室信息 @param @return 设定文件 @return
	 *         JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "查询总检科室信息", httpMethod = "GET", response = JSONObject.class, notes = "查询总检科室信息")
	@RequestMapping("/getDictSection")
	@ResponseBody
	public JSONObject getDictSection() {
		JSONObject messageJsonObject = new JSONObject();
		try {
			List<Dictsection> list = dictSectionService.getDictSectionList();
			JSONArray jsonArray = new JSONArray();
			for (Dictsection dictsection : list) {
				if (dictsection != null) {
					JSONObject obj = new JSONObject();
					obj.put("name", dictsection.getSectionName());
					obj.put("id", dictsection.getIdSection());
					jsonArray.add(obj);
				}
			}
			messageJsonObject.put("data", jsonArray);
			return messageJsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(messageJsonObject);
	}

	/**
	 * 
	 * @Title: deleteDictSection @Description: 删除总检科室信息 @param @param
	 *         idDictSection 血型id @param @return 设定文件 @return JSONObject
	 *         返回类型 @throws
	 */
	@ApiOperation(value = "删除总检科室信息", httpMethod = "GET", response = JSONObject.class, notes = "删除总检科室信息")
	@RequestMapping("/deleteDictSection")
	@ResponseBody
	public JSONObject deleteDictSection(
			@ApiParam(required = true, name = "idDictSection", value = "血型id") Long idDictSection) {
		JSONObject message = new JSONObject();
		if (null == idDictSection || "".equals(idDictSection)) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			Dictsection dictsection = (Dictsection) dictSectionService.getDictSectionById(idDictSection);
			dictsection.setSectionStatus(1);
			int i = dictSectionService.updateDictSection(dictsection);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: addDictSection @Description: 添加总检科室 @param @param dictsection
	 *         总检科室 @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	@ApiOperation(value = "添加总检科室", httpMethod = "POST", response = JSONObject.class, notes = "添加总检科室信息")
	@RequestMapping("/addDictSection")
	@ResponseBody
	public JSONObject addDictSection(
			@ApiParam(required = true, name = "dictSection", value = "总检科室") Dictsection dictsection) {
		JSONObject message = new JSONObject();
		dictsection.setSectionCreatetime(new Date());
		dictsection.setSectionUpdatetime(dictsection.getSectionCreatetime());
		dictsection.setSectionStatus(0);
		try {
			int i = dictSectionService.addDictSection(dictsection);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

	/**
	 * 
	 * @Title: updateDictSection @Description: 修改总检科室信息 @param @param
	 *         dictSection 总检科室信息 @param @return 设定文件 @return JSONObject
	 *         返回类型 @throws
	 */
	@ApiOperation(value = "修改总检科室信息", httpMethod = "POST", response = JSONObject.class, notes = "修改总检科室信息")
	@RequestMapping("/updateDictSection")
	@ResponseBody
	public JSONObject updateDictSection(
			@ApiParam(required = true, name = "dictSection", value = "总检科室信息") Dictsection dictSection) {
		JSONObject message = new JSONObject();
		if (dictSection.getIdSection() == 0) {
			return InterfaceResultUtil.getReturnMapValidValue(message);
		}
		try {
			dictSection.setSectionUpdatetime(new Date());
			int i = dictSectionService.updateDictSection(dictSection);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

}
