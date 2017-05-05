package br.order.controller.emp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.Organization;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.pojo.emp.Employee;
import br.order.service.emp.EmpService;

/**
 * @ClassName: EmpController
 * @Description: 员工信息controller
 * @author admin
 * @date 2016年9月12日 下午4:44:41
 */
@Controller
@RequestMapping("/emp")
public class EmpController {
	@Autowired
	private EmpService empService;
	@Autowired
	private CommonController commonController;
	
	//表单提交中时间转换方法
		@InitBinder    
		public void initBinder(WebDataBinder binder) {    
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
		        dateFormat.setLenient(false);    
		        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
		} 
	
	/** 
	* @Title: getEmpByUserId 
	* @Description: 根据用户id查询员工信息
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="查询员工信息",httpMethod="GET",response=JSONObject.class,notes="根据用户id查询员工信息")
	@RequestMapping("/getEmpInfo")
	@ResponseBody
	public JSONObject getEmpByUserId(){
		JSONObject message = new JSONObject();
		try {
			Employee emp = empService.getEmpByUserId(commonController.getUserBySession().getUserId());
			message.put("data", emp);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
	/** 
	* @Title: updateEmp 
	* @Description:修改员工信息 
	* @param employee
	* @return    设定文件 
	* @return JSONObject    返回类型 
	*/
	@ApiOperation(value="修改员工信息",httpMethod="POST",response=JSONObject.class,notes="修改员工信息")
	@RequestMapping("/updateEmp")
	@ResponseBody
	public JSONObject updateEmp(@ApiParam(required=true,name="employee",value="employee,修改员工对象")Employee employee){
		JSONObject message = new JSONObject();
		try {
			employee.setEmpEditId(commonController.getUserBySession().getUserId());
			employee.setEmpEditName(commonController.getUserBySession().getUserLoginName());
			employee.setEmpEditTime(new Date());
			int i = empService.updateEmp(employee);
			message.put("data", i);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}
}
