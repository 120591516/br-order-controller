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
import br.crm.pojo.dict.DictconclusionWithBLOBs;
import br.crm.service.dict.DictConclusionService;
import br.crm.vo.conclusion.DictconclusionVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictConclusionController
 * @Description:结论词 字典表
 * @author adminis
 * @date 2016年12月6日 下午1:43:09
 *
 */
@Controller
@RequestMapping("/dictConclusion")
public class DictConclusionController {

    @Autowired
    private DictConclusionService dictConclusionService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "分页搜索查询结论词", httpMethod = "GET", response = JSONObject.class, notes = "分页搜索查询结论词")
    @RequestMapping("/getConclusionList")
    @ResponseBody
    public JSONObject getConclusionList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows, DictconclusionVo dictconclusionVo) {

        JSONObject message = new JSONObject();
        try {
            PageInfo<DictconclusionVo> list = dictConclusionService.getConclusionList(page, rows, dictconclusionVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictConclusion");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "添加结论词", httpMethod = "POST", response = JSONObject.class, notes = "添加结论词")
    @RequestMapping("/addConclusion")
    @ResponseBody
    public JSONObject addConclusion(DictconclusionWithBLOBs dictconclusionWithBLOBs) {

        JSONObject message = new JSONObject();
        try {
            if (null != dictconclusionWithBLOBs.getfFemaledisease() && null != dictconclusionWithBLOBs.getfMaledisease()) {
                dictconclusionWithBLOBs.setIdConclusion(UUIDUtils.getId());
                dictconclusionWithBLOBs.setStatus(0);
                dictconclusionWithBLOBs.setCreatetime(new Date());
                dictconclusionWithBLOBs.setEdittime(new Date());
                int i = dictConclusionService.addConclusion(dictconclusionWithBLOBs);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据id查询结论词", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询结论词")
    @RequestMapping("/getConclusionById")
    @ResponseBody
    public JSONObject getConclusionById(String id) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(id)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            DictconclusionVo dictconclusionVo = dictConclusionService.getConclusionById(id);
            message.put("data", dictconclusionVo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改结论词", httpMethod = "POST", response = JSONObject.class, notes = "修改结论词")
    @RequestMapping("/updateConclusion")
    @ResponseBody
    public JSONObject updateConclusion(DictconclusionVo dictconclusionWithBLOBs) {
        JSONObject message = new JSONObject();
        try {
            dictconclusionWithBLOBs.setEdittime(new Date());
            String id = dictconclusionWithBLOBs.getSexId();
            Integer flag = new Integer(id);
            if (flag == 0) {// 0是通用
                dictconclusionWithBLOBs.setfFemaledisease(0);
                dictconclusionWithBLOBs.setfMaledisease(0);
            }
            else if (flag == 1) {// 男性
                dictconclusionWithBLOBs.setfFemaledisease(1);
                dictconclusionWithBLOBs.setfMaledisease(0);
            }
            else {
                dictconclusionWithBLOBs.setfFemaledisease(1);
                dictconclusionWithBLOBs.setfMaledisease(0);
            }
            int i = dictConclusionService.updateConclusion(dictconclusionWithBLOBs);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除结论词", httpMethod = "POST", response = JSONObject.class, notes = "删除结论词")
    @RequestMapping("/deleteConclusion")
    @ResponseBody
    public JSONObject deleteConclusion(String id) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(id)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {

            DictconclusionVo dictconclusionVo = dictConclusionService.getConclusionById(id);
            if (null != dictconclusionVo) {
                dictconclusionVo.setStatus(1);
                int i = dictConclusionService.updateConclusion(dictconclusionVo);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
