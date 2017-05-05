package br.order.controller.dict;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import br.crm.pojo.dict.DictCountry;
import br.crm.service.dict.CountryManagerService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: CountryManagerController
 * @Description: 国家字典表的相关维护
 * @author zxy
 * @date 2016年9月12日 上午11:11:12
 *
 */
@Controller
@RequestMapping("/countryManage")
public class CountryManagerController {

    @Autowired
    private CountryManagerService countryManagerService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /**
     * 
     * @Title: getList @Description: 分页查询国家列表 @param @param page
     * 当前页 @param @param rows 每页显示条数 @param @param condition 查询条件 @param @return
     * 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询国家列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询国家列表")
    @RequestMapping("/getList")
    @ResponseBody
    public JSONObject getList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "condition", value = "condition,查询条件") String condition) {

        JSONObject message = new JSONObject();
        if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
            message.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            PageInfo<DictCountry> pageInfo = countryManagerService.getList(page, rows, condition);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "countryManage");
            message.put("operationList", operationList);
            message.put("data", pageInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: addCountry @Description: 新增国家 @param @param dictCountry
     * 新增国家对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "新增国家", httpMethod = "POST", response = JSONObject.class, notes = "新增国家")
    @RequestMapping("/addCountry")
    @ResponseBody
    public JSONObject addCountry(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,新增国家对象") DictCountry dictCountry) {
        JSONObject message = new JSONObject();
        dictCountry.setCountryStatus(0);
        dictCountry.setCountryCreateTime(new Date());
        try {
            // 插入数据
            int i = countryManagerService.addCountry(dictCountry);
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
     * @Title: getCountryById @Description: 根据国家Id查询国家信息 @param @param
     * country_id 国家Id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据国家Id查询国家信息", httpMethod = "GET", response = JSONObject.class, notes = "根据国家Id查询国家信息")
    @RequestMapping("/getCountryById")
    @ResponseBody
    public JSONObject getCountryById(@ApiParam(required = true, name = "country_id", value = "国家Id") Long country_id) {
        JSONObject message = new JSONObject();
        if (null == country_id || "".equals(country_id)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            DictCountry dictCountry = countryManagerService.getCountryById(country_id);
            message.put("data", dictCountry);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
    * @Title: updateCountry
    * @Description: 修改国家信息
    * @param @param dictCountry 修改国家对象
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
    @ApiOperation(value = "修改国家信息", httpMethod = "POST", response = JSONObject.class, notes = "修改国家信息")
    @RequestMapping("/updateCountry")
    @ResponseBody
    public JSONObject updateCountry(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,修改国家对象") DictCountry dictCountry) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictCountry.getCountryKeys()) || StringUtils.isEmpty(dictCountry.getCountryCode()) || StringUtils.isEmpty(dictCountry.getCountryName())
                || StringUtils.isEmpty(dictCountry.getCountryInputCode()) || StringUtils.isEmpty(dictCountry.getCountryLineOrder())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            dictCountry.setCountryUpdateTime(new Date());
            int i = countryManagerService.updateCountry(dictCountry);
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
    * @Title: deleteCountry
    * @Description: 逻辑删除国家信息
    * @param @param countryId 国家id
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
    @ApiOperation(value = "删除国家信息", httpMethod = "GET", response = JSONObject.class, notes = "删除国家信息")
    @RequestMapping("/deleteCountry")
    @ResponseBody
    public JSONObject deleteCountry(@ApiParam(required = true, name = "countryId", value = "countryId,国家id") Long countryId) {
        JSONObject message = new JSONObject();
        if (null == countryId || "".equals(countryId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            DictCountry dictCountry = countryManagerService.getCountryById(countryId);
            dictCountry.setCountryStatus(1);
            int i = countryManagerService.updateCountry(dictCountry);
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
    * @Title: checkCountryDate
    * @Description: 检验国家名称是否重复
    * @param @param dictCountry 国家对象
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
    @ApiOperation(value = "检验国家名称是否重复", httpMethod = "POST", response = JSONObject.class, notes = "检验国家名称是否重复")
    @RequestMapping("/checkCountryName")
    @ResponseBody
    public JSONObject checkCountryName(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,国家对象") DictCountry dictCountry) {

        JSONObject message = new JSONObject();
        try {
            if (null != dictCountry) {
                DictCountry country = countryManagerService.checkCountryName(dictCountry.getCountryName());
                if (null != country && country.getCountryId() != 0) {
                    message.put("data", 1);
                }
                else {
                    message.put("data", 0);

                }
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
    * @Title: checkCountryCode
    * @Description: 检验国家代码是否重复
    * @param @param dictCountry 国家对象
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
    @ApiOperation(value = "检验国家代码是否重复", httpMethod = "POST", response = JSONObject.class, notes = "检验国家代码是否重复")
    @RequestMapping("/checkCountryCode")
    @ResponseBody
    public JSONObject checkCountryCode(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,国家对象") DictCountry dictCountry) {

        JSONObject message = new JSONObject();
        try {

            DictCountry country = countryManagerService.checkCountryCode(dictCountry.getCountryCode());
            if (null != country && country.getCountryId() != 0) {
                message.put("data", 1);
            }
            else {
                message.put("data", 0);

            }
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
    * @Title: checkCountryInputCode
    * @Description: 检验国家输入码是否重复
    * @param @param dictCountry 国家对象
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
    @ApiOperation(value = "检验国家输入码是否重复", httpMethod = "POST", response = JSONObject.class, notes = "检验国家输入码是否重复")
    @RequestMapping("/checkCountryInputCode")
    @ResponseBody
    public JSONObject checkCountryInputCode(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,国家对象") DictCountry dictCountry) {

        JSONObject message = new JSONObject();
        try {
            DictCountry country = countryManagerService.checkCountryInputCode(dictCountry.getCountryInputCode());
            if (null != country && country.getCountryId() != 0) {
                message.put("data", 1);
            }
            else {
                message.put("data", 0);

            }
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
    * @Title: checkCountryLineOrder
    * @Description: 检验国家行序是否重复
    * @param @param dictCountry 国家对象
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
    @ApiOperation(value = "检验国家行序是否重复", httpMethod = "POST", response = JSONObject.class, notes = "检验国家行序是否重复")
    @RequestMapping("/checkCountryLineOrder ")
    @ResponseBody
    public JSONObject checkCountryLineOrder(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,国家对象") DictCountry dictCountry) {

        JSONObject message = new JSONObject();
        try {
            DictCountry country = countryManagerService.checkCountryLineOrder(dictCountry.getCountryLineOrder());
            if (null != country && country.getCountryId() != 0) {
                message.put("data", 1);
            }
            else {
                message.put("data", 0);

            }
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}