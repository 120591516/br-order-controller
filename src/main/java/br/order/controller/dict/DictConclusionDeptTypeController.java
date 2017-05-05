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
import br.crm.common.utils.UUIDUtils;
import br.crm.pojo.dict.Dictconclusiondepttype;
import br.crm.service.dict.DictConclusionDeptTypeService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictConclusionDeptTypeController
 * @Description: 结论词科室类型
 * @author adminis
 * @date 2016年12月6日 上午9:35:29
 *
 */
@Controller
@RequestMapping("/dictConclusionDeptType")
public class DictConclusionDeptTypeController {

    @Autowired
    private DictConclusionDeptTypeService dictConclusionDeptTypeService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "分页查询结论词科室类型", httpMethod = "GET", response = JSONObject.class, notes = "分页查询结论词科室类型")
    @RequestMapping("/getConclusionDeptTypeList")
    @ResponseBody
    public JSONObject getConclusionDeptTypeList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<Dictconclusiondepttype> list = dictConclusionDeptTypeService.getConclusionDeptTypeList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictConclusionDeptType");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "新增结论词科室类型", httpMethod = "POST", response = JSONObject.class, notes = "新增结论词科室类型")
    @RequestMapping("/addConclusionDeptType")
    @ResponseBody
    public JSONObject addConclusionDeptType(Dictconclusiondepttype dictconclusiondepttype) {

        JSONObject message = new JSONObject();
        try {
            dictconclusiondepttype.setIdConclusionDeptType(UUIDUtils.getId());
            dictconclusiondepttype.setStatus(0);
            dictconclusiondepttype.setCreatetime(new Date());
            dictconclusiondepttype.setEdittime(new Date());
            int i = dictConclusionDeptTypeService.addConclusionDeptType(dictconclusiondepttype);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据id查询结论词科室类型", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询结论词科室类型")
    @RequestMapping("/getConclusionDeptTypeById")
    @ResponseBody
    public JSONObject getConclusionDeptTypeById(String idConclusionDeptType) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(idConclusionDeptType)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {

            Dictconclusiondepttype dictconclusiondepttype = dictConclusionDeptTypeService.getConclusionDeptTypeById(idConclusionDeptType);
            message.put("data", dictconclusiondepttype);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改结论词科室类型", httpMethod = "POST", response = JSONObject.class, notes = "修改结论词科室类型")
    @RequestMapping("/updateConclusionDeptType")
    @ResponseBody
    public JSONObject updateConclusionDeptType(Dictconclusiondepttype dictconclusiondepttype) {
        JSONObject message = new JSONObject();
        try {
            dictconclusiondepttype.setEdittime(new Date());
            int i = dictConclusionDeptTypeService.updateConclusionDeptType(dictconclusiondepttype);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除结论词科室类型", httpMethod = "GET", response = JSONObject.class, notes = "删除结论词科室类型")
    @RequestMapping("/deleteConclusionDeptType")
    @ResponseBody
    public JSONObject deleteConclusionDeptType(String idConclusionDeptType) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(idConclusionDeptType)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusiondepttype dictconclusiondepttype = dictConclusionDeptTypeService.getConclusionDeptTypeById(idConclusionDeptType);
            if (null != dictconclusiondepttype) {
                dictconclusiondepttype.setStatus(1);
                int i = dictConclusionDeptTypeService.updateConclusionDeptType(dictconclusiondepttype);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "查询结论词科室类型", httpMethod = "GET", response = JSONObject.class, notes = "查询结论词科室类型")
    @RequestMapping("/getConclusionDeptType")
    @ResponseBody
    public JSONObject getConclusionDeptTypeList() {
        JSONObject message = new JSONObject();
        try {
            List<Map<String, String>> map = dictConclusionDeptTypeService.getConclusionDeptTypeList();
            message.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
