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
import br.crm.pojo.dict.Dictsex;
import br.crm.service.dict.DictsexService;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 
 * @ClassName: DictSexController
 * @Description: 性别字典表信息相关维护
 * @author zxy
 * @date 2016年9月12日 上午11:58:39
 *
 */
@Controller
@RequestMapping("/dictsex")
public class DictSexController {
    @Autowired
    private DictsexService dictsexService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getDictsexList @Description: 分页查询性别列表 @param @param page
     *         当前页 @param @param rows 每页显示条数 @param @return 设定文件 @return
     *         JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "分页查询性别列表", httpMethod = "GET", response = JSONObject.class, notes = "分页查询性别列表")
    @RequestMapping("/getDictsexList")
    @ResponseBody
    public JSONObject getDictsexList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject jsonObject = new JSONObject();
        try {
            PageInfo<Dictsex> dictsexByPage = dictsexService.getDictSexList(page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "dictsex");
            jsonObject.put("operationList", operationList);
            jsonObject.put("data", dictsexByPage);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: addDictsex @Description: 添加性别 @param @param dictsex
     *         性别对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "添加性别", httpMethod = "POST", response = JSONObject.class, notes = "添加性别")
    @RequestMapping("/addDictsex")
    @ResponseBody
    public JSONObject addDictsex(@ApiParam(required = true, name = "dictsex", value = "dictsex,性别对象") Dictsex dictsex) {
        JSONObject jsonObject = new JSONObject();
        dictsex.setSexStatus(0);
        dictsex.setSexCreatetime(new Date());
        dictsex.setSexUpdatetime(dictsex.getSexCreatetime());
        try {
            int i = dictsexService.addDictSex(dictsex);
            jsonObject.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 数据回显
     * 
     * @return
     */
    @ApiOperation(value = "根据id获取对象信息", httpMethod = "GET", response = JSONObject.class, notes = "根据id获取对象信息")
    @RequestMapping("/getDictsexById")
    @ResponseBody
    public JSONObject getDictsexById(@ApiParam(required = true, name = "pid", value = "pid,主键") int pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            Dictsex dictsex = dictsexService.getDictsexByID(pid);
            jsonObject.put("data", dictsex);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);

        }
        catch (Exception e) {
        }
        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

    /**
     * 
     * @Title: updateDictsex @Description: 修改性别 @param @param dictsex
     *         性别对象 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "修改性别", httpMethod = "POST", response = JSONObject.class, notes = "修改性别")
    @RequestMapping("/updateDictsex")
    @ResponseBody
    public JSONObject updateDictsex(@ApiParam(required = true, name = "dictsex", value = "dictsex,性别对象") Dictsex dictsex) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(dictsex.getIdSex())) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            int i = dictsexService.updateDictSex(dictsex);
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
     * @Title: deleteDictsex @Description: 逻辑删除性别 @param @param pid
     *         修改主键 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "逻辑删除性别", httpMethod = "GET", response = JSONObject.class, notes = "逻辑删除性别")
    @RequestMapping("/deleteDictsex")
    @ResponseBody
    public JSONObject deleteDictsex(@ApiParam(required = true, name = "pid", value = "pid,修改主键") int pid) {
        JSONObject jsonObject = new JSONObject();
        if (CommonUtils.isEmpty(pid)) {
            return InterfaceResultUtil.getReturnMapValidValue(jsonObject);
        }
        try {
            Dictsex dictsex = dictsexService.getDictsexByID(pid);
            dictsex.setSexStatus(1);

            int i = dictsexService.updateDictSex(dictsex);
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
     * @Title: dictSexListByStatus @Description: 可用性别列表 @param @return
     *         设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "可用性别列表", httpMethod = "GET", response = JSONObject.class, notes = "可用性别列表")
    @RequestMapping("/dictSexListByStatus")
    @ResponseBody
    public JSONObject dictSexListByStatus() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Dictsex> list = dictsexService.dictSexListByStatus();
            jsonObject.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(jsonObject);
    }

}
