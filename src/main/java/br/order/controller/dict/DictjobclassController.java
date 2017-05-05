package br.order.controller.dict;

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
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictjobclass;
import br.crm.service.dict.DictJobClassService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictjobclassController
 * @Description: 工作类型字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午3:36:42
 *
 */
@Controller
@RequestMapping("/jobclassManage")
public class DictjobclassController {

    @Autowired
    private DictJobClassService dictJobClassService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getJobClassList @Description: 分页查询工作类型 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询工作类型", httpMethod = "GET", response = JSONObject.class, notes = "分页查询工作类型")
    @RequestMapping("/getJobClassList")
    @ResponseBody
    public JSONObject getJobClassList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        if (null == page || null == rows || "".equals(rows) || "".equals(page)) {
            message.put("message", "页码为空");
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            PageInfo<Dictjobclass> pageInfo = dictJobClassService.getJobClassList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "jobclassManage");
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
     * 
     * @Title: addJobClass @Description: 新增工作类型 @param @param dictjobclass
     *         新增工作类型对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "新增工作类型", httpMethod = "POST", response = JSONObject.class, notes = "新增工作类型")
    @RequestMapping("/addJobClass")
    @ResponseBody
    public JSONObject addJobClass(@ApiParam(required = true, name = "dictjobclass", value = "新增工作类型对象") Dictjobclass dictjobclass) {

        JSONObject message = new JSONObject();
        dictjobclass.setJobclassCreatetime(new Date());
        dictjobclass.setJobclassUpdatetime(dictjobclass.getJobclassCreatetime());
        dictjobclass.setJobclassStatus(0);
        try {
            int i = dictJobClassService.addJobClass(dictjobclass);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: getJobClassById @Description: 根据工作类型Id查询详细信息 @param @param
     *         idJobclass 工作类型Id @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "根据工作类型Id查询详细信息", httpMethod = "GET", response = JSONObject.class, notes = "根据工作类型Id查询详细信息")
    @RequestMapping("/getJobClassById")
    @ResponseBody
    public JSONObject getJobClassById(@ApiParam(required = true, name = "idJobclass", value = "工作类型Id") Long idJobclass) {
        JSONObject message = new JSONObject();
        if (null == idJobclass || "".equals(idJobclass)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictjobclass dictjobclass = dictJobClassService.getJobClassById(idJobclass);
            message.put("data", dictjobclass);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateJobClass @Description: 修改工作类型信息 @param @param dictjobclass
     *         修改教育对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改工作类型信息", httpMethod = "POST", response = JSONObject.class, notes = "修改工作类型信息")
    @RequestMapping("/updateJobClass")
    @ResponseBody
    public JSONObject updateJobClass(@ApiParam(required = true, name = "dicteducation", value = "修改教育对象") Dictjobclass dictjobclass) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictjobclass.getJobclassName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictJobClassService.updateJobClass(dictjobclass);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: deleteJobClass @Description: 删除工作类型信息 @param @param idJobclass
     *         工作类型id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除工作类型信息", httpMethod = "GET", response = JSONObject.class, notes = "删除工作类型信息")
    @RequestMapping("/deleteJobClass")
    @ResponseBody
    public JSONObject deleteJobClass(@ApiParam(required = true, name = "idJobclass", value = "工作类型id") Long idJobclass) {
        JSONObject message = new JSONObject();
        if (null == idJobclass || "".equals(idJobclass)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictjobclass dictjobclass = dictJobClassService.getJobClassById(idJobclass);
            dictjobclass.setJobclassStatus(1);
            int i = dictJobClassService.updateJobClass(dictjobclass);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
