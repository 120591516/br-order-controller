package br.order.controller.firstdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
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

import br.crm.common.utils.GetRequestMappingName;
import br.crm.common.utils.UUIDUtils;
import br.crm.pojo.branch.OrganizationBranchImg;
import br.crm.pojo.dict.DictImg;
import br.crm.pojo.firstshow.Firstdatashow;
import br.crm.pojo.suite.OrganizationExamSuiteImg;
import br.crm.service.branch.OrgBranchService;
import br.crm.service.dict.DictImgService;
import br.crm.service.firstdata.OrgFirstDataService;
import br.crm.service.org.OrganizationService;
import br.crm.service.suite.OrgExamSuiteService;
import br.crm.vo.branch.OrganizationBranchVo;
import br.crm.vo.firstdata.FirstdatashowVo;
import br.crm.vo.org.OrganizationQu;
import br.crm.vo.org.OrganizationVo;
import br.crm.vo.suite.OrgExamSuiteQu;
import br.crm.vo.suite.OrgExamSuiteVo;
import br.order.common.utils.InterfaceResultUtil;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

/**
 * 首页展示Controller
 * 
 * @ClassName: FirstDataController
 * @Description: TODO(首页展示Controller)
 * @author adminis
 * @date 2016年10月12日 上午10:53:00
 *
 */
@Controller
@RequestMapping("/firstData")
public class FirstDataController {

    /**
     * 机构Service
     */
    @Autowired
    private OrganizationService organizationService;

    /**
     * 门店服务接口
     */
    @Autowired
    private OrgBranchService orgBranchService;

    /**
     * 套餐服务
     */
    @Autowired
    private OrgExamSuiteService orgExamSuiteService;

    /**
     * 首页展示服务
     */
    @Autowired
    private OrgFirstDataService orgFirstDataService;

