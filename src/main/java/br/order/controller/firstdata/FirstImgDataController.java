package br.order.controller.firstdata;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;

import br.crm.pojo.dict.DictImg;
import br.crm.pojo.firstshow.Firstimgshow;
import br.crm.service.firstdata.FirstImgDataService;
import br.crm.vo.firstdata.FirstimgshowVo;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

@Controller
@RequestMapping("/firstImgData")
public class FirstImgDataController {

    @Autowired
    private FirstImgDataService firstImgDataService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @ApiOperation(value = "图片列表查询", httpMethod = "GET", response = JSONObject.class, notes = "图片列表查询")
    @RequestMapping("/showFirstImgDataList")
    @ResponseBody
    public JSONObject showFirstImgDataList() {
        JSONObject message = new JSONObject();
        try {
            List<FirstimgshowVo> list = firstImgDataService.showFirstImgDataList();
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "firstImg");
            message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "图片展示新增", httpMethod = "POST", response = JSONObject.class, notes = "图片展示新增")
    @RequestMapping("/addFirstImgData")
    @ResponseBody
    public JSONObject addFirstImgData(Firstimgshow firstimgshow) {
        JSONObject message = new JSONObject();
        try {
            int i = firstImgDataService.addFirstImgData(firstimgshow);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "图片展示查看", httpMethod = "GET", response = JSONObject.class, notes = "图片展示查看")
    @RequestMapping("/getFirstImgDataById")
    @ResponseBody
    public JSONObject getFirstImgDataById(String firstimgshowId) {

        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(firstimgshowId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            FirstimgshowVo firstimgshowVo = firstImgDataService.getFirstImgDataById(firstimgshowId);
            message.put("data", firstimgshowVo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "图片修改", httpMethod = "GET", response = JSONObject.class, notes = "图片修改")
    @RequestMapping("/updateFirstImgData")
    @ResponseBody
    public JSONObject updateFirstImgData(Firstimgshow firstimgshow) {
        JSONObject message = new JSONObject();
        try {
            firstimgshow.setFirstimgshowUpdated(new Date());
            int i = firstImgDataService.updateFirstImgData(firstimgshow);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "图片删除", httpMethod = "GET", response = JSONObject.class, notes = "图片删除")
    @RequestMapping("/deleteFirstImgData")
    @ResponseBody
    public JSONObject deleteFirstImgData(String firstimgshowId) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(firstimgshowId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Firstimgshow firstimgshow = firstImgDataService.getFirstImgDataById(firstimgshowId);
            if (null != firstimgshow) {
                int i = firstImgDataService.deleteFirstImgData(firstimgshow);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "获取图片", httpMethod = "GET", response = JSONObject.class, notes = "获取图片")
    @RequestMapping("/getImgById")
    @ResponseBody
    public JSONObject getImgById(String id, String type) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(type)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            if (type.equals("1")) {// 1 门店
                List<DictImg> list = firstImgDataService.getImgByBranchId(id);
                message.put("data", list);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
            else {
                List<DictImg> list = firstImgDataService.getImgBySuiteId(id);
                message.put("data", list);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "图片位置获取", httpMethod = "GET", response = JSONObject.class, notes = "图片位置获取")
    @RequestMapping("/getImgIndex")
    @ResponseBody
    public JSONObject getImgIndex() {
        JSONObject message = new JSONObject();
        try {

            List<Map<String, String>> list = firstImgDataService.getImgIndex();
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据机构id获取套餐", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id获取套餐")
    @RequestMapping("/getSuiteByOrgId")
    @ResponseBody
    public JSONObject getSuiteByOrgId(String orgId) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(orgId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            List<Map<String, String>> list = firstImgDataService.getSuiteByOrgId(orgId);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
