package br.order.controller.dict;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictageunit;
import br.crm.service.dict.DictAgeUnitService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictAgeUnitController
 * @Description: 年龄单位字典表的相关维护
 * @author zxy
 * @date 2016年9月12日 上午11:30:31
 *
 */
@Controller
@RequestMapping("/dictAgeUnitManage")
public class DictAgeUnitController {

    @Autowired
    private DictAgeUnitService dictAgeUnitService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getAllDictageunit @Description: 查询所有的年龄单位 @param @return
     *         设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "查询所有的年龄单位", httpMethod = "GET", response = JSONObject.class, notes = "查询所有的年龄单位")
    @RequestMapping("/getAllDictageunit")
    @ResponseBody
    public JSONObject getAllDictageunit() {

        JSONObject message = new JSONObject();

        try {

            List<Dictageunit> list = dictAgeUnitService.getAllDictageunit();
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictAgeUnitManage");
            message.put("operationList", operationList);
            message.put("data", list);

            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: addDictAgeUnit @Description: 添加年龄单位 @param @param dictageunit
     *         新增年龄单位对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加年龄单位", httpMethod = "POST", response = JSONObject.class, notes = "添加年龄单位")
    @RequestMapping("/addDictAgeUnit")
    @ResponseBody
    public JSONObject addDictAgeUnit(@ApiParam(required = true, name = "dictageunit", value = "新增年龄单位对象") Dictageunit dictageunit) {
        JSONObject message = new JSONObject();
        dictageunit.setAgeunitCreatetime(new Date());
        dictageunit.setAgeunitStatus(0);
        dictageunit.setAgeunitUpdatetime(new Date());
        try {
            int i = dictAgeUnitService.addDictAgeUnit(dictageunit);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 根据年龄单位Id查询信息
     */
    @ApiOperation(value = "根据年龄单位Id查询信息", httpMethod = "GET", response = JSONObject.class, notes = "根据年龄单位Id查询信息")
    @RequestMapping("/getDictAgeUnitById")
    @ResponseBody
    public JSONObject getDictAgeUnitById(@ApiParam(required = true, name = "country_id", value = "国家Id") Integer ageunitId) {
        JSONObject message = new JSONObject();
        if (null == ageunitId || "".equals(ageunitId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictageunit dictageunit = dictAgeUnitService.getDictAgeUnitById(ageunitId);
            message.put("data", dictageunit);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateDictageunit @Description: 修改年龄单位信息 @param @param
     *         dictageunit 修改年龄单位对象 @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "修改年龄单位信息", httpMethod = "POST", response = JSONObject.class, notes = "修改年龄单位信息")
    @RequestMapping("/updateDictageunit")
    @ResponseBody
    public JSONObject updateDictageunit(@ApiParam(required = true, name = "dictageunit", value = "修改年龄单位对象") Dictageunit dictageunit) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictageunit.getAgeunitName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            dictageunit.setAgeunitUpdatetime(new Date());
            int i = dictAgeUnitService.updateDictageunit(dictageunit);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: deleteCountry @Description: 删除年龄单位信息 @param @param ageunitId
     *         年龄单位id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除年龄单位信息", httpMethod = "GET", response = JSONObject.class, notes = "删除年龄单位信息")
    @RequestMapping("/deleteDictageunit")
    @ResponseBody
    public JSONObject deleteCountry(@ApiParam(required = true, name = "ageunitId", value = "年龄单位id") Integer ageunitId) {
        JSONObject message = new JSONObject();
        if (null == ageunitId || "".equals(ageunitId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictageunit dictageunit = dictAgeUnitService.getDictAgeUnitById(ageunitId);
            dictageunit.setAgeunitStatus(1);
            int i = dictAgeUnitService.updateDictageunit(dictageunit);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
