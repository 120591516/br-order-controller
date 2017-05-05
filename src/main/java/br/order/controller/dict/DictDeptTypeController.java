package br.order.controller.dict;

import java.util.Date;

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
import br.crm.common.utils.UUIDUtils;
import br.crm.pojo.dict.DictDeptType;
import br.crm.service.dict.DictDeptTypeService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;

/**
 * 
 * @ClassName: DictDeptTypeController
 * @Description: TODO(科室类型的相关维护)
 * @author adminis
 * @date 2016年11月10日 下午3:20:04
 *
 */
@Controller
@RequestMapping("/dictDeptTypeManage")
public class DictDeptTypeController {

    @Autowired
    private DictDeptTypeService dictDeptTypeServicel;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: addDeptType @Description: TODO(新增科室类型) @param @return
     *         设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "新增科室类型", httpMethod = "POST", response = JSONObject.class, notes = "新增科室类型")
    @RequestMapping("/addDeptType")
    @ResponseBody
    public JSONObject addDeptType(DictDeptType dictDeptType) {

        JSONObject message = new JSONObject();
        try {
            dictDeptType.setDictDeptTypeCreateTime(new Date());
            dictDeptType.setDictDeptTypeEditTime(dictDeptType.getDictDeptTypeCreateTime());
            dictDeptType.setDictDeptTypeStatus(0);
            dictDeptType.setDictDeptTypeId(UUIDUtils.getId());
            int id = dictDeptTypeServicel.addDeptType(dictDeptType);
            message.put("data", id);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: deleteDeptType @Description: TODO(删除科室类型) @param @param
     *         dictDeptTypeId @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "删除科室类型", httpMethod = "GET", response = JSONObject.class, notes = "删除科室类型")
    @RequestMapping("/deleteDeptType")
    @ResponseBody
    public JSONObject deleteDeptType(@ApiParam(required = true, name = "dictDeptTypeId", value = "dictDeptTypeId,科室类型id") String dictDeptTypeId) {

        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictDeptTypeId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictDeptTypeServicel.deleteDeptType(dictDeptTypeId);
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
     * @Title: updateDeptType @Description: TODO(修改科室类型) @param @param
     *         dictDeptType @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改科室类型", httpMethod = "POST", response = JSONObject.class, notes = "删除科室类型")
    @RequestMapping("/updateDeptType")
    @ResponseBody
    public JSONObject updateDeptType(DictDeptType dictDeptType) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictDeptType.getDictDeptTypeId())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            dictDeptType.setDictDeptTypeEditTime(new Date());
            dictDeptType.setDictDeptTypeStatus(0);
            int i = dictDeptTypeServicel.updateDeptType(dictDeptType);
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
     * @Title: getDeptTypeById @Description: TODO(根据ID查看科室类型) @param @return
     *         设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据ID查看科室类型", httpMethod = "GET", response = JSONObject.class, notes = "根据ID查看科室类型")
    @RequestMapping("/getDeptTypeById")
    @ResponseBody
    public JSONObject getDeptTypeById(@ApiParam(required = true, name = "dictDeptTypeId", value = "dictDeptTypeId,科室类型id") String dictDeptTypeId) {

        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictDeptTypeId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {

            DictDeptType dictDeptType = dictDeptTypeServicel.getDeptTypeById(dictDeptTypeId);
            message.put("data", dictDeptType);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: selectDeptTypeList @Description: TODO查看科室类型) @param @param page
     *         页码 @param @param rows 条数 @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "查看科室类型", httpMethod = "GET", response = JSONObject.class, notes = "查看科室类型")
    @RequestMapping("/selectDeptTypeList")
    @ResponseBody
    public JSONObject selectDeptTypeList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {

        JSONObject message = new JSONObject();
        try {
            PageInfo<DictDeptType> list = dictDeptTypeServicel.selectDeptTypeList(page, rows);
            //List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            //Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictDeptType");
            //message.put("operationList", operationList);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
