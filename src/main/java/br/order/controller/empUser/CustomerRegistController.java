package br.order.controller.empUser;

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
import br.order.user.pojo.empUser.CustomerInfo;
import br.order.user.pojo.empUser.CustomerRegist;
import br.order.user.service.empUser.CustomerInfoService;
import br.order.user.service.empUser.CustomerRegistService;
import br.order.user.vo.empUser.CustomerInfoVo;
import br.order.user.vo.empUser.CustomerRegistQu;
import br.order.user.vo.empUser.CustomerRegistVo;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: CustomerRegistController
 * @Description: 客户注册信息
 * @author server
 * @date 2016年9月13日 下午2:59:10
 */
@Controller
@RequestMapping("/customersRegist")
public class CustomerRegistController {

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustomerRegistService customerRegistService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /** 
    * @Title: getCustomerRegistByPage 
    * @Description: 分页注册用户列表
    * @param page 当前页
    * @param rows 每页显示条数
    * @param customerRegistQu
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "分页注册用户列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询用户注册列表")
    @RequestMapping("/getCustomerRegistByPage")
    @ResponseBody
    public JSONObject getCustomerRegistByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "customerRegist", value = "customerRegist,客户注册信息") CustomerRegistQu customerRegistQu) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<CustomerInfoVo> customerRegistByPage = customerRegistService.getCustomerRegistByPage(page, rows, customerRegistQu);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "customersRegist");
            message.put("operationList", operationList);
            message.put("data", customerRegistByPage);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: insertCustomerRegist 
    * @Description: 新增客户注册信息
    * @param customerRegist 客户注册对象
    * @param customerInfo 客户信息对象
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增客户注册信息", httpMethod = "POST", response = JSONObject.class, notes = "新增客户注册信息")
    @RequestMapping(value = "/insertCustomerRegist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertCustomerRegist(@ApiParam(required = true, name = "customerRegist", value = "customerRegist,注册用户对象") CustomerRegist customerRegist,
            @ApiParam(required = true, name = "customerInfo", value = "customerInfo ,用户信息") CustomerInfo customerInfo) {
        JSONObject message = new JSONObject();
        try {
            String insertCustomerInfo = customerInfoService.insertCustomerInfo(customerInfo);
            if (insertCustomerInfo.length() > 0) {
                customerRegist.setCustomerInfoId(insertCustomerInfo);
                String customerRegistId = customerRegistService.insertCustomerRegist(customerRegist);
                message.put("data", customerRegistId);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateCustomerRegist 
    * @Description:  修改客户注册信息
    * @param customerRegist
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改客户注册信息", httpMethod = "POST", response = JSONObject.class, notes = "修改客户注册信息")
    @RequestMapping(value = "/updateCustomerRegist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateCustomerRegist(@ApiParam(required = true, name = "customerInfo", value = "customerInfo,用户信息") CustomerInfo customerInfo,
            @ApiParam(required = true, name = "customerRegist", value = "customerRegist,注册用户对象") CustomerRegist customerRegist) {
        JSONObject message = new JSONObject();

        try {
            customerInfo.setCustomerInfoId(customerInfo.getCustomerInfoId());
            customerInfo.setCustomerInfoEditTime(new Date());
            int updateCustomerInfo = customerInfoService.updateCustomerInfo(customerInfo);
            if (updateCustomerInfo > 0) {
                CustomerRegist customerRegist1 = new CustomerRegist();
                customerRegist1.setCustomerInfoId(customerInfo.getCustomerInfoId());
                customerRegist1.setCustomerRegistId(customerRegist.getCustomerRegistId());
                customerRegist1.setCustomerRegistEditTime(new Date());
                customerRegistService.updateCustomerRegist(customerRegist1);
            }
            message.put("data", updateCustomerInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: deleteCustomerRegist 
    * @Description: 删除客户注册信息
    * @param customerRegistId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "刪除客户注册信息", httpMethod = "GET", response = JSONObject.class, notes = "刪除客户注册信息")
    @RequestMapping(value = "/deleteCustomerRegist")
    @ResponseBody
    public JSONObject deleteCustomerRegist(@ApiParam(required = true, name = "customerRegistId", value = "customerRegistId,客戶註冊信息Id") String customerRegistId) {
        JSONObject message = new JSONObject();
        try {
            CustomerRegist customerRegist = customerRegistService.getCustomerRegistById(customerRegistId);
            customerRegist.setCustomerRegistId(customerRegistId);
            customerRegist.setCustomerRegistEditTime(new Date());
            customerRegist.setCustomerRegistStatus(1);
            int updateCustomerRegist = customerRegistService.updateCustomerRegist(customerRegist);
            message.put("data", updateCustomerRegist);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: getCustomerRegistById 
    * @Description: 根据id查詢客戶注册信息
    * @param customerRegistId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "查詢客戶注册信息", httpMethod = "GET", response = JSONObject.class, notes = "查詢客户注册信息")
    @RequestMapping(value = "/getCustomerRegistById", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getCustomerRegistById(@ApiParam(required = true, name = "customerRegistId", value = "customerRegistId,用户注册Id") String customerRegistId) {
        JSONObject message = new JSONObject();
        try {
            CustomerRegistVo customerRegist = customerRegistService.getCustomerRegist(customerRegistId);
            message.put("data", customerRegist);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }
}
