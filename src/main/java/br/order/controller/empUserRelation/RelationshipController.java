package br.order.controller.empUserRelation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.GetRequestMappingName;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.user.pojo.empUser.CustomerInfo;
import br.order.user.pojo.empUserRelation.Relationship;
import br.order.user.service.empUser.CustomerInfoService;
import br.order.user.service.empUser.CustomerRegistService;
import br.order.user.service.empUserRelation.RelationshipService;
import br.order.user.vo.empUser.CustomerRegistVo;
import br.order.user.vo.empUserRelation.RelationShipVo;
import br.order.vo.BrRoleVo;

/**
 * @ClassName: RelationshipController
 * @Description: 关系成员表
 * @author server
 * @date 2016年9月13日 下午3:27:24
 */
@Controller
@RequestMapping("/relationship")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustomerRegistService customerRegistService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getCustomerInfoByPage 
    * @Description: 分页查询家庭成员关系列表
    * @param page 当前页
    * @param rows 每页显示的行数
    * @param relationShipVo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据注册用户id查询家庭成员信息", httpMethod = "GET", response = JSONObject.class, notes = "根据登录用户查询家庭成员信息")
    @RequestMapping("/getCustomerInfoByPage")
    @ResponseBody
    public JSONObject getCustomerInfoByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "relationShipVo", value = "relationShipVo,查询条件对象") RelationShipVo relationShipVo) {
        JSONObject message = new JSONObject();
        try {
            String controller = GetRequestMappingName.getControllerName(RelationshipController.class);
            //查询家庭成员信息
            PageInfo<RelationShipVo> pageInfo = relationshipService.getRelationship(page, rows, relationShipVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            if (StringUtils.isNotEmpty(controller)) {

                Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, controller);
                message.put("operationList", operationList);
            }
            message.put("data", pageInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getRelationshipByID 
    * @Description: 根据id查询家庭成员的详细信息
    * @param relationshipId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据id查询家庭成员的详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询家庭成员的详细信息")
    @RequestMapping(value = "/getRelationshipByID", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRelationshipByID(@ApiParam(required = true, name = "relationshipId", value = "relationshipId,查询条件ID") String relationshipId) {
        JSONObject message = new JSONObject();
        try {
            RelationShipVo relationship = relationshipService.getRelationshipById(relationshipId);
            message.put("data", relationship);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: InsertRelationship 
    * @Description: 增加家庭关系成员的信息
    * @param customerInfo 用户信息对象
    * @param relationId 人际关系Id
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "增加家庭关系成员的信息", httpMethod = "POST", response = JSONObject.class, notes = "增加家庭关系成员的信息")
    @RequestMapping(value = "/InsertRelationship", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject InsertRelationship(@ApiParam(required = true, name = "CustomerInfo", value = "CustomerInfo,增加成员对象") CustomerInfo customerInfo,
            @ApiParam(required = true, name = "relationId", value = "relationId,关系字典表Id") String relationId,
            @ApiParam(required = true, name = "customerRegistId", value = "customerRegistId,注册用户Id") String customerRegistId) {
        JSONObject message = new JSONObject();
        try {
            //获取注册用的对象
            CustomerRegistVo customerRegist = customerRegistService.getCustomerRegist(customerRegistId);
            String insertCustomerInfo = customerInfoService.insertCustomerInfo(customerInfo);
            if (insertCustomerInfo != null) {
                if (relationId != null) {
                    Relationship relationship = new Relationship();
                    relationship.setDictRelationId(Long.valueOf(relationId));
                    //亲友用户id  新增加的用户信息
                    relationship.setCustomerInfoRelationId(insertCustomerInfo);
                    relationship.setCustomerInfoId(customerRegist.getCustomerInfoId());
                    relationship.setCreatetime(new Date());
                    relationship.setEdittime(relationship.getCreatetime());
                    relationship.setStatus(0);
                    relationshipService.insertRelationship(relationship);
                }

            }
            message.put("data", 1);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: UpdateRelationship 
    * @Description: 修改家庭成员的信息
    * @param customerInfo
    * @param relationId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改家庭关系成员的信息", httpMethod = "POST", response = JSONObject.class, notes = "修改家庭关系成员的信息")
    @RequestMapping(value = "/UpdateRelationship", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject UpdateRelationship(@ApiParam(required = true, name = "CustomerInfo", value = "CustomerInfo,修改成员对象") CustomerInfo customerInfo,
            @ApiParam(required = true, name = "relationshipId", value = "relationshipId,家庭关系表Id") Relationship relationship) {
        JSONObject message = new JSONObject();
        try {
            customerInfo.setCustomerInfoEditTime(new Date());
            int updateCustomerInfo = customerInfoService.updateCustomerInfo(customerInfo);
            if (updateCustomerInfo > 0) {
                Relationship relationship1 = new Relationship();
                relationship1.setRelationshipId(relationship.getRelationshipId());
                relationship1.setCustomerInfoRelationId(customerInfo.getCustomerInfoId());//修改家庭成员的关系
                relationship1.setEdittime(new Date());
                relationship1.setDictRelationId(Long.valueOf(relationship.getDictRelationId()));
                relationship1.setStatus(relationship.getStatus());
                int updateByPrimaryKeySelective = relationshipService.updateByPrimaryKeySelective(relationship1);
                message.put("data", updateByPrimaryKeySelective);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteRelationship 
    * @Description: 删除家庭关系信息
    * @param customerInfoId
    * @param relationId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除家庭关系成员的信息", httpMethod = "GET", response = JSONObject.class, notes = "删除家庭关系成员的信息")
    @RequestMapping(value = "/deleteRelationship", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteRelationship(@ApiParam(required = true, name = "relationship", value = "relationship,删除成员对象") String relationshipId) {
        JSONObject message = new JSONObject();
        try {
            Relationship relationship = relationshipService.getRelationship(relationshipId);
            relationship.setRelationshipId(relationship.getRelationshipId());
            relationship.setStatus(1);
            relationship.setEdittime(new Date());
            int updateByExampleSelective = relationshipService.updateByPrimaryKeySelective(relationship);

            message.put("data", updateByExampleSelective);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}