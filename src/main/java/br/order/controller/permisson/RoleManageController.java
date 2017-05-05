package br.order.controller.permisson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.CommonUtils;
import br.order.common.utils.InterfaceResultUtil;
import br.order.common.utils.JsonUtils;
import br.order.controller.common.CommonController;
import br.order.pojo.BrRole;
import br.order.pojo.BrRolePermission;
import br.order.pojo.BrUser;
import br.order.pojo.permission.PermissionResult;
import br.order.pojo.permission.RoleResult;
import br.order.service.BrOperationService;
import br.order.service.RoleManageService;
import br.order.vo.BrRoleVo;
import br.order.vo.PermissionVo;

/**
 * 角色Controller
 * @ClassName: RoleManageController
 * @Description: TODO(角色Controller)
 * @author adminis
 * @date 2016年9月12日 下午4:16:33
 *
 */
@Controller
@RequestMapping("/roleManage")
public class RoleManageController {

    /**
     * 角色Service
     */
    @Autowired
    private RoleManageService manageRoleService;

    /**
     * Session
     */
    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /**
     * 分页获取角色列表
     * @Title: getBrRoleList
     * @Description: TODO(分页获取角色列表)
     * @param @param page  当前页
     * @param @param rows  每页显示的条数
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "分页获取角色列表", httpMethod = "GET", response = JSONObject.class, notes = "分页获取角色列表")
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject getBrRoleList(@ApiParam(required = true, name = "page", value = "当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page, Integer rows) {
        JSONObject message = new JSONObject();
        if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
            message.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }

        RoleResult result = manageRoleService.getListBrRole(page, rows);
        if (null == result) {
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        String json = JsonUtils.objectToJson(result);
        List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
        Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "roleManage");
        message.put("operationList", operationList);
        message.put("data", JSONObject.parse(json));
        return InterfaceResultUtil.getReturnMapSuccess(message);
    }

    /**
     * 查询当前角色的id
     * @Title: getBrRoleById
     * @Description: TODO(查询当前角色的id)
     * @param @param id  角色ID
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "根据角色Id查询角色信息", httpMethod = "GET", response = JSONObject.class, notes = "根据角色Id查询角色信息")
    @RequestMapping("/getRoleOne")
    @ResponseBody
    public JSONObject getBrRoleById(@ApiParam(required = true, name = "id", value = "查询当前角色的id") @RequestParam(value = "id", required = true) Long id) {
        JSONObject message = new JSONObject();
        if (null == id || !CommonUtils.isNumber(id + "") || "".equals(id)) {
            message.put("message", "参数有误");
            return InterfaceResultUtil.getReturnMapValidValue(message);

        }
        BrRole brRole = manageRoleService.getBrRoleByRoleId(id);

        if (null == brRole) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        message.put("data", brRole);

        return InterfaceResultUtil.getReturnMapSuccess(message);

    }

    /**
     * 添加角色
     * @Title: addBrRole
     * @Description: TODO(添加角色)
     * @param @param brRole 角色对象
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "新增角色", httpMethod = "POST", response = JSONObject.class, notes = "新增角色")
    @RequestMapping("/addBrRole")
    @ResponseBody
    public JSONObject addBrRole(@ApiParam(required = true, name = "BrRole", value = "角色对象") BrRole brRole) {
        JSONObject message = new JSONObject();
        brRole.setRoleStatus(0);
        brRole.setRoleCreateTime(new Date());
        brRole.setRoleDescribe(null);
        try {
            int i = manageRoleService.addBrRole(brRole);

            message.put("data", i);

            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        message.put("msg", "添加失败");
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 修改角色
     * @Title: updateBrRole
     * @Description: TODO(修改角色)
     * @param @param brRole 角色对象
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "修改角色", httpMethod = "POST", response = JSONObject.class, notes = "修改角色")
    @RequestMapping("/updateBrRole")
    @ResponseBody
    public JSONObject updateBrRole(@ApiParam(required = true, name = "brRole", value = "修改角色对象") BrRole brRole) {

        JSONObject message = new JSONObject();
        try {
            if (StringUtils.isEmpty(brRole.getRoleName())) {
                return InterfaceResultUtil.getReturnMapError(message);
            }
            int i = manageRoleService.updateBrRoleByRoleId(brRole);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        message.put("msg", "修改失败");
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 根据角色Id添加权限
     * @Title: insertRolePermission
     * @Description: TODO(根据角色Id添加权限)
     * @param @param brRolePermission  权限对象
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "根据角色Id添加权限", httpMethod = "POST", response = JSONObject.class, notes = "根据角色Id添加权限")
    @RequestMapping("/insertRolePermission")
    @ResponseBody
    public JSONObject insertRolePermission(@ApiParam(required = true, name = "brRolePermission", value = "要添加的权限") List<BrRolePermission> brRolePermission) {

        JSONObject message = new JSONObject();
        try {
            int i = manageRoleService.insertUserRole(brRolePermission);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        message.put("msg", "添加失败");
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 查询出所有的权限
     * @Title: getRolePermissionById
     * @Description: TODO(查询出所有的权限)
     * @param @param RoleId  角色ID
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "查询出所有的权限", httpMethod = "GET", response = JSONObject.class, notes = "查询出所有的权限")
    @RequestMapping("/getRolePermissionById")
    @ResponseBody
    public JSONObject getRolePermissionById(@ApiParam(required = true, name = "id", value = "角色Id") @RequestParam(value = "id", required = true) Long RoleId) {
        JSONObject message = new JSONObject();

        if (null == RoleId || "".equals(RoleId)) {
            message.put("msg", "参数错误");
            return InterfaceResultUtil.getReturnMapError(message);

        }
        try {

            List<PermissionResult> list = manageRoleService.getRolePermissionById(RoleId);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        message.put("msg", "查询失败");
        return InterfaceResultUtil.getReturnMapError(message);

    }

    /**
     * 修改权限列表
     * @Title: updategetRolePermission
     * @Description: TODO(修改权限列表)
     * @param @param rolePermissonList  权限列表
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "修改权限列表", httpMethod = "POST", response = JSONObject.class, notes = "修改权限列表")
    @RequestMapping("/updategetRolePermission")
    @ResponseBody
    public JSONObject updategetRolePermission(@ApiParam(required = true, name = "rolePermissonList", value = "权限列表") List<BrRolePermission> rolePermissonList) {

        JSONObject message = new JSONObject();

        try {
            int i = manageRoleService.updategetRolePermission(rolePermissonList);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        message.put("msg", "修改失败");
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 删除角色
     * @Title: deleteBrUser
     * @Description: TODO(删除角色)
     * @param @param roleId  角色ID
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "删除角色", httpMethod = "POST", response = JSONObject.class, notes = "删除角色")
    @RequestMapping("/deleteBrRole")
    @ResponseBody
    public JSONObject deleteBrUser(@ApiParam(required = true, name = "roleId", value = "角色id") Long roleId) {
        JSONObject message = new JSONObject();
        try {

            BrRole brRole = new BrRole();
            brRole.setRoleId(roleId);
            brRole.setRoleStatus(1);
            int i = manageRoleService.updateBrRoleByRoleId(brRole);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 根据角色Id查询所有可用权限和操作
     * @Title: getPermissionAndOperationByIdAndOpen
     * @Description: TODO(根据角色Id查询所有可用权限和操作)
     * @param @param roleId 角色ID
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "根据角色Id查询所有可用权限和操作", httpMethod = "GET", response = JSONObject.class, notes = "根据角色Id查询所有可用权限和操作")
    @RequestMapping("/getPermissionAndOperationByIdAndOpen")
    @ResponseBody
    public JSONObject getPermissionAndOperationByIdAndOpen(@ApiParam(required = true, name = "roleId", value = "roleId,查询当前角色的id") @RequestParam(value = "roleId", required = true) Long roleId) {
        JSONObject message = new JSONObject();
        if (null == roleId || "".equals(roleId)) {
            message.put("msg", "参数错误");
            return InterfaceResultUtil.getReturnMapError(message);

        }
        try {
            List<PermissionVo> list = manageRoleService.getPermissionAndOperationByIdAndOpen(roleId);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 保存修改好的权限与操作
     * @Title: savePermission
     * @Description: TODO(保存修改好的权限与操作)
     * @param @param roleId 角色ID
     * @param @param indexData 个级菜单索引
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @ApiOperation(value = "保存修改好的权限与操作", httpMethod = "GET", response = JSONObject.class, notes = "保存修改好的权限与操作")
    @RequestMapping("/savePermission")
    @ResponseBody
    public JSONObject savePermission(@ApiParam(required = true, name = "roleId", value = "roleId,查询当前角色的id") @RequestParam(value = "roleId", required = true) Long roleId,
            @ApiParam(required = true, name = "indexData", value = "indexData,个级菜单索引") String indexData) {
        JSONObject message = new JSONObject();
        BrUser brUser = commonController.getUserBySession();
        String[] str = indexData.split(",");
        // 一级菜单
        List<Long> firstIndex = new ArrayList<Long>();
        // 三级菜单
        List<Long> opeIndex = new ArrayList<Long>();
        // 三级
        for (String first : str) {
            if (first.contains(".")) {
                String[] str2 = first.split("\\.");
                opeIndex.add(Long.valueOf(str2[0]));
            }
            else {
                firstIndex.add(Long.valueOf(first));
            }
        }
        try {
            // 调用Service
            int i = manageRoleService.savePermission(roleId, firstIndex, opeIndex, brUser);

            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(message);
    }

}