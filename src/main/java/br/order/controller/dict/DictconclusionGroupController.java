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
import br.crm.pojo.dict.Dictconclusiongroup;
import br.crm.service.dict.DictconclusionGroupService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictconclusionGroupController
 * @Description: 结论词分组
 * @author adminis
 * @date 2016年12月6日 上午10:29:12
 *
 */
@Controller
@RequestMapping("/dictConclusionGroup")
public class DictconclusionGroupController {

    @Autowired
    private DictconclusionGroupService dictconclusionGroupService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "分页查询结论词分组", httpMethod = "GET", response = JSONObject.class, notes = "分页查询结论词分组")
    @RequestMapping("/getConclusionGroupList")
    @ResponseBody
    public JSONObject getConclusionGroupList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<Dictconclusiongroup> list = dictconclusionGroupService.getConclusionGroupList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictConclusionGroup");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "新增结论词分组", httpMethod = "POST", response = JSONObject.class, notes = "新增结论词分组")
    @RequestMapping("/addConclusionGroup")
    @ResponseBody
    public JSONObject addConclusionGroup(Dictconclusiongroup dictconclusiongroup) {
        JSONObject message = new JSONObject();
        try {
            dictconclusiongroup.setKeyconclusiongroupid(UUIDUtils.getId());
            dictconclusiongroup.setStatus(0);
            dictconclusiongroup.setCreatetime(new Date());
            dictconclusiongroup.setEdittime(new Date());
            int i = dictconclusionGroupService.addConclusionGroup(dictconclusiongroup);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据id查询结论词分组", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询结论词分组")
    @RequestMapping("/getConclusionGroupById")
    @ResponseBody
    public JSONObject getConclusionGroupById(String keyconclusiongroupid) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(keyconclusiongroupid)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusiongroup dictconclusiongroup = dictconclusionGroupService.getConclusionGroupById(keyconclusiongroupid);
            message.put("data", dictconclusiongroup);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改结论词分组", httpMethod = "POST", response = JSONObject.class, notes = "修改结论词分组")
    @RequestMapping("/updateConclusionGroup")
    @ResponseBody
    public JSONObject updateConclusionGroup(Dictconclusiongroup dictconclusiongroup) {
        JSONObject message = new JSONObject();
        try {
            dictconclusiongroup.setEdittime(new Date());
            int i = dictconclusionGroupService.updateConclusionGroup(dictconclusiongroup);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除结论词分组", httpMethod = "GET", response = JSONObject.class, notes = "删除结论词分组")
    @RequestMapping("/deleteConclusionGroup")
    @ResponseBody
    public JSONObject deleteConclusionGroup(String keyconclusiongroupid) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(keyconclusiongroupid)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusiongroup dictconclusiongroup = dictconclusionGroupService.getConclusionGroupById(keyconclusiongroupid);
            if (null != dictconclusiongroup) {
                dictconclusiongroup.setStatus(1);
                int i = dictconclusionGroupService.updateConclusionGroup(dictconclusiongroup);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "查询结论词分组", httpMethod = "GET", response = JSONObject.class, notes = "查询结论词分组")
    @RequestMapping("/getConclusionGroup")
    @ResponseBody
    public JSONObject getConclusionGroupList() {
        JSONObject message = new JSONObject();
        try {
            List<Map<String, String>> map = dictconclusionGroupService.getConclusionGroupList();
            message.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
