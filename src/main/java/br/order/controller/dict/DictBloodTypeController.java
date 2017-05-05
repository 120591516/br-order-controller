package br.order.controller.dict;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.common.utils.InterfaceResultUtil;
import br.crm.pojo.dict.Dictbloodtype;
import br.crm.service.dict.DictBloodTypeService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictBloodTypeController
 * @Description: 血型字典表的信息相关维护
 * @author zxy
 * @date 2016年9月12日 上午11:44:41
 *
 */
@Controller
@RequestMapping("/bloodTypeManage")
public class DictBloodTypeController {

    @Autowired
    private DictBloodTypeService dictBloodTypeService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getAllBloodType @Description: 查询所有的血型 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "查询所有的血型", httpMethod = "GET", response = JSONObject.class, notes = "查询所有的血型")
    @RequestMapping("/getAllBloodType")
    @ResponseBody
    public JSONObject getAllBloodType() {

        JSONObject message = new JSONObject();
        try {

            List<Dictbloodtype> list = dictBloodTypeService.getAllBloodType();
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "bloodTypeManage");
            message.put("operationList", operationList);
            message.put("data", list);

            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * 添加血型
     */
    @ApiOperation(value = "添加血型", httpMethod = "POST", response = JSONObject.class, notes = "添加血型")
    @RequestMapping("/addBloodType")
    @ResponseBody
    public JSONObject addBloodType(@ApiParam(required = true, name = "dictbloodtype", value = "新增血型对象") Dictbloodtype dictbloodtype) {

        JSONObject message = new JSONObject();
        dictbloodtype.setBloodtypeCreatetime(new Date());
        dictbloodtype.setBloodtypeStatus(0);
        dictbloodtype.setBloodtypeUpdatetime(new Date());
        try {

            int i = dictBloodTypeService.addBloodType(dictbloodtype);
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
     * @Title: getBloodTypeById @Description: 根据血型Id查询信息 @param @param
     *         idBloodtype 血型Id @param @return 设定文件 @return JSONObject
     *         返回类型 @throws
     */
    @ApiOperation(value = "根据血型Id查询信息", httpMethod = "GET", response = JSONObject.class, notes = "根据血型Id查询信息")
    @RequestMapping("/getBloodTypeById")
    @ResponseBody
    public JSONObject getBloodTypeById(@ApiParam(required = true, name = "idBloodtype", value = "血型Id") Long idBloodtype) {
        JSONObject message = new JSONObject();
        if (null == idBloodtype || "".equals(idBloodtype)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictbloodtype dictbloodtype = dictBloodTypeService.getBloodTypeById(idBloodtype);

            message.put("data", dictbloodtype);

            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateBloodType @Description: 修改血型信息 @param @param dictbloodtype
     *         修改年龄单位对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改血型信息", httpMethod = "POST", response = JSONObject.class, notes = "修改血型信息")
    @RequestMapping("/updateBloodType")
    @ResponseBody
    public JSONObject updateBloodType(@ApiParam(required = true, name = "dictageunit", value = "修改年龄单位对象") Dictbloodtype dictbloodtype) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictbloodtype.getBloodtypeName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictBloodTypeService.updateBloodType(dictbloodtype);
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
     * @Title: deleteBloodType @Description: 删除血型信息 @param @param idBloodtype
     *         血型id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除血型信息", httpMethod = "GET", response = JSONObject.class, notes = "删除血型信息")
    @RequestMapping("/deleteBloodType")
    @ResponseBody
    public JSONObject deleteBloodType(@ApiParam(required = true, name = "idBloodtype", value = "血型id") Long idBloodtype) {
        JSONObject message = new JSONObject();
        if (null == idBloodtype || "".equals(idBloodtype)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictbloodtype bloodTypeById = dictBloodTypeService.getBloodTypeById(idBloodtype);
            bloodTypeById.setBloodtypeStatus(1);
            int i = dictBloodTypeService.updateBloodType(bloodTypeById);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
