package br.order.controller.empComDept;

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
import br.order.user.pojo.empComDept.EnterpriseDep;
import br.order.user.pojo.empComUser.EnterpriseEmp;
import br.order.user.service.empComDept.EnterpriseDepService;
import br.order.user.service.empComUser.EnterpriseEmpService;
import br.order.user.vo.empComDept.EnterpriseDepVo;
import br.order.vo.BrRoleVo;
import br.order.vo.BrUserVo;

/**
 * @ClassName: EnterpriseDeptController
 * @Description: 部门表
 * @author server
 * @date 2016年9月13日 下午3:12:29
 */
/**
 * @ClassName: EnterpriseDeptController
 * @Description: TODO
 * @author server
 * @date 2016年9月13日 下午3:19:17
 */
@Controller
@RequestMapping("/enterpriseDep")
public class EnterpriseDeptController {

    @Autowired
    private EnterpriseDepService enterpriseDepService;

    @Autowired
    private EnterpriseEmpService enterpriseEmpService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getEnterpriseByPage 
    * @Description: 分页查询企业下部门的列表
    * @param page 当前页数
    * @param rows 每页显示行数
    * @param enterpriseDepVo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "分页查询企业下部门的列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询企业下部门的列表")
    @RequestMapping("/getEnterpriseDepByPage")
    @ResponseBody
    public JSONObject getEnterpriseByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "EnterpriseDepVo", value = "EnterpriseDepVo,查询条件") EnterpriseDepVo enterpriseDepVo) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<EnterpriseDepVo> allEnterpriseDep = enterpriseDepService.getAllEnterpriseDep(page, rows, enterpriseDepVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "enterpriseDept");
            message.put("operationList", operationList);
            message.put("data", allEnterpriseDep);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: getValidEnterpriseDep 
    * @Description: 查询部门的有效信息
    * @param enterpriseDepId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "查询部门的有效信息", httpMethod = "GET", response = JSONObject.class, notes = "查询企业信息")
    @RequestMapping("/getValidEnterpriseDep")
    @ResponseBody
    public JSONObject getValidEnterpriseDep() {
        JSONObject message = new JSONObject();
        try {
            List<EnterpriseDep> validEnterpriseDep = enterpriseDepService.getValidEnterpriseDep();
            message.put("data", validEnterpriseDep);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
     * @Title: getEnterpriseById 
     * @Description: 根据id查询部门信息
     * @param enterpriseDepId
     * @return    设定文件 
     * @return JSONObject    返回类型 
     */
    @ApiOperation(value = "根据id查询部门信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id查询部门信息")
    @RequestMapping("/getEnterpriseDepById")
    @ResponseBody
    public JSONObject getEnterpriseById(@ApiParam(required = true, name = "enterpriseDepId", value = "enterpriseDepId,所属企业id") String enterpriseDepId) {
        JSONObject message = new JSONObject();
        try {
            EnterpriseDep enterpriseDep = enterpriseDepService.getEnterpriseDepById(enterpriseDepId);
            message.put("data", enterpriseDep);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: insertEnterpriseDep 
    * @Description: 添加企业部门信息
    * @param enterpriseDep
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "添加企业部门信息", httpMethod = "POST", response = JSONObject.class, notes = "添加企业部门信息")
    @RequestMapping(value = "/insertEnterpriseDep", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertEnterpriseDep(@ApiParam(required = true, name = "enterpriseDep", value = "enterpriseDep,部门信息对象") EnterpriseDep enterpriseDep) {
        JSONObject message = new JSONObject();
        try {      
        	BrUserVo userBySession = commonController.getUserBySession();
        	enterpriseDep.setEnterpriseDepEditId((userBySession.getUserId()).toString());
            int i = enterpriseDepService.insertEnterpriseDep(enterpriseDep);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: updateEnterpriseDep 
    * @Description: 修改企业的信息
    * @param enterpriseDep
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "修改企业部门信息", httpMethod = "POST", response = JSONObject.class, notes = "修改企业部门信息")
    @RequestMapping(value = "/updateEnterpriseDep", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateEnterpriseDep(@ApiParam(required = true, name = "enterpriseDep", value = "enterpriseDep,部门信息对象") EnterpriseDep enterpriseDep) {
        JSONObject message = new JSONObject();
        try {
        	if(enterpriseDep.getEnterpriseDepId()==null&&"".equals(enterpriseDep.getEnterpriseDepId())){
        		return InterfaceResultUtil.getReturnMapValidValue(message);
        	}
            enterpriseDep.setEnterpriseDepEditTime(new Date());
            int i = enterpriseDepService.updateEnterpriseDep(enterpriseDep);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /** 
    * @Title: deleteEnterpriseDep 
    * @Description: 删除企业部门的信息
    * @param enterpriseDepId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "删除企业部门信息", httpMethod = "get", response = JSONObject.class, notes = "删除企业部门信息")
    @RequestMapping(value = "/deleteEnterpriseDep", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteEnterpriseDep(@ApiParam(required = true, name = "enterpriseDep", value = "enterpriseDep,部门信息对象") String enterpriseDepId) {
        JSONObject message = new JSONObject();
        try {
            EnterpriseDep enterpriseDep = enterpriseDepService.getEnterpriseDepById(enterpriseDepId);
            enterpriseDep.setEnterpriseDepStatus(1);
            enterpriseDep.setEnterpriseDepEditTime(new Date());
            int updateEnterpriseDep = enterpriseDepService.updateEnterpriseDep(enterpriseDep);
            //逻辑删除部门信息
            //根据部门id，把所属部门下的员工的状态改为1
            List<EnterpriseEmp> enterpriseEmpList = enterpriseEmpService.getEnterpriseEmpByDeptId(enterpriseDepId);
            for (EnterpriseEmp enterpriseEmp : enterpriseEmpList) {
                enterpriseEmp.setStatus(1);
            }
            message.put("data", updateEnterpriseDep);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
