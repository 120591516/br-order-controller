package br.order.controller.suite;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import br.crm.common.utils.UUIDUtils;
import br.crm.pojo.org.DictExamSuiteType;
import br.crm.service.dict.DictExamSuiteTypeService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

@Controller
@RequestMapping("/orgExamSuiteType")
public class OrgExamSuiteTypeController {

    @Autowired
    private DictExamSuiteTypeService dictExamSuiteTypeService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "套餐类型列表展示", httpMethod = "GET", notes = "套餐类型列表展示")
    @RequestMapping("/getOrgExamSuiteTypeList")
    @ResponseBody
    public JSONObject getOrgExamSuiteTypeList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<DictExamSuiteType> list = dictExamSuiteTypeService.getExamSuiteTypeByPage(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgExamSuiteType");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "套餐类型新增", httpMethod = "GET", notes = "套餐类型列表新增")
    @RequestMapping("/addOrgExamSuiteType")
    @ResponseBody
    public JSONObject addOrgExamSuiteType(DictExamSuiteType dictExamSuiteType) {
        JSONObject message = new JSONObject();
        try {
            dictExamSuiteType.setExamTypeId(UUIDUtils.getId());
            dictExamSuiteType.setExamTypeStatus(0);
            dictExamSuiteType.setExamTypeCreateTime(new Date());
            dictExamSuiteType.setExamTypeEditTime(dictExamSuiteType.getExamTypeCreateTime());
            int i = dictExamSuiteTypeService.insertExamSuiteType(dictExamSuiteType);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据套餐类型id查询基本信息", httpMethod = "GET", response = JSONObject.class, notes = "根据套餐类型id查询信息")
    @RequestMapping("/getExamSuiteTypeById")
    @ResponseBody
    public JSONObject getExamSuiteTypeById(@ApiParam(required = true, name = "idExamSuiteType", value = "idExamSuiteType,机构id") String idExamSuiteType) {
        JSONObject message = new JSONObject();
        try {
            DictExamSuiteType dictExamSuiteType = dictExamSuiteTypeService.getDictExamSuiteTypeById(idExamSuiteType);
            message.put("data", dictExamSuiteType);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改套餐类型", httpMethod = "POST", notes = "修改套餐类型")
    @RequestMapping(value = "/updateExamSuiteType", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateExamSuiteType(@ApiParam(required = true, value = "ExamSuiteType,修改", name = "examSuiteType") DictExamSuiteType dictExamSuiteType) {
        JSONObject message = new JSONObject();
        try {
            dictExamSuiteType.setExamTypeEditTime(new Date());
            int i = dictExamSuiteTypeService.updateExamSuiteType(dictExamSuiteType);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除套餐类型", httpMethod = "GET", response = JSONObject.class, notes = "删除套餐类型")
    @RequestMapping("/deleteExamSuiteType")
    @ResponseBody
    public JSONObject deleteExamSuiteType(@ApiParam(required = true, name = "examSuiteTypeId", value = "examSuiteTypeId,套餐类型id") String examSuiteTypeId) {
        JSONObject message = new JSONObject();
        try {
            DictExamSuiteType suiteType = dictExamSuiteTypeService.getDictExamSuiteTypeById(examSuiteTypeId);
            suiteType.setExamTypeEditTime(new Date());
            suiteType.setExamTypeStatus(1);
            int i = dictExamSuiteTypeService.updateExamSuiteType(suiteType);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
