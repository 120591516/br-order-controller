package br.order.controller.permisson;

import java.util.Date;

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

import br.crm.pojo.permission.OrganizationUserManager;
import br.crm.service.permission.OrgUserManagerService;
import br.crm.vo.permission.OrganizationUserManagerVo;
import br.order.common.utils.InterfaceResultUtil;

/** 
* 
* @ClassName: OrgUserManagerController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 王文腾
* @date 2016年11月17日 上午10:05:15 
*/
@Controller
@RequestMapping("orgUserManager")
public class OrgUserManagerController {
    @Autowired
    private OrgUserManagerService orgUserManagerService;
    
    @ApiOperation(value="新增管理用户",httpMethod="POST",response=JSONObject.class,notes="新增管理用户")
    @RequestMapping(value="/insertOrgUserManager",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrgUserManager(@ApiParam(required=true,name="orgUserManager",value="orgUserManager,新增管理用户对象")OrganizationUserManager orgUserManager){
        JSONObject message = new JSONObject();
        try {
            int i =orgUserManagerService.insertOrgUserManager(orgUserManager);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }  @ApiOperation(value="分页获取管理员信息",httpMethod="GET",response=JSONObject.class,notes="新增管理用户")
    @RequestMapping(value="/getOrgUserManagerByPage")
    @ResponseBody
    public JSONObject getOrgUserManagerByPage(
            @ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required=true,name="userId",value="userId,用户id")String userId){
        JSONObject message = new JSONObject();
        try {
            PageInfo<OrganizationUserManagerVo> pageInfo=orgUserManagerService.getOrgUserManagerByPage(page, rows,userId);
            message.put("data", pageInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
    @ApiOperation(value="根据管理用户id查询用户对象",httpMethod="GET",response=JSONObject.class,notes="根据管理用户id查询用户对象")
    @RequestMapping(value="/getOrgUserManager")
    @ResponseBody
    public JSONObject getOrgUserManager(@ApiParam(required=true,name="userManagerId",value="orgUserManager,管理用户id")String userManagerId){
        JSONObject message = new JSONObject();
        try {
            OrganizationUserManager orgUserManager =orgUserManagerService.getOrgUserManager(userManagerId);
            message.put("data", orgUserManager);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
    @ApiOperation(value="修改管理用户id查询用户对象",httpMethod="POST",response=JSONObject.class,notes="修改管理用户id查询用户对象")
    @RequestMapping(value="/updateOrgUserManager",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject updateOrgUserManager(@ApiParam(required=true,name="orgUserManager",value="orgUserManager,新增管理用户对象")OrganizationUserManager orgUserManager){
        JSONObject message = new JSONObject();
        try {
            int i =orgUserManagerService.updateOrgUserManager(orgUserManager);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
    @ApiOperation(value="删除管理用户id查询用户对象",httpMethod="GET",response=JSONObject.class,notes="删除管理用户id查询用户对象")
    @RequestMapping(value="/deleteOrgUserManager")
    @ResponseBody
    public JSONObject deleteOrgUserManager(@ApiParam(required=true,name="userManagerId",value="orgUserManager,管理用户id")String userManagerId){
        JSONObject message = new JSONObject();
        try {
            OrganizationUserManager  orgUserManager =new OrganizationUserManager();
            orgUserManager.setUserManagerId(userManagerId);
            orgUserManager.setUserManagerEditTime(new Date());
            orgUserManager.setUserManagerStatus(1);
            int i =orgUserManagerService.updateOrgUserManager(orgUserManager);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
