package br.order.controller.dict;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.CommonUtils;
import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictpayway;
import br.crm.service.dict.DictpaywayService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictpaywayController
 * @Description: 支付方式字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午1:57:22
 *
 */
@Controller
@RequestMapping("/dictpayway")
public class DictpaywayController {
    @Autowired
    private DictpaywayService dictpaywayService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getDictpaywayList @Description: 分页查询支付方式列表 @param @param page
     * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */
    @ApiOperation(value = "分页查询支付方式列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询支付方式列表")
    @RequestMapping("/getDictpaywayList")
    @ResponseBody
    public JSONObject getDictpaywayList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject jsonObject = new JSONObject();
        try {
            PageInfo<Dictpayway> dictpaywayByPage = dictpaywayService.getDictpaywayByPage(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictpayway");
            jsonObject.put("operationList", operationList);
            jsonObject.put("data", dictpaywayByPage);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: addDictpayway @Description: 添加支付方式 @param @param dictpayway
     * 支付方式对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加支付方式", httpMethod = "POST", response = JSONObject.class, notes = "添加支付方式")
    @RequestMapping("/addDictpayway")
    @ResponseBody
    public JSONObject addDictpayway(@ApiParam(required = true, name = "dictpayway", value = "dictpayway,支付方式对象") Dictpayway dictpayway) {
        JSONObject jsonObject = new JSONObject();
        dictpayway.setfDisabled(0);
        dictpayway.setStatus(0);
        dictpayway.setCreateTime(new Date());
        dictpayway.setEditTime(dictpayway.getCreateTime());
        try {
            int i = dictpaywayService.addDictpayway(dictpayway);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getDictpaywayById @Description: 根据id获取对象信息 @param @param pid
     * 主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据id获取对象信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象信息")
    @RequestMapping("/getDictpaywayById")
    @ResponseBody
    public JSONObject getDictpaywayById(@ApiParam(required = true, name = "pid", value = "pid,主键") Long pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            Dictpayway dictpayway = dictpaywayService.selectByPrimaryKey(pid);
            jsonObject.put("data", dictpayway);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: updateDictpayway @Description: 修改支付方式 @param @param dictpayway
     * 支付方式对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改支付方式", httpMethod = "POST", response = JSONObject.class, notes = "修改支付方式")
    @RequestMapping("/updateDictpayway")
    @ResponseBody
    public JSONObject updateDictpayway(@ApiParam(required = true, name = "Dictpayway", value = "Dictpayway,支付方式对象") Dictpayway dictpayway) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(dictpayway.getIdPayway())) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            int i = dictpaywayService.updateDictpayway(dictpayway);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: deleteDictpayway @Description: 逻辑删除支付方式 @param @param pid
     * 修改主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "逻辑删除支付方式", httpMethod = "GET", response = JSONObject.class, notes = "逻辑删除支付方式")
    @RequestMapping("/deleteDictpayway")
    @ResponseBody
    public JSONObject deleteDictpayway(@ApiParam(required = true, name = "pid", value = "pid,修改主键") Long pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            Dictpayway dictpayway = dictpaywayService.selectByPrimaryKey(pid);
            dictpayway.setStatus(1);
            int i = dictpaywayService.updateDictpayway(dictpayway);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

}
