package br.order.controller.empCom;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.user.pojo.empCom.Enterprise;
import br.order.user.pojo.empUser.CustomerInfo;
import br.order.user.service.empCom.EnterpriseService;
import br.order.user.vo.empCom.EnterpriseVo;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: EnterpriseController
 * @Description: 企业
 * @author server
 * @date 2016年9月13日 下午3:07:49
 */
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getEnterpriseByPage 
    * @Description: 分页查询企业信息列表
    * @param page 当前页
    * @param rows 每页显示行数
    * @param enterpriseVo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "查询企业信息", httpMethod = "POST", response = JSONObject.class, notes = "查询列表企业信息")
    @RequestMapping("/getEnterpriseByPage")
    @ResponseBody
    public JSONObject getEnterpriseByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "Enterprise", value = "Enterprise,查询条件") EnterpriseVo enterpriseVo) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<EnterpriseVo> enterpriseByAllPage = enterpriseService.getEnterpriseByAllPage(page, rows, enterpriseVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "enterprise");
            message.put("operationList", operationList);
            message.put("data", enterpriseByAllPage);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
     * @Title: getValidEnterprise 
     * @Description: 查询有效企业信息列表
     * @param enterpriseVo
     * @return    设定文件 
     * @return JSONObject    返回类型 
     */
    @ApiOperation(value = "查询有效企业信息列表", httpMethod = "GET", response = JSONObject.class, notes = "查询有效企业信息列表")
    @RequestMapping("/getValidEnterprise")
    @ResponseBody
    public JSONObject getValidEnterprise(@ApiParam(required = true, name = "Enterprise", value = "Enterprise,查询条件") Enterprise enterprise) {
        JSONObject message = new JSONObject();
        try {

            List<Enterprise> validEnterprise = enterpriseService.getValidEnterprise();
            message.put("data", validEnterprise);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getEnterpriseByEditId 
    * @Description: 根据注册id查询企业信息
    * @param enterpriseId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据企业id查询企业信息", httpMethod = "GET", response = JSONObject.class, notes = "根据企业id查询企业信息")
    @RequestMapping(value = "/getEnterpriseById")
    @ResponseBody
    public JSONObject getEnterpriseByEditId(@ApiParam(required = true, name = "enterpriseId", value = "enterpriseId,企业id") String enterpriseId) {
        JSONObject message = new JSONObject();
        try {
            EnterpriseVo enterpriseVo = enterpriseService.getEnterpriseVoById(enterpriseId);
            message.put("data", enterpriseVo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: insertEnterprise 
    * @Description: 添加企业信息
    * @param enterprise
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "添加企业信息", httpMethod = "POST", response = JSONObject.class, notes = "添加企业信息")
    @RequestMapping(value = "/insertEnterprise")
    @ResponseBody
    public JSONObject insertEnterprise(@ApiParam(required = true, name = "enterprise", value = "enterprise,企业信息对象") Enterprise enterprise,
            @ApiParam(required = true, name = "customerInfo", value = "customerInfo,客户信息对象") CustomerInfo customerInfo) {
        JSONObject message = new JSONObject();
        try {
            String i = enterpriseService.insertEnterprise(enterprise, customerInfo);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateEnterprise 
    * @Description: 修改企业信息 
    * @param enterprise
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改企业信息", httpMethod = "POST", response = JSONObject.class, notes = "修改企业信息")
    @RequestMapping(value = "/updateEnterprise")
    @ResponseBody
    public JSONObject updateEnterprise(@ApiParam(required = true, name = "enterprise", value = "enterprise,企业信息对象") Enterprise enterprise,
            @ApiParam(required = true, name = "customerInfo", value = "customerInfo,用户对象") CustomerInfo customerInfo) {
        JSONObject message = new JSONObject();
        try {
        	if(enterprise.getEnterpriseId()==null&&"".equals(enterprise.getEnterpriseId())){
        		return InterfaceResultUtil.getReturnMapValidValue(message);
        	}
            enterprise.setEnterpriseEditTime(new Date());
            int i = enterpriseService.updateEnterprise(enterprise, customerInfo);
            if (i > 0) {
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteEnterprise 
    * @Description: 删除企业信息
    * @param enterpriseId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除企业信息", httpMethod = "GET", response = JSONObject.class, notes = "删除企业信息")
    @RequestMapping(value = "/deleteEnterprise")
    @ResponseBody
    public JSONObject deleteEnterprise(@ApiParam(required = true, name = "enterpriseId", value = "enterpriseId,企业信息id") String enterpriseId) {
        JSONObject message = new JSONObject();

        try {
            Enterprise enterprise = enterpriseService.getEnterpriseById(enterpriseId);
            enterprise.setEnterpriseStatus(1);
            enterprise.setEnterpriseEditTime(new Date());
            int i = enterpriseService.deleteEnterprise(enterprise);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
