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
import br.crm.pojo.dict.Dictmarriage;
import br.crm.service.dict.DictMarriageService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictmarriageController
 * @Description: 婚姻字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:39:26
 *
 */
@Controller
@RequestMapping("/marriageManage")
public class DictmarriageController {

    @Autowired
    private DictMarriageService dictMarriageService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getMarriageList @Description: 分页查询婚姻列表 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询婚姻列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询婚姻列表")
    @RequestMapping("/getMarriageList")
    @ResponseBody
    public JSONObject getMarriageList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
            message.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {

            PageInfo<Dictmarriage> pageInfo = dictMarriageService.getMarriageList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "marriageManage");
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
     * @Title: addMarriage @Description: 新增婚姻 @param @param dictmarriage
     *         新增婚姻对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "新增婚姻", httpMethod = "POST", response = JSONObject.class, notes = "新增婚姻")
    @RequestMapping("/addMarriage")
    @ResponseBody
    public JSONObject addMarriage(@ApiParam(required = true, name = "dictidentity", value = "新增婚姻对象") Dictmarriage dictmarriage) {

        JSONObject message = new JSONObject();
        dictmarriage.setMarriageCreatetime(new Date());
        dictmarriage.setMarriageUpdatetime(dictmarriage.getMarriageCreatetime());
        dictmarriage.setMarriageStatus(0);
        try {
            int i = dictMarriageService.addMarriage(dictmarriage);
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
     * @Title: getMarriageById @Description: 根据婚姻Id查询详细信息 @param @param
     *         idMarriage 婚姻Id @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "根据婚姻Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据婚姻Id查询详细信息")
    @RequestMapping("/getMarriageById")
    @ResponseBody
    public JSONObject getMarriageById(@ApiParam(required = true, name = "idMarriage", value = "婚姻Id") Integer idMarriage) {
        JSONObject message = new JSONObject();
        if (null == idMarriage || "".equals(idMarriage)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictmarriage dictmarriage = dictMarriageService.getMarriageById(idMarriage);
            message.put("data", dictmarriage);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateMarriage @Description: 修改婚姻信息 @param @param dictmarriage
     *         修改婚姻对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改婚姻信息", httpMethod = "POST", response = JSONObject.class, notes = "修改婚姻信息")
    @RequestMapping("/updateMarriage")
    @ResponseBody
    public JSONObject updateMarriage(@ApiParam(required = true, name = "dictmarriage", value = "修改婚姻对象") Dictmarriage dictmarriage) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictmarriage.getMarriageName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictMarriageService.updateMarriage(dictmarriage);
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
     * @Title: deleteMarriage @Description: 删除婚姻信息 @param @param idMarriage
     *         婚姻id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除婚姻信息", httpMethod = "GET", response = JSONObject.class, notes = "删除婚姻信息")
    @RequestMapping("/deleteMarriage")
    @ResponseBody
    public JSONObject deleteMarriage(@ApiParam(required = true, name = "idMarriage", value = "婚姻id") Integer idMarriage) {
        JSONObject message = new JSONObject();
        if (null == idMarriage || "".equals(idMarriage)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictmarriage dictmarriage = dictMarriageService.getMarriageById(idMarriage);
            dictmarriage.setMarriageStatus(1);
            int i = dictMarriageService.updateMarriage(dictmarriage);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
