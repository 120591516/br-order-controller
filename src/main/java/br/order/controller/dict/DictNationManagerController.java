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
import br.crm.pojo.dict.DictNation;
import br.crm.service.dict.DictNationService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictNationManagerController
 * @Description: 民族字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:42:12
 *
 */
@Controller
@RequestMapping("/dictNationManage")
public class DictNationManagerController {

    @Autowired
    private DictNationService dictNationService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getAllNation @Description: 分页获取所有的民族列表 @param @param page
     * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */
    @ApiOperation(value = "分页获取所有的民族列表", httpMethod = "GET", response = JSONObject.class, notes = "分页获取所有的民族列表")
    @RequestMapping("/getAllNation")
    @ResponseBody
    public JSONObject getAllNation(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
            message.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            PageInfo<DictNation> pageInfo = dictNationService.getAllNation(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictNationManage");
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
     * @Title: addNation @Description: 添加民族 @param @param dictNation
     * 新增民族对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加民族", httpMethod = "POST", response = JSONObject.class, notes = "添加民族")
    @RequestMapping("/addNation")
    @ResponseBody
    public JSONObject addNation(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,新增民族对象") DictNation dictNation) {
        JSONObject message = new JSONObject();
        dictNation.setCreatetime(new Date());
        dictNation.setEditTime(dictNation.getCreatetime());
        dictNation.setNationStatus(0);
        try {
            int i = dictNationService.addNation(dictNation);
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
     * @Title: getNationById @Description: 根据民族Id查询民族信息 @param @param id
     * 民族Id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据民族Id查询民族信息", httpMethod = "GET", response = JSONObject.class, notes = "根据民族Id查询民族信息")
    @RequestMapping("/getNationById")
    @ResponseBody
    public JSONObject getNationById(@ApiParam(required = true, name = "country_id", value = "民族Id") Long id) {
        JSONObject message = new JSONObject();
        if (null == id || "".equals(id)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {

            DictNation dictNation = dictNationService.getNationById(id);

            message.put("data", dictNation);

            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateNation @Description: 修改民族信息 @param @param dictNation
     * 修改民族对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改民族信息", httpMethod = "POST", response = JSONObject.class, notes = "修改民族信息")
    @RequestMapping("/updateNation")
    @ResponseBody
    public JSONObject updateNation(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,修改民族对象") DictNation dictNation) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictNation.getNationName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictNationService.updateNation(dictNation);
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
     * @Title: deleteDictNation @Description: 删除民族信息 @param @param id
     * 民族id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除民族信息", httpMethod = "GET", response = JSONObject.class, notes = "删除民族信息")
    @RequestMapping("/deleteDictNation")
    @ResponseBody
    public JSONObject deleteDictNation(@ApiParam(required = true, name = "id", value = "民族id") Long id) {
        JSONObject message = new JSONObject();
        if (null == id || "".equals(id)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {

            DictNation dictNation = dictNationService.getNationById(id);
            dictNation.setNationStatus(1);
            int i = dictNationService.updateNation(dictNation);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
