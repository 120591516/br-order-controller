package br.order.controller.empUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.GetRequestMappingName;
import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictsex;
import br.crm.service.dict.DictsexService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.user.pojo.empUser.CustomerInfo;
import br.order.user.pojo.empUserRelation.Relationship;
import br.order.user.service.empUser.CustomerInfoService;
import br.order.user.service.empUserRelation.RelationshipService;
import br.order.user.vo.empUser.CustomerRegistVo;
import br.order.vo.BrUserVo;

/**
 * @ClassName: CustomerInfoController
 * @Description: 客户信息表
 * @author server
 * @date 2016年9月13日 下午2:42:45
 */
@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController {
    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private DictsexService dicSexService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /** 
    * @Title: getcustomerInfoByPage 
    * @Description: 分页查询客户信息列表
    * @param page 当前页
    * @param rows 每页显示条数
    * @param customerInfo 查询条件
    * @return    设定文件 
    * @return JSONObject  分页显示客户信息列表
    */
    @ApiOperation(value = "查询员工信息", httpMethod = "GET", response = JSONObject.class, notes = "查询员工信息")
    @RequestMapping("/getcustomerInfoByPage")
    @ResponseBody
    public JSONObject getcustomerInfoByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "customerInfo", value = "customerInfo,查询条件") CustomerInfo customerInfo) {
        JSONObject message = new JSONObject();
        try {
            String controller = GetRequestMappingName.getControllerName(CustomerInfoController.class);
            PageInfo<CustomerInfo> customerInfoByPage = customerInfoService.getCustomerInfoByPage(page, rows, customerInfo);
            BrUserVo user = commonController.getUserBySession();
            if (StringUtils.isNotEmpty(controller)) {
                Map<String, Object> operationByRole = brOperationService.getOperationByRole(user.getRoles(), controller);
                message.put("operationByRole", operationByRole);
            }
            message.put("data", customerInfoByPage);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapSuccess(message);
    }

    /** 
    * @Title: insertCustomerInfo 
    * @Description: 添加客户信息
    * @param customerInfo 客户信息对象
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "添加基本信息", httpMethod = "POST", response = JSONObject.class, notes = "添加基本信息")
    @RequestMapping(value = "/insertCustomerInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertCustomerInfo(@ApiParam(required = true, name = "customerInfo", value = "customerInfo,客户信息对象") CustomerInfo customerInfo) {
        JSONObject message = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            String customerInfoId = customerInfoService.insertCustomerInfo(customerInfo);

            Relationship relationship = new Relationship();
            if (customerInfoId.length() > 0) {
                relationship.setCustomerInfoId(customerInfoId);
                relationship.setStatus(0);
                relationship.setCreatetime(new Date());
                relationship.setEdittime(new Date());
            }
            int i = relationshipService.insertRelationship(relationship);
            jsonObject.put("i", i);
            array.add(jsonObject);
            //加载性别信息
            JSONObject sexListJson = new JSONObject();
            List<Dictsex> dictSexList = dicSexService.dictSexListByStatus();
            sexListJson.put("dictSexList", dictSexList);
            array.add(sexListJson);

            message.put("data", array);

            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateCustomerInfo 
    * @Description:  修改客户信息
    * @param customerInfo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改基本信息", httpMethod = "POST", response = JSONObject.class, notes = "修改基本信息")
    @RequestMapping(value = "/updateCustomerInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateCustomerInfo(@ApiParam(required = true, name = "customerInfo", value = "customerInfo,基本信息对象") CustomerInfo customerInfo) {
        JSONObject message = new JSONObject();
        try {
            customerInfo.setCustomerInfoEditTime(new Date());
            int i = customerInfoService.updateCustomerInfo(customerInfo);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getCustomerInfo 
    * @Description: 根据Id获取用户基本信息
    * @param customerInfoId 
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据Id获取用户基本信息", httpMethod = "GET", response = JSONObject.class, notes = "获取登录用户基本信息")
    @RequestMapping("/getCustomerInfo")
    @ResponseBody
    public JSONObject getCustomerInfo(@ApiParam(required = true, name = "customerInfoId", value = "customerInfoId,基本信息对象") String customerInfoId) {
        JSONObject message = new JSONObject();
        try {

            CustomerInfo customerInfo = customerInfoService.getCustomerInfo(customerInfoId);
            message.put("data", customerInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getCustomerInfoByInfo 用户名重名校验
    * @Description: 
    * @param customerInfoPhone 电话号码
    * @param customerInfoEmail 邮件
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "用户名重名校验", httpMethod = "GET", response = JSONObject.class, notes = "用户名重名校验")
    @RequestMapping("/getCustomerInfoByInfo")
    @ResponseBody
    public JSONObject getCustomerInfoByInfo(@ApiParam(required = true, name = "customerInfoPhone", value = "customerInfoPhone,注册手机号") String customerInfoPhone,
            @ApiParam(required = true, name = "customerInfoEmail", value = "customerInfoEmail,注册邮箱") String customerInfoEmail) {
        JSONObject message = new JSONObject();
        try {
            HttpSession session = request.getSession();
            CustomerRegistVo customerRegistVo = (CustomerRegistVo) session.getAttribute("loginUser");
            String customerInfoId = customerRegistVo.getCustomerInfoId();
            int i = customerInfoService.getCountByExample(customerInfoId, customerInfoPhone, customerInfoEmail);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /*
     * 删除关系成员
     * 
     */
    /** 
    * @Title: deleteCustomerInfo 
    * @Description: 删除客户信息
    * @param customerInfoId 
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除客户信息", httpMethod = "GET", response = JSONObject.class, notes = "删除客户信息")
    @RequestMapping("/deleteCustomerInfo")
    @ResponseBody
    public JSONObject deleteCustomerInfo(@ApiParam(required = true, name = "customerInfoId", value = "customerInfoId,客户信息Id") String customerInfoId) {
        JSONObject message = new JSONObject();
        try {
            CustomerInfo customerInfo = customerInfoService.getCustomerInfo(customerInfoId);
            customerInfo.setCustomerInfoStatus(1);
            int i = customerInfoService.updateCustomerInfo(customerInfo);
            if (i > 0) {
                Relationship relationShip = new Relationship();
                relationShip.setEdittime(new Date());
                relationShip.setCustomerInfoRelationId(customerInfoId);
                relationShip.setCustomerInfoId(customerInfo.getCustomerInfoId());
                relationShip.setStatus(1);
                i = relationshipService.updateByExampleSelective(relationShip);
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
