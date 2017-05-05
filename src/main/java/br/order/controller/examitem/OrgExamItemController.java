package br.order.controller.examitem;

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

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.common.utils.StringTransCodeUtil;
import br.crm.pojo.examitem.OrganizationExamItem;
import br.crm.service.examitem.OrgExamItemService;
import br.crm.vo.examitem.OrgExamItemQu;
import br.crm.vo.examitemvalue.OrgExamItemVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/** 
* (体检项controller)
* @ClassName: OrgExamItemControlle r 
* @Description: TODO(体检项controller) 
* @author 王文腾
* @date 2016年9月13日 上午10:54:20 
*/
@Controller
@RequestMapping("/orgExamItem")
public class OrgExamItemController {

    /**
     * {体检项service}
     */
    @Autowired
    private OrgExamItemService orgExamItemService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getOrgExamItemByPage 
    * @Description: TODO(分页获取体检项列表) 
    * @param page 当前页数
    * @param rows 每页显示行数
    * @param orgExamItemQu 条件查询对象
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "分页获取检查项列表", httpMethod = "GET", response = JSONObject.class, notes = "分页获取检查项列表")
    @RequestMapping("/getOrgExamItemByPage")
    @ResponseBody
    public JSONObject getOrgExamItemByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "orgExamFeeItem", value = "orgExamFeeItem,条件查询对象") OrgExamItemQu orgExamItemQu) {
        JSONObject message = new JSONObject();
        try {
            orgExamItemQu = (OrgExamItemQu) StringTransCodeUtil.transCode(orgExamItemQu);
            PageInfo<OrgExamItemVo> pageInfo = orgExamItemService.getOrgExamItemByPage(orgExamItemQu, page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgExamItem");
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
    * @Title: getAllOrgExamItem 
    * @Description: TODO(获取体检项列表) 
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "获取体检项列表", httpMethod = "GET", response = JSONObject.class, notes = "获取体检项列表")
    @RequestMapping("/getAllOrgExamItem")
    @ResponseBody
    public JSONObject getAllOrgExamItem() {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationExamItem> list = orgExamItemService.getAllOrgExamItem();
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: insertOrgExamItem 
    * @Description: TODO(新增体检项) 
    * @param orgExamItem 体检项对象
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "新增体检项", httpMethod = "POST", response = JSONObject.class, notes = "新增体检项")
    @RequestMapping(value = "/insertOrgExamItem", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgExamItem(@ApiParam(required = true, name = "insertOrgExamItem", value = "insertOrgExamItem,新增体检项对象") OrganizationExamItem orgExamItem) {
        JSONObject message = new JSONObject();
        try {
            //插入数据        	   	
            int i = orgExamItemService.insertOrgExamItem(orgExamItem);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getOrgExamItemById 
    * @Description: TODO(根据id查询体检项信息) 
    * @param orgExamItemId 体检项id
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "根据id查询体检项信息", httpMethod = "GET", response = JSONObject.class, notes = "根据用户id查询体检项信息")
    @RequestMapping("/getOrgExamItemById")
    @ResponseBody
    public JSONObject getOrgExamItemById(@ApiParam(required = true, name = "orgExamItemId", value = "orgExamItemId,体检项id") String orgExamItemId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationExamItem orgExamItem = orgExamItemService.getOrgExamItemById(orgExamItemId);
            message.put("data", orgExamItem);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateOrgExamItem 
    * @Description: TODO(修改体检项) 
    * @param orgExamItem 体检项对象
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "修改体检项", httpMethod = "POST", response = JSONObject.class, notes = "修改体检项")
    @RequestMapping("/updateOrgExamItem")
    @ResponseBody
    public JSONObject updateOrgExamItem(@ApiParam(required = true, name = "orgExamItem", value = "orgExamItem,检查项对象") OrganizationExamItem orgExamItem) {
        JSONObject message = new JSONObject();
        try {
            if (null != orgExamItem) {
                if (StringUtils.isNotEmpty(orgExamItem.getExamItemTypeId()) && !orgExamItem.getExamItemTypeId().equals("-1")) {
                    int i = orgExamItemService.updateOrgExamItem(orgExamItem);
                    message.put("data", i);
                    return InterfaceResultUtil.getReturnMapSuccess(message);
                }
                else {
                    message.put("data", "请检查填写内容是否完整");
                    return InterfaceResultUtil.getReturnMapError(message);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteOrgExamItem 
    * @Description: TODO(deleteOrgExamItem) 
    * @param orgExamItemId 体检项id
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "删除体检项", httpMethod = "GET", response = JSONObject.class, notes = "删除体检项")
    @RequestMapping("/deleteOrgExamItem")
    @ResponseBody
    public JSONObject deleteOrgExamItem(@ApiParam(required = true, name = "orgExamItemId", value = "orgExamItemId,体检项id") String orgExamItemId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationExamItem orgExamItem = orgExamItemService.getOrgExamItemById(orgExamItemId);
            if (null != orgExamItem) {
                orgExamItem.setExamItemStatus(1);
                int i = orgExamItemService.countExamItemRelation(orgExamItemId);
                if (i < 0) {
                    message.put("data", "该项存在关联关系，尚不能删除");
                    return InterfaceResultUtil.getReturnMapError(message);
                }
                else {
                    i = orgExamItemService.updateOrgExamItem(orgExamItem);
                    message.put("data", i);
                    return InterfaceResultUtil.getReturnMapSuccess(message);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
     * @Title: getOrgExamItemByFeeItem 
     * @Description: TODO(查询指定收费项下的体检项) 
     * @param id  收费项id
     * @return JSONObject    
     * @throws 
     */
    @ApiOperation(value = "查询指定收费项下的体检项", httpMethod = "GET", response = JSONObject.class, notes = "查询指定收费项下的体检项")
    @RequestMapping("/getOrgExamItemByFeeItem")
    @ResponseBody
    public JSONObject getOrgExamItemByFeeItem(@ApiParam(required = true, name = "page", value = "page,当前页") Integer page, @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") Integer rows,
            @ApiParam(required = true, name = "id", value = "收费项id,id") String id) {
        JSONObject message = new JSONObject();
        try {
            if (id != null) {
                PageInfo<OrganizationExamItem> pageInfo = orgExamItemService.getExamItemByFeeItem(id, page, rows);
                message.put("data", pageInfo);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
