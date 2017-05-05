package br.order.controller.dict;

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
import br.crm.pojo.dict.Dictreceipttype;
import br.crm.service.dict.DictreceipttypeService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictreceipttypeController
 * @Description: 发票类型字典表相关信息维护
 * @author zxy
 * @date 2016年9月12日 下午1:53:18
 *
 */
@Controller
@RequestMapping("/dictreceipttype")
public class DictreceipttypeController {
    @Autowired
    private DictreceipttypeService dictreceipttypeservice;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getDictreceipttypeList @Description: 分页查询发票类型列表 @param @param
     *         page 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询发票类型列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询发票类型列表")
    @RequestMapping("/getDictreceipttypeList")
    @ResponseBody
    public JSONObject getDictreceipttypeList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject jsonObject = new JSONObject();
        try {
            PageInfo<Dictreceipttype> dictreceipttypeByPage = dictreceipttypeservice.getDictreceipttypeByPage(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictreceipttype");
            jsonObject.put("operationList", operationList);
            jsonObject.put("data", dictreceipttypeByPage);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: addDictreceipttype @Description: 添加发票类型 @param @param
     *         dictreceipttype 发票类型对象 @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "添加发票类型", httpMethod = "POST", response = JSONObject.class, notes = "添加发票类型")
    @RequestMapping("/addDictreceipttype")
    @ResponseBody
    public JSONObject addDictreceipttype(@ApiParam(required = true, name = "dictreceipttype", value = "dictreceipttype,发票类型对象") Dictreceipttype dictreceipttype) {
        JSONObject jsonObject = new JSONObject();
        dictreceipttype.setReceipttypeCreatetime(new Date());
        dictreceipttype.setReceipttypeUpdatetime(dictreceipttype.getReceipttypeCreatetime());
        dictreceipttype.setReceipttypeStatus(0);
        try {
            int i = dictreceipttypeservice.addDictreceipttype(dictreceipttype);
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
     * @Title: getDictreceipttypeById @Description: 根据id获取对象信息 @param @param pid
     *         主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据id获取对象信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象信息")
    @RequestMapping("/getDictreceipttypeById")
    @ResponseBody
    public JSONObject getDictreceipttypeById(@ApiParam(required = true, name = "pid", value = "pid,主键") Long pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            Dictreceipttype dictreceipttype = dictreceipttypeservice.selectByPrimaryKey(pid);
            jsonObject.put("data", dictreceipttype);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: updateDictreceipttype @Description: 修改发票类型 @param @param
     *         dictreceipttype 发票类型对象 @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "修改发票类型", httpMethod = "POST", response = JSONObject.class, notes = "修改发票类型")
    @RequestMapping("/updateDictreceipttype")
    @ResponseBody
    public JSONObject updateDictreceipttype(@ApiParam(required = true, name = "dictreceipttype", value = "dictreceipttype,发票类型对象") Dictreceipttype dictreceipttype) {
        JSONObject jsonObject = new JSONObject();
        dictreceipttype.setReceipttypeCreatetime(new Date());
        if (CommonUtils.isEmpty(dictreceipttype.getIdReceipttype())) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            int i = dictreceipttypeservice.updateDictreceipttype(dictreceipttype);
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
     * @Title: deleteDictreceipttype @Description: 逻辑删除发票类型 @param @param pid
     *         修改主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "逻辑删除发票类型", httpMethod = "GET", response = JSONObject.class, notes = "逻辑删除发票类型")
    @RequestMapping("/deleteDictreceipttype")
    @ResponseBody
    public JSONObject deleteDictreceipttype(@ApiParam(required = true, name = "pid", value = "pid,修改主键") Long pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            Dictreceipttype dictreceipttype = dictreceipttypeservice.selectByPrimaryKey(pid);
            dictreceipttype.setReceipttypeUpdatetime(new Date());
            dictreceipttype.setReceipttypeStatus(1);

            int i = dictreceipttypeservice.updateDictreceipttype(dictreceipttype);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

}
