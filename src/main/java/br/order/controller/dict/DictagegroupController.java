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
import br.crm.pojo.dict.Dictagegroup;
import br.crm.service.dict.DictagegroupService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictagegroupController
 * @Description: 年龄分组字典表的相关维护
 * @author zxy
 * @date 2016年9月12日 上午11:24:51
 *
 */
@Controller
@RequestMapping("/dictagegroup")
public class DictagegroupController {
    @Autowired
    private DictagegroupService dictagegroupService;

    @Autowired
    private CommonController commonController;

    @Autowired
    private BrOperationService brOperationService;

    /**
     * 
     * @Title: getAllDictagegroup @Description: 查询所有的年龄分组 @param @param page
     * 当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return JSONObject
     * 返回类型 @throws
     */
    @ApiOperation(value = "查询所有的年龄分组", httpMethod = "GET", response = JSONObject.class, notes = "查询所有的年龄分组")
    @RequestMapping("/getAllDictagegroup")
    @ResponseBody
    public JSONObject getAllDictagegroup(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {

        JSONObject message = new JSONObject();

        try {

            PageInfo<Dictagegroup> pageInfo = dictagegroupService.getDictagegroupList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictagegroup");
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
     * @Title: addDictAgeGroup @Description: 添加年龄分组 @param @param dictagegroup
     * 新增年龄分组对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加年龄分组", httpMethod = "POST", response = JSONObject.class, notes = "添加年龄分组")
    @RequestMapping("/addDictAgeGroup")
    @ResponseBody
    public JSONObject addDictAgeGroup(Dictagegroup dictagegroup) {
        JSONObject message = new JSONObject();
        dictagegroup.setAgegroupCreatetime(new Date());
        dictagegroup.setAgegroupStatus(0);
        dictagegroup.setAgegroupUpdatetime(new Date());
        try {
            int i = dictagegroupService.addDictagegroup(dictagegroup);
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
     * @Title: getDictAgeGroupById @Description: 根据年龄分组Id查询信息 @param @param
     * agegroupId 分组Id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "根据年龄分组Id查询信息", httpMethod = "GET", response = JSONObject.class, notes = "根据年龄分组Id查询信息")
    @RequestMapping("/getDictAgeGroupById")
    @ResponseBody
    public JSONObject getDictAgeGroupById(@ApiParam(required = true, name = "agegroupId", value = "分组Id") Integer agegroupId) {
        JSONObject message = new JSONObject();
        if (null == agegroupId || "".equals(agegroupId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictagegroup dictagegroup = dictagegroupService.getDictagegroup(agegroupId);
            message.put("data", dictagegroup);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: updateDictagegroup @Description: 修改年龄分组信息 @param @param
     * dictagegroup 修改年龄分组对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改年龄分组信息", httpMethod = "POST", response = JSONObject.class, notes = "修改年龄分组信息")
    @RequestMapping("/updateDictagegroup")
    @ResponseBody
    public JSONObject updateDictagegroup(@ApiParam(required = true, name = "dictagegroup", value = "修改年龄分组对象") Dictagegroup dictagegroup) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(dictagegroup.getAgegroupName())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = dictagegroupService.updateDictagegroup(dictagegroup);
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
     * @Title: deleteCountry @Description: 删除年龄分组信息 @param @param agegroupId
     * 年龄分组id @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "删除年龄分组信息", httpMethod = "GET", response = JSONObject.class, notes = "删除年龄分组信息")
    @RequestMapping("/deleteDictagegroup")
    @ResponseBody
    public JSONObject deleteCountry(@ApiParam(required = true, name = "agegroupId", value = "年龄分组id") Integer agegroupId) {
        JSONObject message = new JSONObject();
        if (null == agegroupId || "".equals(agegroupId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            Dictagegroup dictagegroup = dictagegroupService.getDictagegroup(agegroupId);
            dictagegroup.setAgegroupStatus(1);
            int i = dictagegroupService.updateDictagegroup(dictagegroup);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

}
