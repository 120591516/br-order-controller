package br.order.controller.examitemvalue;

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

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.examitemvalue.OrganizationExamItemValue;
import br.crm.service.examitemvalue.OrgExamItemValueService;
import br.crm.vo.examitemvalue.OrgExamItemValueVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * (体检项特征词controller)
 * 
 * @ClassName: OrgExamItemValueController
 * @Description: TODO(体检项特征词controller)
 * @author 王文腾
 * @date 2016年9月13日 上午11:30:24
 */
@Controller
@RequestMapping("/orgExamItemValue")
public class OrgExamItemValueController {

    @Autowired
    private OrgExamItemValueService orgExamItemValueService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * @Title: getOrgExamItemValueByPage @Description: TODO(分页获取检查项体征词表) @param
     * page 页数 @param rows 行数 @param orgExamItemValueVo 条件查询对象 @return
     * JSONObject @throws
     */

    @ApiOperation(value = "分页获取检查项体征词表", httpMethod = "GET", response = JSONObject.class, notes = "分页获取检查项体征词表")
    @RequestMapping("/getOrgExamItemValueByPage")
    @ResponseBody
    public JSONObject getOrgExamItemValueByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "orgExamItemValue", value = "orgExamItemValue,条件查询参数对象") OrgExamItemValueVo orgExamItemValueVo) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<OrgExamItemValueVo> pageInfo = orgExamItemValueService.getOrgExamItemValueByPage(orgExamItemValueVo, page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgExamItemValue");
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
     * @Title: insertOrgExamItemValue @Description: TODO(新增检查项体征词) @param
     * orgExamItemValue 新增检查项体征词对象 @return JSONObject @throws
     */

    @ApiOperation(value = "新增检查项体征词", httpMethod = "POST", response = JSONObject.class, notes = "新增检查项体征词")
    @RequestMapping(value = "/insertOrgExamItemValue", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgExamItemValue(@ApiParam(required = true, name = "orgExamItemValue", value = "orgExamItemValue,新增检查项体征词") OrganizationExamItemValue orgExamItemValue) {
        JSONObject message = new JSONObject();
        try {
            // 插入数据
            int i = orgExamItemValueService.insertOrgExamItemValue(orgExamItemValue);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: getOrgExamItemValueById @Description: TODO(根据用户id查询体征词信息) @param
     * orgExamItemValueId 体检项特征词id @return JSONObject @throws
     */

    @ApiOperation(value = "根据id查询体征词信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询体征词信息")
    @RequestMapping("/getOrgExamItemValueById")
    @ResponseBody
    public JSONObject getOrgExamItemValueById(@ApiParam(required = true, name = "orgExamItemValueId", value = "orgExamItemValueId,体征词id") String orgExamItemValueId) {
        JSONObject message = new JSONObject();
        try {
            OrgExamItemValueVo orgExamItemValue = orgExamItemValueService.getOrgExamItemValueById(orgExamItemValueId);
            message.put("data", orgExamItemValue);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: updateOrgExamItemValue @Description: TODO(修改体征词) @param
     * orgExamItemValue 修改体征词对象 @return JSONObject @throws
     */

    @ApiOperation(value = "修改体征词", httpMethod = "POST", response = JSONObject.class, notes = "修改体征词")
    @RequestMapping("/updateOrgExamItemValue")
    @ResponseBody
    public JSONObject updateOrgExamItemValue(@ApiParam(required = true, name = "orgExamItemValue", value = "orgExamItemValue,体征词对象") OrganizationExamItemValue orgExamItemValue) {
        JSONObject message = new JSONObject();
        try {
            int i = orgExamItemValueService.updateOrgExamItemValue(orgExamItemValue);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: deleteOrgExamItemValue @Description: TODO(删除体征词) @param
     * orgExamItemValueId 特征词id @return JSONObject @throws
     */

    @ApiOperation(value = "删除体征词", httpMethod = "GET", response = JSONObject.class, notes = "删除体征词")
    @RequestMapping("/deleteOrgExamItemValue")
    @ResponseBody
    public JSONObject deleteOrgExamItemValue(@ApiParam(required = true, name = "orgExamItemValueId", value = "orgExamItemValueId,体征词id") String orgExamItemValueId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationExamItemValue orgExamItemValue = orgExamItemValueService.getOrgExamItemValueById(orgExamItemValueId);
            if (null != orgExamItemValue) {
                orgExamItemValue.setExamItemValueStatus(1);
                int i = orgExamItemValueService.updateOrgExamItemValue(orgExamItemValue);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "通过检查项id查询体征词信息", httpMethod = "GET", response = JSONObject.class, notes = "通过检查项id查询体征词信息")
    @RequestMapping("/getAllOrgExamItemValue")
    @ResponseBody
    public JSONObject getAllOrgExamItemValue(@ApiParam(required = true, name = "examItemId", value = "examItemId,检查项id") String examItemId) {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationExamItemValue> list = orgExamItemValueService.getAllOrgExamItemValue(examItemId);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "获取可用检查项名称与id", httpMethod = "GET", response = JSONObject.class, notes = "获取可用检查项名称与id")
    @RequestMapping("/getExamItemName")
    @ResponseBody
    public JSONObject getExamItemName() {
        JSONObject message = new JSONObject();
        try {
            List<Map<String, String>> map = orgExamItemValueService.getExamItemName();
            message.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
