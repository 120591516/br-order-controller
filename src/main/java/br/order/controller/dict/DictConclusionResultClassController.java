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
import br.crm.pojo.dict.Dictconclusionresultclass;
import br.crm.service.dict.DictConclusionResultClassService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictConclusionResultClassController
 * @Description: 结论词结果分类
 * @author adminis
 * @date 2016年12月6日 上午11:15:32
 *
 */
@Controller
@RequestMapping("/dictConclusionResultClass")
public class DictConclusionResultClassController {

    @Autowired
    private DictConclusionResultClassService dictConclusionResultClassService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "分页查询结论词结果分类", httpMethod = "GET", response = JSONObject.class, notes = "分页查询结论词结果分类")
    @RequestMapping("/getConclusionResultClassList")
    @ResponseBody
    public JSONObject getConclusionResultClassList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<Dictconclusionresultclass> list = dictConclusionResultClassService.getConclusionResultClassList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictConclusionResultClass");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "新增结论词结果分类", httpMethod = "POST", response = JSONObject.class, notes = "新增结论词结果分类")
    @RequestMapping("/addConclusionResultClass")
    @ResponseBody
    public JSONObject addConclusionResultClass(Dictconclusionresultclass dictconclusionresultclass) {

        JSONObject message = new JSONObject();
        try {
            dictconclusionresultclass.setIdConclusionresultclass(UUIDUtils.getId());
            dictconclusionresultclass.setStatus(0);
            dictconclusionresultclass.setCreatetime(new Date());
            dictconclusionresultclass.setEdittime(new Date());
            int i = dictConclusionResultClassService.addConclusionResultClass(dictconclusionresultclass);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据id查询结论词结果分类", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询结论词结果分类")
    @RequestMapping("/getConclusionResultClassById")
    @ResponseBody
    public JSONObject getConclusionResultClassById(String idConclusionresultclass) {

        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(idConclusionresultclass)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusionresultclass dictconclusionresultclass = dictConclusionResultClassService.getConclusionResultClassById(idConclusionresultclass);
            message.put("data", dictconclusionresultclass);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "修改结论词结果分类", httpMethod = "POST", response = JSONObject.class, notes = "修改结论词结果分类")
    @RequestMapping("/updateConclusionResultClass")
    @ResponseBody
    public JSONObject updateConclusionResultClass(Dictconclusionresultclass dictconclusionresultclass) {
        JSONObject message = new JSONObject();
        try {
            dictconclusionresultclass.setEdittime(new Date());
            int i = dictConclusionResultClassService.updateConclusionResultClass(dictconclusionresultclass);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除结论词结果分类", httpMethod = "GET", response = JSONObject.class, notes = "删除结论词结果分类")
    @RequestMapping("/deleteConclusionResultClass")
    @ResponseBody
    public JSONObject deleteConclusionResultClass(String idConclusionresultclass) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(idConclusionresultclass)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictconclusionresultclass dictconclusionresultclass = dictConclusionResultClassService.getConclusionResultClassById(idConclusionresultclass);
            if (null != dictConclusionResultClassService) {
                dictconclusionresultclass.setStatus(1);
                int i = dictConclusionResultClassService.updateConclusionResultClass(dictconclusionresultclass);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "查询结论词结果分类", httpMethod = "GET", response = JSONObject.class, notes = "查询结论词结果分类")
    @RequestMapping("/getConclusionResultClass")
    @ResponseBody
    public JSONObject getConclusionResultClassList() {
        JSONObject message = new JSONObject();
        try {
            List<Map<String, String>> map = dictConclusionResultClassService.getConclusionResultClassList();
            message.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
