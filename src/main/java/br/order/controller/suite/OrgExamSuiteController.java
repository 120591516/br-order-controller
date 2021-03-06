package br.order.controller.suite;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import br.crm.common.utils.StringTransCodeUtil;
import br.crm.service.suite.OrgExamFeeItemSuiteService;
import br.crm.service.suite.OrgExamSuiteService;
import br.crm.vo.examfeeitem.OrgExamFeeItemVo;
import br.crm.vo.suite.OrgExamSuiteQu;
import br.crm.vo.suite.OrgExamSuiteVo;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: OrgExamSuiteController
 * @Description: TODO 套餐
 * @author kangting
 * @date 2016年9月12日 上午11:53:53
 *
 */
@Controller
@RequestMapping("/orgExamSuite")
public class OrgExamSuiteController {

    @Autowired
    private OrgExamSuiteService orgExamSuiteService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private OrgExamFeeItemSuiteService orgExamFeeItemSuiteService;

    /**
     * 
     * @Title: getOrgExamSuite @Description: TODO 套餐列表展示 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @param orgExamSuiteQu
     *         查询条件 @param @return @return JSONObject @throws
     */
    @ApiOperation(value = "套餐列表展示", httpMethod = "GET", notes = "套餐列表展示")
    @RequestMapping("/getOrgExamSuite")
    @ResponseBody
    public JSONObject getOrgExamSuite(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "orgExamSuiteQu", value = "orgExamSuiteQu,查询条件") OrgExamSuiteQu orgExamSuiteQu) {
        JSONObject message = new JSONObject();
        try {
            OrgExamSuiteQu qu = (OrgExamSuiteQu) StringTransCodeUtil.transCode(orgExamSuiteQu);
            PageInfo<OrgExamSuiteVo> pageInfo = orgExamSuiteService.getOrgExamSuite(page, rows, qu);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgSuite");
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
     * @Title: getOrgExamSuiteById @Description:  根据ID查看套餐列表信息 @param @param
     *         idOrgExamSuite 套餐id @param @return @return JSONObject @throws
     */
    @ApiOperation(value = "根据ID查看套餐列表", notes = "根据id查看套餐列表对应表", httpMethod = "GET")
    @RequestMapping("/getOrgExamSuiteById")
    @ResponseBody
    public JSONObject getOrgExamSuiteById(@ApiParam(value = "idOrgExamSuite", name = "idOrgExamSuite", required = true) String idOrgExamSuite) {
        JSONObject message = new JSONObject();
        try {
            OrgExamSuiteVo oesh = orgExamSuiteService.getOrgExamSuiteById(idOrgExamSuite);
            message.put("data", oesh);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: addOrgExamSuite @Description: TODO 增加套餐列表对应数据 @param @param
     *         orgExamSuiteVo 套餐类 @param @return @return JSONObject @throws
     */
    @ApiOperation(value = "增加套餐列表对应数据", notes = "增加套餐列表数据", httpMethod = "POST")
    @RequestMapping(value = "/addOrgExamSuite", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addOrgExamSuite(@ApiParam(value = "套餐列表对应数据", name = "OrgExamSuite", required = true) OrgExamSuiteVo orgExamSuiteVo) {
        JSONObject message = new JSONObject();
        try {
            int i = orgExamSuiteService.insertOrgExamSuite(orgExamSuiteVo);
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
     * @Title: updateOrgExamSuite @Description: TODO 修改套餐列表对应数据 @param @param
     *         orgExamSuiteVo @param @return @return JSONObject @throws
     */
    @ApiOperation(value = "修改套餐列表对应数据", notes = "OrgExamSuite", httpMethod = "POST")
    @RequestMapping(value = "/updateOrgExamSuite", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateOrgExamSuite(@ApiParam(name = "OrgExamSuite", value = "套餐列表对应信息", required = true) OrgExamSuiteVo orgExamSuiteVo) {
        JSONObject message = new JSONObject();
        try {
            int r = orgExamSuiteService.updateOrgExamSuite(orgExamSuiteVo);
            message.put("data", r);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: delectOrgExamSuite @Description: TODO 删除套餐 @param @param
     *         idOrgExamSuite 套餐id @param @return @return JSONObject @throws
     */
    @ApiOperation(value = "删除套餐", notes = "OrgExamSuite", httpMethod = "GET")
    @RequestMapping(value = "/delectOrgExamSuite", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject delectOrgExamSuite(@ApiParam(name = "idOrgExamSuite", value = "套餐id", required = true) String idOrgExamSuite) {
        JSONObject message = new JSONObject();
        try {

            int r = orgExamSuiteService.delectOrgExamSuite(idOrgExamSuite);
            if (r < 0) {
                message.put("data", "该项存在关联关系，尚不能删除");
                return InterfaceResultUtil.getReturnMapError(message);
            }
            else {
                message.put("data", r);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据套餐查询收费项信息", httpMethod = "GET", response = JSONObject.class, notes = "根据套餐查询收费项信息")
    @RequestMapping("/getOrgExamFeeItemBySuitId")
    @ResponseBody
    public JSONObject getOrgExamFeeItemBySuitId(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "orgExamSuiteId", value = "orgExamSuiteId,套餐id") String orgExamSuiteId, @ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<OrgExamFeeItemVo> pageInfo = orgExamFeeItemSuiteService.getOrgExamFeeItemBySuitId(orgId, orgExamSuiteId, page, rows);
            message.put("data", pageInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据机构ID查询套餐", httpMethod = "GET", response = JSONObject.class, notes = "根据机构ID查询套餐")
    @RequestMapping("/getSuiteListByOrgId")
    @ResponseBody
    public JSONObject getSuiteListByOrgId(String orgId) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(orgId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            List<Map<String, Object>> map = orgExamFeeItemSuiteService.getSuiteListByOrgId(orgId);
            message.put("data", map);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
