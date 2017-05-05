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
import br.crm.pojo.dict.Dictconclusiontype;
import br.crm.service.dict.DictConclusionTypeService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictConclusionTypeController
 * @Description: 结论词类型字典表
 * @author adminis
 * @date 2016年12月5日 下午4:28:08
 *
 */
@Controller
@RequestMapping("/dictConclusionType")
public class DictConclusionTypeController {

    @Autowired
    private DictConclusionTypeService dictConclusionTypeService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "分页查询结论词类型", httpMethod = "GET", response = JSONObject.class, notes = "分页查询结论词类型")
    @RequestMapping("/getConclusionTypeList")
    @ResponseBody
    public JSONObject getConclusionTypeList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {

        JSONObject message = new JSONObject();
        try {
            PageInfo<Dictconclusiontype> list = dictConclusionTypeService.getConclusionTypeList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictConclusionType");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "新增结论词类型", httpMethod = "POST", response = JSONObject.class, notes = "新增结论词类型")
    @RequestMapping("/addConclusionType")
    @ResponseBody
    public JSONObject addConclusionType(Dictconclusiontype dictconclusiontype) {
        JSONObject message = new JSONObject();
        try {
            dictconclusiontype.setIdConclusionType(UUIDUtils.getId());
            dictconclusiontype.setStatus(0);
            dictconclusiontype.setCreatetime(new Date());
            dictconclusiontype.setEdittime(new Date());
            int i = dictConclusionTypeService.addConclusionType(dictconclusiontype);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据id查询结论词类型", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询结论词类型")
    @RequestMapping("/getConclusionTypeById")
    @ResponseBody
    public JSONObject getConclusionTypeById(String idConclusionType) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(idConclusionType)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusiontype dictconclusiontype = dictConclusionTypeService.getConclusionTypeById(idConclusionType);
            message.put("data", dictconclusiontype);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改结论词类型", httpMethod = "POST", response = JSONObject.class, notes = "修改结论词类型")
    @RequestMapping("/updateConclusionType")
    @ResponseBody
    public JSONObject updateConclusionType(Dictconclusiontype dictconclusiontype) {
        JSONObject message = new JSONObject();

        try {
            dictconclusiontype.setEdittime(new Date());
            int i = dictConclusionTypeService.updateConclusionType(dictconclusiontype);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除结论词类型", httpMethod = "GET", response = JSONObject.class, notes = "删除结论词类型")
    @RequestMapping("/deleteConclusionType")
    @ResponseBody
    public JSONObject deleteConclusionType(String idConclusionType) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(idConclusionType)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusiontype dictconclusiontype = dictConclusionTypeService.getConclusionTypeById(idConclusionType);
            if (null != dictconclusiontype) {
                dictconclusiontype.setStatus(1);
                int i = dictConclusionTypeService.updateConclusionType(dictconclusiontype);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "查询结论词类型", httpMethod = "GET", response = JSONObject.class, notes = "查询结论词类型")
    @RequestMapping("/getConclusionType")
    @ResponseBody
    public JSONObject getConclusionTypeList() {
        JSONObject message = new JSONObject();
        try {
            List<Map<String, String>> map = dictConclusionTypeService.getConclusionTypeList();
            message.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
