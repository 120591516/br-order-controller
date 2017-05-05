package br.order.controller.org;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
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

import br.crm.common.utils.UUIDUtils;
import br.crm.pojo.dict.DictEmailRecord;
import br.crm.pojo.org.Organization;
import br.crm.pojo.org.OrganizationConn;
import br.crm.pojo.org.OrganizationEmail;
import br.crm.pojo.org.OrganizationLevel;
import br.crm.pojo.org.OrganizationReview;
import br.crm.pojo.permission.OrganizationUser;
import br.crm.service.dict.DictEmailService;
import br.crm.service.org.OrgConnService;
import br.crm.service.org.OrganizationService;
import br.crm.service.org.RegisterOrgService;
import br.crm.service.permission.OrganizationUserService;
import br.crm.vo.org.OrganizationAllInfoVo;
import br.crm.vo.org.OrganizationQu;
import br.crm.vo.org.OrganizationVo;
import br.crm.vo.org.RegistOrgInfoVo;
import br.order.common.utils.InterfaceResultUtil;
import br.order.common.utils.SimpleEmail;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;
import br.order.vo.BrUserVo;

/**
 * @ClassName: OrganizationController
 * @Description: 体检机构controller
 * @author admin
 * @date 2016年9月12日 下午4:39:52
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RegisterOrgService registerOrgService;

    @Autowired
    private SimpleEmail simpleEmail;

    @Autowired
    private OrgConnService orgConnService;

    @Autowired
    private DictEmailService dictEmailService;

    /**
     * Session
     */
    @Autowired
    private CommonController commonController;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Autowired
    private BrOperationService brOperationService;

    /**
     * @Title: getOrganizationByPage
     * @Description: 分页获取体检机构列表
     * @param organizationQu
     * @param page
     * @param rows
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "分页获取体检机构列表", httpMethod = "POST", response = JSONObject.class, notes = "分页获取体检机构列表")
    @RequestMapping(value = "/getOrganizationByPage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getOrganizationByPage(@ApiParam(required = true, name = "organizationQu", value = "organizationQu,查询条件") OrganizationQu organizationQu,
            @ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<OrganizationVo> pageInfo = organizationService.getOrganizationByPage(organizationQu, page, rows);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "org");
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
     * @Title: insertOrganization
     * @Description: 新增体检机构基本信息
     * @param organization
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "新增体检机构基本信息", httpMethod = "POST", response = JSONObject.class, notes = "新增体检机构基本信息")
    @RequestMapping(value = "/insertOrganization", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertOrganization(@ApiParam(required = true, name = "registOrgInfoVo", value = "registOrgInfoVo,新增机构对象") RegistOrgInfoVo registOrgInfoVo) {
        JSONObject message = new JSONObject();
        try {
            //添加普通机构信息
            String orgId = organizationService.insertOrganization(registOrgInfoVo);

            if (StringUtils.isNotEmpty(orgId)) {
                //添加用户表
                OrganizationUser organizationUser = new OrganizationUser();
                organizationUser.setUserId(UUIDUtils.getId());
                organizationUser.setOrgId(orgId);
                organizationUser.setUserLoginName(registOrgInfoVo.getUserLoginName());
                organizationUser.setOrgBranchId(0 + "");
                organizationUser.setOrgBranchDeptId(0 + "");
                organizationUser.setUserPhone(registOrgInfoVo.getOrgConnPhone());
                organizationUser.setUserEmail(registOrgInfoVo.getOrgConnEmail());
                organizationUser.setUserName(registOrgInfoVo.getOrgConnName());
                Md5Hash md5 = new Md5Hash(registOrgInfoVo.getUserPassword(), registOrgInfoVo.getUserLoginName(), 2);
                organizationUser.setUserPassword(md5.toString());
                organizationUser.setUserStatus(1);
                organizationUser.setUserCreateTime(new Date());
                organizationUser.setUserEditTime(new Date());
                int i = organizationUserService.addOrgUserByOrg(organizationUser);
                message.put("data", i);
                return InterfaceResultUtil.getReturnMapSuccess(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: getOrgAllInfoById
     * @Description: 根据机构id查询机构所有信息
     * @param orgId
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "根据机构id查询机构所有信息", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id查询机构所有信息")
    @RequestMapping("/getOrgAllInfoById")
    @ResponseBody
    public JSONObject getOrgAllInfoById(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationAllInfoVo organizationAllInfo = organizationService.getOrgAllInfoById(orgId);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "orgDetail");
            message.put("operationList", operationList);
            message.put("data", organizationAllInfo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: getOrganizationById
     * @Description: 根据机构id查询机构基本信息
     * @param orgId
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "根据机构id查询机构基本信息", httpMethod = "GET", response = JSONObject.class, notes = "根据机构id查询机构信息")
    @RequestMapping("/getOrganizationById")
    @ResponseBody
    public JSONObject getOrganizationById(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationVo organizationVo = organizationService.getOrganizationById(orgId);
            message.put("data", organizationVo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: updateOrganization
     * @Description: 修改机构基本信息
     * @param organization
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "修改机构基本信息", httpMethod = "POST", response = JSONObject.class, notes = "修改机构基本信息")
    @RequestMapping("/updateOrganization")
    @ResponseBody
    public JSONObject updateOrganization(@ApiParam(required = true, name = "organization", value = "organization,修改机构对象") OrganizationVo organization) {
        JSONObject message = new JSONObject();
        try {
            //修改机构的基本信息
            int i = organizationService.updateOrganizationVo(organization);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: deleteOrganization
     * @Description:删除机构
     * @param orgId
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "删除机构", httpMethod = "GET", response = JSONObject.class, notes = "删除机构")
    @RequestMapping("/deleteOrganization")
    @ResponseBody
    public JSONObject deleteOrganization(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        try {
            OrganizationVo organizationVo = organizationService.getOrganizationById(orgId);
            if (null != organizationVo) {
                int flag = organizationService.checkIsDelete(organizationVo);
                if (flag == 0) {
                    organizationVo.setOrgStatus(1);
                    organizationVo.setOrgEditTime(new Date());
                    organizationService.updateOrganization(organizationVo);
                    message.put("data", flag);
                    return InterfaceResultUtil.getReturnMapSuccess(message);
                }
                else {
                    message.put("data", flag);
                    return InterfaceResultUtil.getReturnMapSuccess(message);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: getOrganizationLevel
     * @Description: 获取所有体检机构等级
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "获取所有体检机构等级", httpMethod = "GET", response = JSONObject.class, notes = "获取所有体检机构等级")
    @RequestMapping("/getOrganizationLevel")
    @ResponseBody
    public JSONObject getOrganizationLevel() {
        JSONObject message = new JSONObject();
        try {
            List<OrganizationLevel> list = organizationService.getOrganizationLevel();
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: reviewOrganization
     * @Description: 审核体检机构
     * @param orgReview
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "审核体检机构", httpMethod = "POST", response = JSONObject.class, notes = "审核体检机构")
    @RequestMapping("/reviewOrganization")
    @ResponseBody
    public JSONObject reviewOrganization(@ApiParam(required = true, name = "orgReview", value = "orgReview,机构审核对象") OrganizationReview orgReview) {
        JSONObject message = new JSONObject();
        int i = 0;
        try {

            BrUserVo user = commonController.getUserBySession();
            Organization organization = new Organization();
            if (null != user) {
                organization.setOrgId(orgReview.getOrgId());
                organization.setOrgStatus(orgReview.getOrgReviewResult());
                if (orgReview.getOrgReviewResult().intValue() == 0) {
                    //根据机构的ID查询到注册用户ID信息
                    OrganizationUser organizationUser = organizationUserService.getOrgUserByOrgId(orgReview.getOrgId());
                    if (null != organizationUser) {
                        organizationUser.setUserStatus(0);
                        organizationUserService.updateOrgUser(organizationUser);
                    }
                }
                orgReview.setOrgReviewPersonId(user.getUserId());
                orgReview.setOrgReviewCreateTime(new Date());
                i = organizationService.reviewOrganization(organization, orgReview);
            }

            try {
                DictEmailRecord dictEmail = new DictEmailRecord();
                OrganizationEmail orgEmail = new OrganizationEmail();
                String title = "审核结果";
                if (orgReview.getOrgReviewResult() == 0 && i > 0) {
                    List<OrganizationConn> orgConnList = orgConnService.getOrgConnByOrgId(orgReview.getOrgId());
                    String content = "尊敬的" + orgConnList.get(0).getOrgConnName() + "，您注册的体检机构已审核通过，可进行登录";
                    String sendTo = orgConnList.get(0).getOrgConnEmail();
                    simpleEmail.sendMail(title, content, sendTo);
                    dictEmail.setDictEmailTitle(title);
                    dictEmail.setDictEmailStatus(0);
                    dictEmail.setDictEmailTo(sendTo);
                    dictEmail.setDictEmailContent(content);
                    dictEmail.setDictEmailCreateTime(new Date());
                    Long m = dictEmailService.insertEmail(dictEmail);
                    if (m > 0) {
                        orgEmail.setDictEmailId(m);
                        orgEmail.setOrgId(orgReview.getOrgId());
                        organizationService.insertOrgEmail(orgEmail);
                    }

                }
                else if (orgReview.getOrgReviewResult() == 3 && i > 0) {

                    List<OrganizationConn> orgConnList = orgConnService.getOrgConnByOrgId(orgReview.getOrgId());
                    String content = "尊敬的" + orgConnList.get(0).getOrgConnName() + "，您注册的体检机构未通过审核，请联系公司客服了解详细信息";
                    String sendTo = orgConnList.get(0).getOrgConnEmail();
                    simpleEmail.sendMail(title, content, sendTo);
                    dictEmail.setDictEmailTitle(title);
                    dictEmail.setDictEmailTo(sendTo);
                    dictEmail.setDictEmailContent(content);
                    dictEmail.setDictEmailCreateTime(new Date());
                    Long m = dictEmailService.insertEmail(dictEmail);
                    if (m > 0) {
                        orgEmail.setDictEmailId(m);
                        orgEmail.setOrgId(orgReview.getOrgId());
                        organizationService.insertOrgEmail(orgEmail);
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * @Title: getAvailableOrgs
     * @Description: 获取所有可用体检机构
     * @return 设定文件
     * @return JSONObject 返回类型
     */
    @ApiOperation(value = "获取所有可用体检机构", httpMethod = "GET", response = JSONObject.class, notes = "获取所有可用体检机构")
    @RequestMapping("/getAvailableOrgs")
    @ResponseBody
    public JSONObject getAvailableOrgs() {
        JSONObject message = new JSONObject();
        try {
            List<Organization> list = organizationService.getAvailableOrgs();
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "根据体检机构ID获取审核信息", httpMethod = "GET", response = JSONObject.class, notes = "根据体检机构ID获取审核信息")
    @RequestMapping("/getreviewOrganizationListById")
    @ResponseBody
    public JSONObject getreviewOrganizationListById(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {
        JSONObject message = new JSONObject();
        if (!StringUtils.isNotEmpty(orgId)) {
            message.put("date", "参数错误");
            return InterfaceResultUtil.getReturnMapError(message);
        }
        try {
            List<OrganizationReview> list = organizationService.getreviewOrganizationListById(orgId);
            message.put("data", list);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "用户重名校验", httpMethod = "GET", response = JSONObject.class, notes = "用户重名校验")
    @RequestMapping("/getCountByUserName")
    @ResponseBody
    public JSONObject getCountByUserName(@ApiParam(required = true, name = "userLoginName", value = "userLoginName,用户名") String userLoginName) {
        JSONObject message = new JSONObject();
        try {
            int i = 0;
            i = registerOrgService.getCountByUserName(userLoginName);
            message.put("data", i);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }
}
