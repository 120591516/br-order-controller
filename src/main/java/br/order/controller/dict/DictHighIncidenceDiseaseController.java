package br.order.controller.dict;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.common.utils.UUIDUtils;
import br.crm.pojo.org.DictHighIncidenceDisease;
import br.crm.service.dict.DictHighIncidenceDiseaseService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictHighIncidenceDiseaseController
 * @Description: 高发疾病字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:25:13
 *
 */
@Controller
@RequestMapping("/highIncidenceDisease")
public class DictHighIncidenceDiseaseController {

    @Autowired
    private DictHighIncidenceDiseaseService dictHighIncidenceDiseaseService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getHighIncidenceDiseaseList @Description:
     * 分页查询高发疾病列表 @param @param page 当前页 @param @param rows
     * 每页显示条数 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询高发疾病列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询高发疾病列表")
    @RequestMapping("/getHighIncidenceDiseaseList")
    @ResponseBody
    public JSONObject getHighIncidenceDiseaseList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<DictHighIncidenceDisease> pageInfo = dictHighIncidenceDiseaseService.getHighIncidenceDiseaseList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "highIncidenceDisease");
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
     * @Title: getHighIncidenceDiseases @Description: 查询高发疾病列表 @param @return
     * 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "查询高发疾病列表", httpMethod = "GET", response = JSONObject.class, notes = "查询高发疾病列表")
    @RequestMapping("/getHighIncidenceDiseases")
    @ResponseBody
    public JSONObject getHighIncidenceDiseases() {
        JSONObject message = new JSONObject();
        try {
            List<DictHighIncidenceDisease> list = dictHighIncidenceDiseaseService.getHighIncidenceDiseases();
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: addHighIncidenceDisease @Description: 新增高发疾病 @param @param
     * highIncidenceDisease 新增高发疾病 @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */
    @ApiOperation(value = "新增高发疾病", httpMethod = "POST", response = JSONObject.class, notes = "新增高发疾病")
    @RequestMapping("/addhighIncidenceDisease")
    @ResponseBody
    public JSONObject addHighIncidenceDisease(@ApiParam(required = true, name = "highIncidenceDisease", value = "dictCountry,新增高发疾病") DictHighIncidenceDisease highIncidenceDisease) {
        JSONObject message = new JSONObject();
        highIncidenceDisease.setHighIncidenceDiseaseId(UUIDUtils.getId());
        highIncidenceDisease.setHighIncidenceDiseaseCreateTime(new Date());
        highIncidenceDisease.setHighIncidenceDiseaseEditTime(highIncidenceDisease.getHighIncidenceDiseaseCreateTime());
        highIncidenceDisease.setHighIncidenceDiseaseStatus(0);
        try {
            int i = dictHighIncidenceDiseaseService.addHighIncidenceDisease(highIncidenceDisease);
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
     * @Title: getEducationById @Description: 根据高发疾病Id查询详细信息 @param @param
     * idhighIncidenceDisease 高发疾病Id @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */
    @ApiOperation(value = "根据高发疾病Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据高发疾病Id查询详细信息")
    @RequestMapping("/gethighIncidenceDiseaseById")
    @ResponseBody
    public JSONObject getEducationById(@ApiParam(required = true, name = "idhighIncidenceDisease", value = "高发疾病Id") String idhighIncidenceDisease) {
        JSONObject message = new JSONObject();
        if (null == idhighIncidenceDisease || "".equals(idhighIncidenceDisease)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            DictHighIncidenceDisease highIncidenceDisease = dictHighIncidenceDiseaseService.getHighIncidenceDiseaseById(idhighIncidenceDisease);
            message.put("data", highIncidenceDisease);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateHighIncidenceDisease @Description: 修改高发疾病信息 @param @param
     * highIncidenceDisease 修改高发疾病对象 @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */

    @ApiOperation(value = "修改高发疾病信息", httpMethod = "POST", response = JSONObject.class, notes = "修改高发疾病信息")
    @RequestMapping("/updateHighIncidenceDisease")
    @ResponseBody
    public JSONObject updateHighIncidenceDisease(@ApiParam(required = true, name = "highIncidenceDisease", value = "修改高发疾病对象") DictHighIncidenceDisease highIncidenceDisease) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(highIncidenceDisease.getHighIncidenceDiseaseName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            highIncidenceDisease.setHighIncidenceDiseaseEditTime(new Date());
            int i = dictHighIncidenceDiseaseService.updateHighIncidenceDisease(highIncidenceDisease);
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
     * @Title: deleteEducation @Description: 删除高发疾病信息 @param @param
     * idHighIncidenceDisease 高发疾病id @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */
    @ApiOperation(value = "删除高发疾病信息", httpMethod = "GET", response = JSONObject.class, notes = "删除高发疾病信息")
    @RequestMapping("/deleteHighIncidenceDisease")
    @ResponseBody
    public JSONObject deleteEducation(@ApiParam(required = true, name = "idHighIncidenceDisease", value = "高发疾病id") String idHighIncidenceDisease) {
        JSONObject message = new JSONObject();
        if (null == idHighIncidenceDisease || "".equals(idHighIncidenceDisease)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            DictHighIncidenceDisease highIncidenceDisease = dictHighIncidenceDiseaseService.getHighIncidenceDiseaseById(idHighIncidenceDisease);
            highIncidenceDisease.setHighIncidenceDiseaseStatus(1);
            int i = dictHighIncidenceDiseaseService.updateHighIncidenceDisease(highIncidenceDisease);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
