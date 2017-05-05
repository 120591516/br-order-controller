package br.order.controller.dept;

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

import br.crm.common.utils.CommonUtils;
import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dept.OrganizationDept;
import br.crm.service.dept.OrgDeptService;
import br.crm.vo.dept.OrganizationDeptVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrUserVo;

/**
 * 
 * @ClassName: OrgDeptController
 * @Description: 科室的相关接口实现
 * @author zxy
 * @date 2016年9月12日 上午11:03:18
 *
 */
@Controller
@RequestMapping("/OrgDept")
public class OrgDeptController {
    @Autowired
    private OrgDeptService orgDeptService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getOrgDeptByPage @Description: 分页查询部门科室 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @param organizationDeptQu
     *         查询条件 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询部门科室", httpMethod = "GET", response = JSONObject.class, notes = "分页查询部门科室列表")
    @RequestMapping("/getOrgDeptList")
    @ResponseBody
    public JSONObject getOrgDeptByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "organizationDeptQu", value = "organizationDeptQu,查询条件") OrganizationDeptVo organizationDeptVo) {
        JSONObject jsonObject = new JSONObject();
        try {
            PageInfo<OrganizationDeptVo> pageInfo = orgDeptService.getOrgDeptByCondition(page, rows, organizationDeptVo);
            BrUserVo user = commonController.getUserBySession();
            Map<String, Object> operationByRole = brOperationService.getOperationByRole(user.getRoles(), "orgBranchDept");
            jsonObject.put("operationByRole", operationByRole);
            jsonObject.put("data", pageInfo);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getOrgDeptAll @Description: 部门科室列表 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "部门科室列表", httpMethod = "GET", response = JSONObject.class, notes = "部门科室列表")
    @RequestMapping("/getOrgDeptAll")
    @ResponseBody
    public JSONObject getOrgDeptAll() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<OrganizationDept> list = orgDeptService.getOrgDeptAll();
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
     * @Title: addOrgDept @Description: 添加部门科室 @param @param dept
     *         部门科室对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加部门科室", httpMethod = "POST", response = JSONObject.class, notes = "添加部门科室")
    @RequestMapping("/addOrgDept")
    @ResponseBody
    public JSONObject addOrgDept(@ApiParam(required = true, name = "dept", value = "dept,部门科室对象") OrganizationDept dept) {
        JSONObject jsonObject = new JSONObject();
        try {
            int i = orgDeptService.addOrgDept(dept);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 数据反填
     * 
     * @return
     */
    @ApiOperation(value = "根据id获取对象信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象信息")
    @RequestMapping("/getOrgDeptById")
    @ResponseBody
    public JSONObject getOrgDeptById(@ApiParam(required = true, name = "pid", value = "pid,主键") String pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            OrganizationDeptVo dept = orgDeptService.getOrgDeptByPid(pid);
            jsonObject.put("data", dept);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: updateOrgDept @Description: 修改部门科室 @param @param dept
     *         部门科室对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改部门科室", httpMethod = "POST", response = JSONObject.class, notes = "修改部门科室")
    @RequestMapping("/updateOrgDept")
    @ResponseBody
    public JSONObject updateOrgDept(@ApiParam(required = true, name = "dept", value = "dept,部门科室对象") OrganizationDeptVo dept) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(dept.getOrgDeptId())) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            int i = orgDeptService.updateOrgDept(dept);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: deleteOrgDept @Description: 逻辑删除部门科室 @param @param pid
     *         修改主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "逻辑删除部门科室", httpMethod = "GET", response = JSONObject.class, notes = "逻辑删除部门科室")
    @RequestMapping("/deleteOrgDept")
    @ResponseBody
    public JSONObject deleteOrgDept(@ApiParam(required = true, name = "pid", value = "pid,修改主键") String pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            OrganizationDeptVo dept = orgDeptService.getOrgDeptByPid(pid);
            dept.setEdittime(new Date());
            dept.setStatus(1);
            int i = orgDeptService.updateOrgDept(dept);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getOrgDeptByOrgid @Description: 根据机构id获取对象列表 @param @param orgid
     *         机构id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据机构id获取对象列表", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id获取对象列表")
    @RequestMapping("/getOrgDeptByOrgid")
    @ResponseBody
    public JSONObject getOrgDeptByOrgid(@ApiParam(required = true, name = "orgid", value = "orgid,机构id") String orgid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(orgid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            List<OrganizationDept> dept = orgDeptService.getOrgDeptByOrgid(orgid);
            jsonObject.put("data", dept);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: getOrgDeptByBranid @Description: 根据门店id获取对象列表 @param @param
     *         branid 门店id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据门店id获取上级科室名称", httpMethod = "GET", response = JSONObject.class, notes = "根据门店id获取对象列表")
    @RequestMapping("/getOrgDeptByBranid")
    @ResponseBody
    public JSONObject getOrgDeptByBranid(@ApiParam(required = true, name = "branid", value = "branid,门店id") String branid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(branid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            List<Map<String, String>> list = orgDeptService.getDeptNameByBranchId(branid);
            jsonObject.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

}
