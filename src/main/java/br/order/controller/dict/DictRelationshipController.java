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
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.user.pojo.dict.DictRelationship;
import br.order.user.service.dict.DictRelationshipService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictRelationshipController
 * @Description: 人际关系字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午12:44:15
 *
 */
@Controller
@RequestMapping("/dictrelationship")
public class DictRelationshipController {
    @Autowired
    private DictRelationshipService dictRelationshipService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getDictrelationshipList @Description: 分页查询人际关系字典列表 @param @param
     *         page 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询人际关系字典列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询人际关系字典列表")
    @RequestMapping("/getDictrelationshipList")
    @ResponseBody
    public JSONObject getDictrelationshipList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(page) || CommonUtils.isEmpty(rows)) {
            jsonObject.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            PageInfo<DictRelationship> pageInfo = dictRelationshipService.getDictRelationshipByPage(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictrelationship");
            jsonObject.put("operationList", operationList);
            jsonObject.put("data", pageInfo);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);

    }

    /**
     * 
     * @Title: addDictrelationship @Description: 添加人际关系 @param @param
     *         dictrelationship 人际关系对象 @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "添加人际关系", httpMethod = "POST", response = JSONObject.class, notes = "添加人际关系")
    @RequestMapping("/addDictrelationship")
    @ResponseBody
    public JSONObject addDictrelationship(@ApiParam(required = true, name = "dictrelationship", value = "dictrelationship,人际关系对象") DictRelationship dictrelationship) {
        JSONObject jsonObject = new JSONObject();
        dictrelationship.setDictRelationCreateTime(new Date());
        dictrelationship.setDictRelationEditTime(dictrelationship.getDictRelationCreateTime());
        dictrelationship.setDictRelationStatus(0);
        try {
            int i = dictRelationshipService.addDictRelationship(dictrelationship);
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
     * @Title: getDictrelationshipById @Description: 根据id获取对象信息 @param @param
     *         pid 主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据id获取对象信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象信息")
    @RequestMapping("/getDictrelationshipById")
    @ResponseBody
    public JSONObject getDictrelationshipById(@ApiParam(required = true, name = "pid", value = "pid,主键") Long pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            DictRelationship dictrelationship = dictRelationshipService.getDictRelationshipByPid(pid);
            jsonObject.put("data", dictrelationship);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: updateDictrelationship @Description: 修改人际关系 @param @param
     *         dictrelationship 人际关系对象 @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "修改人际关系", httpMethod = "POST", response = JSONObject.class, notes = "修改人际关系")
    @RequestMapping("/updateDictrelationship")
    @ResponseBody
    public JSONObject updateDictrelationship(@ApiParam(required = true, name = "dictrelationship", value = "dictrelationship,人际关系对象") DictRelationship dictrelationship) {
        JSONObject jsonObject = new JSONObject();
        dictrelationship.setDictRelationEditTime(new Date());
        if (CommonUtils.isEmpty(dictrelationship.getDictRelationId())) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            int i = dictRelationshipService.updateDictRelationship(dictrelationship);
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
     * @Title: deleteDictrelationship @Description: 逻辑删除人际关系 @param @param pid
     * 修改主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "逻辑删除人际关系", httpMethod = "GET", response = JSONObject.class, notes = "逻辑删除人际关系")
    @RequestMapping("/deleteDictrelationship")
    @ResponseBody
    public JSONObject deleteDictrelationship(@ApiParam(required = true, name = "pid", value = "pid,修改主键") Long pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            DictRelationship dictrelationship = dictRelationshipService.getDictRelationshipByPid(pid);
            dictrelationship.setDictRelationEditTime(new Date());
            dictrelationship.setDictRelationStatus(1);
            int i = dictRelationshipService.updateDictRelationship(dictrelationship);
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
     * @Title: getDictrelationshipList @Description: 分页查询人际关系字典列表 @param @param
     *         page 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "查询可用人际关系字典列表", httpMethod = "GET", response = JSONObject.class, notes = "查询可用人际关系字典列表")
    @RequestMapping("/getValidRelationship")
    @ResponseBody
    public JSONObject getValidRelationship() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<DictRelationship> cusInfoListByStatus = dictRelationshipService.cusInfoListByStatus();
            jsonObject.put("data", cusInfoListByStatus);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);

    }

}
