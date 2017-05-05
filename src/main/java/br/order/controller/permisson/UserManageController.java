package br.order.controller.permisson;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
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

import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.pojo.BrUser;
import br.order.pojo.permission.RoleVo;
import br.order.service.BrOperationService;
import br.order.service.UserManageService;
import br.order.service.UserPermissionService;
import br.order.vo.BrRoleVo;
import br.order.vo.BrUserVo;
import br.order.vo.UserPermissionVo;
import br.order.vo.UserRoleVo;

/**
 * @ClassName: UserManageController
 * @Description: 用户信息controller
 * @author admin
 * @date 2016年9月12日 下午4:53:13
 */
@Controller
@RequestMapping("/userManage")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private BrOperationService brOperationService;

    /**
     * Session
     */
    @Autowired
    private CommonController commonController;

    /*其中@ApiOperation和@ApiParam为添加的API相关注解，个参数说明如下：
    @ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”；其他参数可参考源码；
    @ApiParam(required = “是否必须参数”, name = “参数名称”, value = “参数具体描述”*/

    /** 
    * @Title: getUserByPage 
    * @Description: 分页获取用户列表
    * @param page
    * @param rows
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "分页获取用户列表", httpMethod = "GET", response = JSONObject.class, notes = "分页获取用户列表")
    @RequestMapping("/getUserByPage")
    @ResponseBody
    public JSONObject getUserByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<UserRoleVo> pageInfo = userManageService.getUserByPage(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "userManage");
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
    * @Title: insertBrUser 
    * @Description: 新增用户
    * @param brUser
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增用户", httpMethod = "POST", response = JSONObject.class, notes = "新增用户")
    @RequestMapping(value = "/insertBrUser", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertBrUser(@ApiParam(required = true, name = "brUser", value = "brUser,新增用户对象") BrUser brUser) {
        JSONObject message = new JSONObject();
        try {
            BrUserVo user = commonController.getUserBySession();
            brUser.setUserEditId(user.getUserId());
            brUser.setUserEditName(user.getUserName());
            brUser.setUserStatus(0);
            brUser.setUserCreateTime(new Date());
            brUser.setUserEditTime(brUser.getUserCreateTime());
            brUser.setUserLoginTime(new Date());
            brUser.setUserLastTime(new Date());
            //密码加密：1.加盐，2:两次散列算法
            String salt = brUser.getUserLoginName();
            Md5Hash md5 = new Md5Hash("88888888", salt, 2);
            brUser.setUserPassword(md5.toString());
            //插入数据
            int i = userManageService.insertBrUser(brUser);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getBrUserById 
    * @Description: 根据用户id查询用户信息
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据用户id查询用户信息", httpMethod = "GET", response = JSONObject.class, notes = "根据用户id查询用户信息")
    @RequestMapping("/getBrUserById")
    @ResponseBody
    public JSONObject getBrUserById(@ApiParam(required = true, name = "userId", value = "userId,用户id") Long userId) {
        JSONObject message = new JSONObject();
        try {
            BrUser brUser = userManageService.getBrUserById(userId);
            message.put("data", brUser);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getCountByUserName 
    * @Description: 用户重名校验
    * @param userName
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "用户重名校验", httpMethod = "GET", response = JSONObject.class, notes = "用户重名校验")
    @RequestMapping("/getCountByUserName")
    @ResponseBody
    public JSONObject getCountByUserName(@ApiParam(required = true, name = "userName", value = "userName,用户名") String userName,
            @ApiParam(required = false, name = "userId", value = "userId,用户id") Long userId) {
        JSONObject message = new JSONObject();
        try {
            int i = userManageService.getCountByUserName(userName, userId);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateBrUser 
    * @Description: 修改用户
    * @param brUser
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改用户", httpMethod = "POST", response = JSONObject.class, notes = "修改用户")
    @RequestMapping("/updateBrUser")
    @ResponseBody
    public JSONObject updateBrUser(@ApiParam(required = true, name = "brUser", value = "brUser,修改用户对象") BrUser brUser) {
        JSONObject message = new JSONObject();
        try {
            BrUserVo user = commonController.getUserBySession();
            brUser.setUserEditId(user.getUserId());
            brUser.setUserEditName(user.getUserLoginName());
            brUser.setUserEditTime(new Date());
            brUser.setUserLoginName(null);
            int i = userManageService.updateBrUser(brUser);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteBrUser 
    * @Description: 删除用户
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除用户", httpMethod = "GET", response = JSONObject.class, notes = "删除用户")
    @RequestMapping("/deleteBrUser")
    @ResponseBody
    public JSONObject deleteBrUser(@ApiParam(required = true, name = "userId", value = "userId,用户id") Long userId) {
        JSONObject message = new JSONObject();
        try {
            BrUserVo user = commonController.getUserBySession();
            BrUser brUser = new BrUser();
            brUser.setUserId(userId);
            brUser.setUserStatus(1);
            brUser.setUserEditId(user.getUserId());
            brUser.setUserEditName(user.getUserLoginName());
            brUser.setUserEditTime(new Date());
            int i = userManageService.updateBrUser(brUser);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getRolesByUserId 
    * @Description: 根据用户id获取角色列表
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "根据用户id获取角色列表", httpMethod = "GET", response = JSONObject.class, notes = "根据用户id获取角色列表")
    @RequestMapping("/getRolesByUserId")
    @ResponseBody
    public JSONObject getRolesByUserId(@ApiParam(required = true, name = "id", value = "userId,所选用户Id") Long userId) {
        JSONObject message = new JSONObject();
        try {
            if (userId != null && !userId.equals("")) {
                List<RoleVo> list = userManageService.getRolesByUserId(userId);
                message.put("data", list);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: insertUserRole 
    * @Description: 新增用户角色
    * @param strRolesId
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "新增用户角色", httpMethod = "POST", response = JSONObject.class, notes = "新增用户角色")
    @ResponseBody
    @RequestMapping("/insertUserRole")
    public JSONObject insertUserRole(@ApiParam(required = true, name = "strRolesId", value = "strRolesId,新增用户的角色id字符串") String strRolesId,
            @ApiParam(required = true, name = "userId", value = "userId,需要新增角色的用户id") Long userId) {
        JSONObject message = new JSONObject();
        try {
            int i = userManageService.insertUserRole(strRolesId, userId);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateUserRole 
    * @Description: 修改用户角色
    * @param strRolesId
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改用户角色", httpMethod = "POST", response = JSONObject.class, notes = "修改用户角色")
    @ResponseBody
    @RequestMapping("/updateUserRole")
    public JSONObject updateUserRole(@ApiParam(required = true, name = "strRolesId", value = "strRolesId,修改用户的角色id字符串") String strRolesId,
            @ApiParam(required = true, name = "userId", value = "userId,需要修改角色的用户id") Long userId) {
        JSONObject message = new JSONObject();
        try {
            int i = userManageService.updateUserRole(strRolesId, userId);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getUserPermission 
    * @Description: 获取用户权限列表
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "获取用户权限列表", httpMethod = "GET", response = JSONObject.class, notes = "获取用户权限列表")
    @ResponseBody
    @RequestMapping("/getUserPermission")
    public JSONObject getUserPermission() {
        JSONObject message = new JSONObject();
        try {
            Long userId = null;
            userId = commonController.getUserBySession().getUserId();
            System.out.println("userID:" + userId);
            List<UserPermissionVo> userPermissions = userPermissionService.getUserPermission(userId);
            UserRoleVo userRoleVo = userManageService.getUserRoleById(userId);
            JSONObject permissions = new JSONObject();
            permissions.put("permissions", userPermissions);
            message.put("userRole", userRoleVo);
            message.put("data", permissions);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: resetPassWord 
    * @Description: 重置用户密码
    * @param userId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "重置用户密码", httpMethod = "GET", response = JSONObject.class, notes = "重置用户密码")
    @ResponseBody
    @RequestMapping("/resetPassWord")
    public JSONObject resetPassWord(@ApiParam(required = true, name = "userId", value = "userId,修改用户的角色id字符串") Long userId) {
        JSONObject message = new JSONObject();
        try {
            BrUserVo user = commonController.getUserBySession();
            BrUser brUser = userManageService.getBrUserById(userId);
            //密码加密：1.加盐，2:两次散列算法
            String salt = brUser.getUserLoginName();
            Md5Hash md5 = new Md5Hash("88888888", salt, 2);
            brUser.setUserPassword(md5.toString());
            brUser.setUserEditId(user.getUserId());
            brUser.setUserEditName(user.getUserLoginName());
            brUser.setUserEditTime(new Date());
            int i = userManageService.updateBrUser(brUser);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /** 
    * @Title: editPassWord 
    * @Description: 修改密码
    * @param oldPwd
    * @param newPwd
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改密码", httpMethod = "POST", response = JSONObject.class, notes = "修改密码")
    @ResponseBody
    @RequestMapping("/editPassWord")
    public JSONObject editPassWord(@ApiParam(required = true, name = "oldPwd", value = "oldPwd,旧密码") @RequestParam("oldPwd") String oldPwd,
            @ApiParam(required = true, name = "newPwd", value = "newPwd,新密码") @RequestParam("newPwd") String newPwd) {
        JSONObject message = new JSONObject();
        try {
            BrUserVo user = commonController.getUserBySession();
            BrUser brUser = userManageService.getBrUserById(user.getUserId());
            //密码加密：1.加盐，2:两次散列算法
            String salt = brUser.getUserLoginName();
            Md5Hash md5 = new Md5Hash(oldPwd, salt, 2);
            if (brUser.getUserPassword().equals(md5.toString())) {
                Md5Hash md5New = new Md5Hash(newPwd, salt, 2);
                brUser.setUserPassword(md5New.toString());
                brUser.setUserEditId(user.getUserId());
                brUser.setUserEditName(user.getUserLoginName());
                brUser.setUserEditTime(new Date());
                int i = userManageService.updateBrUser(brUser);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
            else {
                message.put("data", "原密码输入错误");
                return InterfaceResultUtil.getReturnMapError(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);

    }

    @ApiOperation(value = "查看个人信息", httpMethod = "get", response = JSONObject.class, notes = "查看个人信息")
    @ResponseBody
    @RequestMapping("/personalInfor")
    public JSONObject getPersonalInfor() {
        JSONObject message = new JSONObject();
        try {
            BrUserVo user = commonController.getUserBySession();
            if (null != user) {
                BrUser brUser = userManageService.getBrUserById(user.getUserId());
                brUser.setUserPassword(null);
                message.put("data", brUser);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