    /**
     * 图片服务
     */
    @Autowired
    private DictImgService dictImgService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /**
     * 
     * @Title: getOrganizationList @Description: TODO(首页展示体检机构查询) @param @param
     *         page 当前页 可不填 @param @param rows 每页显示的条数 默认10000条
     *         可不填 @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "体检机构查询", httpMethod = "POST", response = JSONObject.class, notes = "体检机构查询")
    @RequestMapping("/getOrganizationList")
    @ResponseBody
    public JSONObject getOrganizationList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10000", required = true) Integer rows) {

        JSONObject message = new JSONObject();
        try {
            OrganizationQu qu = new OrganizationQu();
            qu.setOrgStatus(0);
            PageInfo<OrganizationVo> list = organizationService.getOrganizationByPage(qu, page, rows);
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
     * @Title: getOrgbanchList @Description: TODO(首页展示门店查询) @param @param page
     *         当前页 可不填 @param @param rows 每页显示的条数 默认10000条 可不填 @param @param
     *         orgId @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "门店查询", httpMethod = "GET", response = JSONObject.class, notes = "门店查询")
    @RequestMapping("/getOrgbanchListByOrg")
    @ResponseBody
    public JSONObject getOrgbanchList(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10000", required = true) Integer rows,
            @ApiParam(required = true, name = "orgId", value = "orgId,体检机构id") String orgId) {

        JSONObject message = new JSONObject();
        try {
            if (StringUtils.isEmpty(orgId)) {
                return InterfaceResultUtil.getReturnMapError(message);
            }
            OrganizationBranchVo vo = new OrganizationBranchVo();
            vo.setStatus(0);
            vo.setOrgId(orgId);
            PageInfo<OrganizationBranchVo> branchList = orgBranchService.searchOrganizationBranch(vo, page, rows);
            // 查询图片id
            if (!branchList.getList().isEmpty()) {
                for (OrganizationBranchVo banchVo : branchList.getList()) {
                    // 根据门店ID查询图片中间表
                    List<OrganizationBranchImg> list = orgFirstDataService.getImgByBranchId(banchVo.getBranchId());
                    if (CollectionUtils.isNotEmpty(list)) {
                        for (OrganizationBranchImg organizationBranchImg : list) {
                            DictImg img = dictImgService.getEntityById(organizationBranchImg.getDictImgId());
                            if (null != img) {
                                banchVo.setImgURL(img.getImgLocation());
                                banchVo.setImgId(img.getImgId().toString());
                            }
                        }
                    }
                }
            }

            message.put("data", branchList);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 
     * @Title: getSuiteList @Description: TODO(首页展示套餐查询) @param @param branchId
     *         门店ID @param @return 设定文件 @return JSONObject 返回类型 @throws
     */
    @ApiOperation(value = "套餐查询", httpMethod = "GET", response = JSONObject.class, notes = "套餐查询")
    @RequestMapping("/getSuiteListByOrg")
    @ResponseBody
    public JSONObject getSuiteList(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {

        JSONObject message = new JSONObject();
        try {
            if (StringUtils.isNotEmpty(orgId)) {
            	 int page=1;
            	 int rows=100000;
            	 OrgExamSuiteQu orgExamSuiteQu = new OrgExamSuiteQu();
            	 orgExamSuiteQu.setOrgId(orgId);
            	PageInfo<OrgExamSuiteVo> suiteList = orgExamSuiteService.getOrgExamSuite(page, rows, orgExamSuiteQu);
                if (CollectionUtils.isNotEmpty(suiteList.getList())) {
                    for (OrgExamSuiteVo orgExamSuiteVo : suiteList.getList()) {
                        List<OrganizationExamSuiteImg> list = orgFirstDataService.getImgByOrgExamSuiteId(orgExamSuiteVo.getExamSuiteId());
                        if (CollectionUtils.isNotEmpty(list)) {
                            for (OrganizationExamSuiteImg organizationExamSuiteImg : list) {
                                DictImg img = dictImgService.getEntityById(organizationExamSuiteImg.getDictImgId());
                                if (null != img) {
                                    orgExamSuiteVo.setExamSuiteImgUrl(img.getImgLocation());
                                    orgExamSuiteVo.setImgId(img.getImgId().toString());
                                }
                            }
                        }
                    }
                }

                message.put("data", suiteList);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "首页展示分页查询", httpMethod = "GET", response = JSONObject.class, notes = "首页展示分页查询")
    @RequestMapping("/showFirstData")
    @ResponseBody
    public JSONObject showFirstData(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows, FirstdatashowVo firstdatashowVo) {

        JSONObject message = new JSONObject();
        try {
            List<FirstdatashowVo> result = new ArrayList<FirstdatashowVo>();
            PageInfo<Firstdatashow> list = orgFirstDataService.showFirstData(page, rows, firstdatashowVo);
            List<Firstdatashow> firstDataShow = list.getList();
            if (CollectionUtils.isNotEmpty(firstDataShow)) {
                for (Firstdatashow first : firstDataShow) {
                    FirstdatashowVo vo = new FirstdatashowVo();
                    // 根据图片的ID查询图片
                    if (null != first.getFirstdatashowDataImageId() && 0 != first.getFirstdatashowDataImageId()) {
                        DictImg img = dictImgService.getEntityById(first.getFirstdatashowDataImageId());
                        if (null != img) {
                            vo.setImgURL(img.getImgLocation());
                        }
                    }
                    // 获取OrgName
                    OrganizationVo organization = organizationService.getOrganizationById(first.getFirstdatashowOrgId());
                    vo.setOrgName(organization.getOrgShortName());
                    BeanUtils.copyProperties(vo, first);
                    result.add(vo);
                }
            }
            String controller = GetRequestMappingName.getControllerName(FirstDataController.class);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            if (StringUtils.isNotEmpty(controller)) {
                Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, controller);
                message.put("operationList", operationList);
            }
            message.put("data", result);
            message.put("total", list.getTotal());

            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "保存首页数据", httpMethod = "POST", response = JSONObject.class, notes = "保存首页数据")
    @RequestMapping("/saveShowFirstData")
    @ResponseBody
    public JSONObject saveShowFirstData(FirstdatashowVo firstdatashowVo) {

        JSONObject message = new JSONObject();
        if (null == firstdatashowVo.getFirstdatashowDataType() || null == firstdatashowVo.getFirstdatashowDataImageId() || null == firstdatashowVo.getFirstdatashowOrgId()
                || "".equals(firstdatashowVo.getFirstdatashowOrgId())) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            firstdatashowVo.setFirstdatashowCreateTime(new Date());
            firstdatashowVo.setFirstdatashowId(UUIDUtils.getId());
            firstdatashowVo.setFirstdatashowDataStatus(0);
            firstdatashowVo.setFirstdatashowEditTime(firstdatashowVo.getFirstdatashowCreateTime());
            int i = orgFirstDataService.saveShowFirstData(firstdatashowVo);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "删除首页数据", httpMethod = "GET", response = JSONObject.class, notes = "删除首页数据")
    @RequestMapping("/deleteShowFirstData")
    @ResponseBody
    public JSONObject deleteShowFirstData(String firstdatashowId) {
        JSONObject message = new JSONObject();
        if (StringUtils.isEmpty(firstdatashowId)) {
            return InterfaceResultUtil.getReturnMapValidValue(message);
        }
        try {
            int i = orgFirstDataService.deleteShowFirstData(firstdatashowId);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
