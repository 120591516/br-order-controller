package br.order.controller.org.user;

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
import com.github.pagehelper.StringUtil;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.CommonUtils;
import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.permission.OrganizationUser;
import br.crm.service.permission.OrganizationUserService;
import br.crm.service.permission.RoleManageCrmService;
import br.crm.service.permission.UserManageCrmService;
import br.crm.vo.permission.OrgUserVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: OrganizationUserController
 * @Description: 机构员工信息的相关维护
 * @author zxy
 * @date 2016年9月12日 上午10:52:09
 *
 */
@Controller
@RequestMapping("/organizationUser")
public class OrganizationUserController {

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Autowired
    private UserManageCrmService userManageCrmService;

    @Autowired
    private RoleManageCrmService roleManageCrmService;

    /**
     * 
     * @Title: getOrgUserByCondition @Description: 分页查询医生信息 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @param orgUserQu
     *         查询条件的对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询医生信息", httpMethod = "GET", response = JSONObject.class, notes = "分页查询医生信息列表")
    @RequestMapping("/getOrgUserList")
    @ResponseBody
    public JSONObject getOrgUserByCondition(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "orgUserVo", value = "orgUserVo,查询条件的对象") OrgUserVo orgUserVo) {
        JSONObject jsonObject = new JSONObject();
        try {
            PageInfo<OrgUserVo> orgUserByCondition = organizationUserService.getOrgUserByCondition(page, rows, orgUserVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgUser");
            jsonObject.put("operationList", operationList);
            jsonObject.put("data", orgUserByCondition);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getOrgUserAll @Description: 医生列表 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "医生列表", httpMethod = "GET", response = JSONObject.class, notes = "医生信息列表")
    @RequestMapping("/getOrgUserAll")
    @ResponseBody
    public JSONObject getOrgUserAll() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<OrganizationUser> list = organizationUserService.getOrgUserAll();
            jsonObject.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /** 
    * @Title: getOrgUserByDept 
    * @Description: TODO(获取科室下的医生列表) 
    * @return JSONObject    
    * @throws 
    */

    @ApiOperation(value = "医生列表", httpMethod = "GET", response = JSONObject.class, notes = "医生信息列表")
    @RequestMapping("/getOrgUserByDept")
    @ResponseBody
    public JSONObject getOrgUserByDept(@ApiParam(required = true, name = "orgBranchDeptId", value = "orgBranchDeptId,机构门店科室id") String orgBranchDeptId) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<OrganizationUser> list = organizationUserService.getOrgUserByDept(orgBranchDeptId);
            jsonObject.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: addOrgUser @Description: 添加医生信息 @param @param user
     *         医生信息对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加医生信息", httpMethod = "POST", response = JSONObject.class, notes = "添加医生信息")
    @RequestMapping("/addOrgUser")
    @ResponseBody
    public JSONObject addOrgUser(OrganizationUser user, String flag) {
        JSONObject jsonObject = new JSONObject();
        try {

            String userId = organizationUserService.addOrgUser(user);

            if ("admin".equals(flag)) {
                int roleID = roleManageCrmService.getRoleIdByName("超级管理员", user.getOrgId());
                userManageCrmService.insertUserRoleOne(roleID, user.getOrgId());
            }
            jsonObject.put("data", StringUtil.isEmpty(userId) ? 0 : 1);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getOrgUserById @Description: 根据id获取对象信息 @param @param pid
     *         主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    /**
     * @Title: getOrgUserById @Description: TODO(这里用一句话描述这个方法的作用) @param @param
     *         pid @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据id获取对象信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象信息")
    @RequestMapping("/getOrgUserById")
    @ResponseBody
    public JSONObject getOrgUserById(@ApiParam(required = true, name = "pid", value = "pid,主键") String pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            OrgUserVo user = organizationUserService.getOrgUserByPid(pid);
            jsonObject.put("data", user);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: updateOrgUser @Description: 修改医生信息 @param @param user
     *         医生信息对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改医生信息", httpMethod = "POST", response = JSONObject.class, notes = "修改医生信息")
    @RequestMapping("/updateOrgUser")
    @ResponseBody
    public JSONObject updateOrgUser(@ApiParam(required = true, name = "user", value = "user,医生信息对象") OrganizationUser user) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(user.getUserId())) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            int result = organizationUserService.updateOrgUser(user);
            jsonObject.put("data", result);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: deleteOrgUser @Description: 逻辑删除医生信息 @param @param pid
     *         修改主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "逻辑删除医生信息", httpMethod = "GET", response = JSONObject.class, notes = "逻辑删除医生信息")
    @RequestMapping("/deleteOrgUser")
    @ResponseBody
    public JSONObject deleteOrgUser(@ApiParam(required = true, name = "pid", value = "pid,修改主键") String pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            OrganizationUser user = organizationUserService.getOrgUser(pid);
            user.setUserCreateTime(new Date());
            user.setUserStatus(1);

            int result = organizationUserService.updateOrgUser(user);
            jsonObject.put("data", result);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getOrgUserByFid @Description: 根据id获取对象列表 @param @param fid
     *         科室id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据id获取对象列表", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象列表")
    @RequestMapping("/getOrgUserByFid")
    @ResponseBody
    public JSONObject getOrgUserByFid(@ApiParam(required = true, name = "fid", value = "fid,科室id") String fid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(fid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            List<OrganizationUser> user = organizationUserService.getOrgUserByFid(fid);
            jsonObject.put("data", user);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    @ApiOperation(value = "机构员工重置密码", httpMethod = "GET", response = JSONObject.class, notes = "机构员工重置密码")
    @RequestMapping("/resetOrgUserPwd")
    @ResponseBody
    public JSONObject resetOrgUserPwd(@ApiParam(required = true, name = "userId", value = "userId,机构医生Id") String userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            int i = organizationUserService.resetOrgUserPwd(userId);
            if (i > 0) {
                jsonObject.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
            }
            else {
                jsonObject.put("data", "输入的参数有误");
                return InterfaceResultUtil.getReturnMapError(jsonObject);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    @ApiOperation(value = "根据门店id获取可用医生(科室负责人)", httpMethod = "GET", response = JSONObject.class, notes = "根据门店id获取可用医生(科室负责人)")
    @RequestMapping("/getOrgUserByBranchId")
    @ResponseBody
    public JSONObject getOrgUserByBranchId(String orgId, String branchId) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Map<String, String>> list = organizationUserService.getOrgUserByBranchId(orgId, branchId);
            jsonObject.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }
}
