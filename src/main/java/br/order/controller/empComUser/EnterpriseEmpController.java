package br.order.controller.empComUser;

import java.util.Date;
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
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.user.pojo.empComUser.EnterpriseEmp;
import br.order.user.pojo.empUser.CustomerInfo;
import br.order.user.service.empComUser.EnterpriseEmpService;
import br.order.user.service.empUser.CustomerInfoService;
import br.order.user.vo.empComUser.EnterpriseEmpVo;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: EnterpriseEmpController
 * @Description:员工表
 * @author server
 * @date 2016年9月13日 下午3:21:11
 */
@Controller
@RequestMapping("/enterpriseEmp")
public class EnterpriseEmpController {

    @Autowired
    private EnterpriseEmpService enterpriseEmpService;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getEnterpriseEmp 
    * @Description: 分页查询员工信息
    * @param page
    * @param rows
    * @param enterpriseEmpVo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "查询员工信息", httpMethod = "GET", response = JSONObject.class, notes = "查询员工信息")
    @RequestMapping("/getEnterpriseEmpByPage")
    @ResponseBody
    public JSONObject getEnterpriseEmp(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "EnterpriseEmpVo", value = "EnterpriseEmpVo,查询条件") EnterpriseEmpVo enterpriseEmpVo) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<EnterpriseEmpVo> enterpriseEmp = enterpriseEmpService.getEnterpriseEmp(page, rows, enterpriseEmpVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "enterpriseEmp");
            message.put("operationList", operationList);
            message.put("data", enterpriseEmp);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: getEnterpriseEmpById 
    * @Description: 根据员工id查询员工信息
    * @param customerInfoId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据员工id查询员工信息", httpMethod = "GET", response = JSONObject.class, notes = "根据员工id查询员工信息")
    @RequestMapping("/getEnterpriseEmpById")
    @ResponseBody
    public JSONObject getEnterpriseEmpById(@ApiParam(required = true, name = "enterpriseEmpId", value = "enterpriseEmpId,查询条件") String enterpriseEmpId) {
        JSONObject message = new JSONObject();
        try {
            EnterpriseEmp enterpriseEmp = enterpriseEmpService.getEmterPriseEmpById(enterpriseEmpId);
            message.put("data", enterpriseEmp);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: insertEnterpriseEmp 
    * @Description: 增加员工的信息
    * @param customerInfo
    * @param enterpriseId
    * @param enterpriseDepId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "添加员工", httpMethod = "POST", response = JSONObject.class, notes = "添加员工")
    @RequestMapping(value = "/insertEnterpriseEmp", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertEnterpriseEmp(@ApiParam(required = true, name = "customerInfo", value = "customerInfo,个人信息对象") CustomerInfo customerInfo,
            @ApiParam(required = true, name = "enterpriseDepId", value = "部门id,enterpriseDepId") String enterpriseId,
            @ApiParam(required = true, name = "enterpriseDepId", value = "部门id,enterpriseDepId") String enterpriseDepId) {

        JSONObject message = new JSONObject();
        try {
            String customerInfoId = customerInfoService.insertCustomerInfo(customerInfo);
            if (customerInfoId != null) {
                EnterpriseEmp enterpriseEmp = new EnterpriseEmp();
                enterpriseEmp.setStatus(0);
                enterpriseEmp.setCreatetime(new Date());
                enterpriseEmp.setEdittime(enterpriseEmp.getCreatetime());
                enterpriseEmp.setCustomerInfoId(customerInfoId);
                enterpriseEmp.setEnterpriseDepId(enterpriseDepId);
                enterpriseEmp.setEnterpriseId(enterpriseId);
                enterpriseEmpService.insertEnterpriseEmp(enterpriseEmp);
                message.put("data", 1);
            }
            message.put("data", 0);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateEnterpriseEmp 
    * @Description: 修改员工信息
    * @param customerInfo
    * @param enterpriseId
    * @param enterpriseDepId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修该员工信息", httpMethod = "POST", response = JSONObject.class, notes = "修改员工信息")
    @RequestMapping("/updateEnterpriseEmp")
    @ResponseBody
    public JSONObject updateEnterpriseEmp(@ApiParam(required = true, name = "customerInfo", value = "customerInfo,个人信息对象") String enterpriseEmpId,
            @ApiParam(required = true, name = "enterpriseId", value = "企业id,enterpriseDepId") String enterpriseId,
            @ApiParam(required = true, name = "enterpriseDepId", value = "部门id,enterpriseDepId") String enterpriseDepId) {

        JSONObject message = new JSONObject();
        try {
            EnterpriseEmp enterpriseEmp = enterpriseEmpService.getEmterPriseEmpById(enterpriseEmpId);
            enterpriseEmp.setEnterpriseEmpId(enterpriseEmpId);
            enterpriseEmp.setEdittime(new Date());
            enterpriseEmp.setEnterpriseDepId(enterpriseDepId);
            enterpriseEmp.setEnterpriseId(enterpriseId);
            int i = enterpriseEmpService.updateEnterpriseEmp(enterpriseEmp);
            if (i > 0) {
                CustomerInfo customerInfo = customerInfoService.getCustomerInfo(enterpriseEmp.getCustomerInfoId());
                if (customerInfo != null && customerInfo.getCustomerInfoId() != null) {
                    customerInfo.setCustomerInfoId(enterpriseEmp.getCustomerInfoId());
                    customerInfo.setCustomerInfoEditTime(new Date());
                }
            }
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteEnterpriseEmp 
    * @Description: 逻辑删除员工信息
    * @param enterpriseEmp
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除员工信息", httpMethod = "POST", response = JSONObject.class, notes = "删除员工信息")
    @RequestMapping("/deleteEnterpriseEmp")
    @ResponseBody
    public JSONObject deleteEnterpriseEmp(@ApiParam(required = true, name = "EnterpriseEmp", value = "EnterpriseEmp,员工信息对象") String enterpriseEmpId) {
        JSONObject message = new JSONObject();
        try {
            EnterpriseEmp enterpriseEmp = enterpriseEmpService.getEmterPriseEmpById(enterpriseEmpId);
            enterpriseEmp.setStatus(1);
            enterpriseEmp.setEdittime(new Date());
            enterpriseEmp.setEnterpriseEmpId(enterpriseEmpId);
            int updateEnterpriseEmp = enterpriseEmpService.updateEnterpriseEmp(enterpriseEmp);
            if (updateEnterpriseEmp > 0) {
                if (enterpriseEmp != null && enterpriseEmp.getCustomerInfoId() != null) {
                    CustomerInfo customerInfo = customerInfoService.getCustomerInfo(enterpriseEmp.getCustomerInfoId());
                    customerInfo.setCustomerInfoStatus(1);
                    //customerInfo.setCustomerInfoId(enterpriseEmp.getCustomerInfoId());
                    customerInfo.setCustomerInfoEditTime(new Date());
                    customerInfoService.updateCustomerInfo(customerInfo);
                }
            }
            message.put("data", updateEnterpriseEmp);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

}
