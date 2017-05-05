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
import br.crm.pojo.dict.Dicteducation;
import br.crm.service.dict.DictEducationService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DicteducationController
 * @Description: 教育字典表的相关信息维护
 * @author zxy
 * @date 2016年9月12日 上午11:51:17
 *
 */
@Controller
@RequestMapping("/educationManage")
public class DicteducationController {

    @Autowired
    private DictEducationService dictEducationService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getEducationList @Description: 分页查询教育列表 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询教育列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询教育列表")
    @RequestMapping("/getEducationList")
    @ResponseBody
    public JSONObject getEducationList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
            message.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            PageInfo<Dicteducation> pageInfo = dictEducationService.getEducationList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "educationManage");
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
     * @Title: addEducation @Description: 新增教育 @param @param dicteducation
     *         新增国家对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */

    @ApiOperation(value = "新增教育", httpMethod = "POST", response = JSONObject.class, notes = "新增教育")
    @RequestMapping("/addEducation")
    @ResponseBody
    public JSONObject addEducation(@ApiParam(required = true, name = "dictCountry", value = "dictCountry,新增国家对象") Dicteducation dicteducation) {
        JSONObject message = new JSONObject();
        dicteducation.setEducationCreatetime(new Date());
        dicteducation.setEducationStatus(0);
        dicteducation.setEducationUpdatetime(new Date());
        try {
            int i = dictEducationService.addEducation(dicteducation);
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
     * @Title: getEducationById @Description: 根据教育Id查询详细信息 @param @param
     *         idEducation 教育Id @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "根据教育Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据教育Id查询详细信息")
    @RequestMapping("/getEducationById")
    @ResponseBody
    public JSONObject getEducationById(@ApiParam(required = true, name = "idEducation", value = "教育Id") Integer idEducation) {
        JSONObject message = new JSONObject();
        if (null == idEducation || "".equals(idEducation)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dicteducation dicteducation = dictEducationService.getEducationById(idEducation);
            message.put("data", dicteducation);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateEducation @Description: 修改教育信息 @param @param dicteducation
     *         修改教育对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改教育信息", httpMethod = "POST", response = JSONObject.class, notes = "修改教育信息")
    @RequestMapping("/updateEducation")
    @ResponseBody
    public JSONObject updateEducation(@ApiParam(required = true, name = "dicteducation", value = "修改教育对象") Dicteducation dicteducation) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dicteducation.getEducationName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictEducationService.updateEducation(dicteducation);
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
     * @Title: deleteEducation @Description: 删除教育信息 @param @param idEducation
     *         教育id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除教育信息", httpMethod = "GET", response = JSONObject.class, notes = "删除教育信息")
    @RequestMapping("/deleteEducation")
    @ResponseBody
    public JSONObject deleteEducation(@ApiParam(required = true, name = "idEducation", value = "教育id") Integer idEducation) {
        JSONObject message = new JSONObject();
        if (null == idEducation || "".equals(idEducation)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dicteducation dicteducation = dictEducationService.getEducationById(idEducation);
            dicteducation.setEducationStatus(1);
            int i = dictEducationService.updateEducation(dicteducation);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
