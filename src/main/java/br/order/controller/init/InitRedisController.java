package br.order.controller.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;

import br.crm.service.customer.order.CustomerOrderCartService;
import br.crm.service.customer.order.CustomerOrderService;
import br.crm.service.firstdata.FirstImgDataService;
import br.crm.service.permission.OrgUserManagerService;
import br.order.common.utils.InterfaceResultUtil;
import br.order.redis.branch.OrgBranchImgRedis;
import br.order.redis.branch.OrgBranchRedis;
import br.order.redis.branch.OrgBranchRestRedis;
import br.order.redis.dept.OrgDeptRedis;
import br.order.redis.dict.DictAreaRedis;
import br.order.redis.dict.DictConclusionDeptTypeRedis;
import br.order.redis.dict.DictConclusionRedis;
import br.order.redis.dict.DictConclusionResultClassRedis;
import br.order.redis.dict.DictCountryRedis;
import br.order.redis.dict.DictDeptTypeRedis;
import br.order.redis.dict.DictExamSuiteTypeRedis;
import br.order.redis.dict.DictHighIncidenceDiseaseRedis;
import br.order.redis.dict.DictImgRedis;
import br.order.redis.dict.DictNationRedis;
import br.order.redis.dict.DictRelationshipRedis;
import br.order.redis.dict.DictagegroupRedis;
import br.order.redis.dict.DictageunitRedis;
import br.order.redis.dict.DictbloodtypeRedis;
import br.order.redis.dict.DictconclusionGroupRedis;
import br.order.redis.dict.DicteducationRedis;
import br.order.redis.dict.DictidentityRedis;
import br.order.redis.dict.DictinformwayRedis;
import br.order.redis.dict.DictjobclassRedis;
import br.order.redis.dict.DictmarriageRedis;
import br.order.redis.dict.DictoccupationRedis;
import br.order.redis.dict.DictpaywayRedis;
import br.order.redis.dict.DictreceipttypeRedis;
import br.order.redis.dict.DictsectionRedis;
import br.order.redis.dict.DictsexRedis;
import br.order.redis.examfeeitem.OrgExamFeeItemDetailRedis;
import br.order.redis.examfeeitem.OrgExamFeeItemRedis;
import br.order.redis.examitem.OrgExamItemRedis;
import br.order.redis.examitem.OrgExamItemTypeRedis;
import br.order.redis.examitem.OrgExamItemUserRedis;
import br.order.redis.examitemvalue.OrgExamItemValueRedis;
import br.order.redis.org.OrgConnRedis;
import br.order.redis.org.OrgCooperationRedis;
import br.order.redis.org.OrgIncomeRedis;
import br.order.redis.org.OrgInvestRedis;
import br.order.redis.org.OrgLevelRedis;
import br.order.redis.org.OrgRedis;
import br.order.redis.org.OrgSaleRedis;
import br.order.redis.org.OrgSoftRedis;
import br.order.redis.org.OrgVisitRedis;
import br.order.redis.org.OrgWebRedis;
import br.order.redis.redis.RedisService;
import br.order.redis.suite.OrgBranchSuiteRedis;
import br.order.redis.suite.OrgExamFeeItemSuiteRedis;
import br.order.redis.suite.OrgExamSuiteHidRedis;
import br.order.redis.suite.OrgExamSuiteImgRedis;
import br.order.redis.suite.OrgExamSuiteRedis;
import br.order.redis.suite.OrgExamSuiteTypeRedis;
import br.order.redis.user.OrgUserRedis;

@Controller
@RequestMapping("/initRedis")
public class InitRedisController {

    @Autowired
    private DictAreaRedis dictAreaRedis;// 省市区服务

    @Autowired
    private DictagegroupRedis dictagegroupRedis;// 年龄分组

    @Autowired
    private DictageunitRedis dictageunitRedis;// 年龄单位服务

    @Autowired
    private DictCountryRedis dictCountryRedis;// 国家服务

    @Autowired
    private DictbloodtypeRedis dictbloodtypeRedis;// 血型服务

    @Autowired
    private DictDeptTypeRedis dictDeptTypeRedis;

    @Autowired
    private DicteducationRedis dicteducationRedis;// 教育服务

    @Autowired
    private DictExamSuiteTypeRedis dictExamSuiteTypeRedis;// 套餐类型服务

    @Autowired
    private DictHighIncidenceDiseaseRedis dictHighIncidenceDiseaseRedis;// 高发疾病服务

    @Autowired
    private DictidentityRedis dictidentityRedis;// 身份服务

    @Autowired
    private DictImgRedis dictImgRedis;

    @Autowired
    private DictinformwayRedis dictinformwayRedis;// 通知方式服务

    @Autowired
    private DictjobclassRedis dictjobclassRedis;// 工作类型服务

    @Autowired
    private DictmarriageRedis dictmarriageRedis;// 婚姻服务

    @Autowired
    private DictNationRedis dictNationRedis;// 民族服务

    @Autowired
    private DictoccupationRedis dictoccupationRedis;// 职业服务

    @Autowired
    private DictpaywayRedis dictpaywayRedis;// 支付方式服务

    @Autowired
    private DictreceipttypeRedis dictreceipttypeRedis;// 发票类型服务

    @Autowired
    private DictsectionRedis dictsectionRedis;// 总检科室服务

    @Autowired
    private DictsexRedis dictsexRedis;// 性别服务

    @Autowired
    private OrgRedis orgRedis;//体检机构服务

    @Autowired
    private OrgExamSuiteRedis orgExamSuiteRedis;//套餐服务

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrgBranchRedis orgBranchRedis;//门店服务

    @Autowired
    private OrgBranchRestRedis orgBranchRestRedis;//门店休息日

    @Autowired
    private OrgBranchImgRedis orgBranchImgRedis;//门店图片

    @Autowired
    private OrgExamSuiteTypeRedis orgExamSuiteTypeRedis;//套餐类型中间表服务

    @Autowired
    private OrgLevelRedis orgLevelRedis;//机构等级服务

    @Autowired
    private OrgExamFeeItemRedis orgExamFeeItemRedis;// 收费项服务

    @Autowired
    private OrgExamFeeItemSuiteRedis orgExamFeeItemSuiteRedis;//套餐收费项

    @Autowired
    private OrgExamFeeItemDetailRedis orgExamFeeItemDetailRedis;//收费项绑定检查项医生

    @Autowired
    private OrgBranchSuiteRedis orgBranchSuiteRedis;//门店-套餐服务

    @Autowired
    private OrgExamItemRedis orgExamItemRedis;//检查项服务

    @Autowired
    private OrgDeptRedis orgDeptRedis;//体检机构科室服务

    @Autowired
    private OrgUserRedis orgUserRedis;//体检机构科室服务

    @Autowired
    private OrgExamItemUserRedis orgExamItemUserRedis;//体检机构科室服务    

    @Autowired
    private OrgUserManagerService orgUserManagerService;//体检机构--管理用户服务

    @Autowired
    private OrgExamItemTypeRedis orgExamItemTypeRedis; //检查项目类型服务

    @Autowired
    private FirstImgDataService firstImgDataService; //轮播图图片ID

    @Autowired
    private CustomerOrderCartService customerOrderCartService;//订单-购物车中间表id

    @Autowired
    private CustomerOrderService customerOrderService;//订单-购物车中间表id

    @Autowired
    private DictRelationshipRedis dictRelationshipRedis;//亲友关系字典表

    @Autowired
    private OrgExamItemValueRedis orgExamItemValueRedis;//体证词

    @Autowired
    private OrgConnRedis orgConnRedis;//体检机构-联系人

    @Autowired
    private OrgCooperationRedis orgCooperationRedis;//体检机构-合作意向

    @Autowired
    private OrgIncomeRedis orgIncomeRedis;//体检机构-体检收入

    @Autowired
    private OrgInvestRedis orgInvestRedis;//体检机构-投资人

    @Autowired
    private OrgSoftRedis orgSoftRedis;//体检机构-软件信息

    @Autowired
    private OrgVisitRedis orgVisitRedis;//体检机构-拜访信息

    @Autowired
    private OrgWebRedis orgWebRedis;//体检机构-网络信息

    @Autowired
    private OrgSaleRedis orgSaleRedis;//体检机构销售信息

    @Autowired
    private DictConclusionRedis dictConclusionRedis;//结论词字典表

    @Autowired
    private DictConclusionDeptTypeRedis dictConclusionDeptTypeRedis;//结论词科室类型

    @Autowired
    private DictconclusionGroupRedis dictconclusionGroupRedis;//结论词分组

    @Autowired
    private DictConclusionResultClassRedis dictConclusionResultClassRedis;//结论词结果
    
    @Autowired    
    private  OrgExamSuiteHidRedis orgExamSuiteHidRedis; //套餐高发疾病
    
    @Autowired
    private OrgExamSuiteImgRedis orgExamSuiteImgRedis;//套餐图片

    // 初始化字典表全部数据
    @ApiOperation(value = "初始化字典表全部数据", httpMethod = "GET", response = JSONObject.class, notes = "初始化字典表全部数据")
    @RequestMapping("/initDictData")
    @ResponseBody
    public JSONObject initDictData() {
        System.out.println("开始初始化字典表缓存数据...");
        try {
            dictAreaRedis.initData();
            dictagegroupRedis.initData();
            dictageunitRedis.initData();
            dictCountryRedis.initData();
            dictbloodtypeRedis.initData();
            dictDeptTypeRedis.initData();
            dicteducationRedis.initData();
            dictExamSuiteTypeRedis.initData();
            dictHighIncidenceDiseaseRedis.initData();
            dictidentityRedis.initData();
            dictImgRedis.initData();
            dictinformwayRedis.initData();
            dictjobclassRedis.initData();
            dictmarriageRedis.initData();
            dictNationRedis.initData();
            dictoccupationRedis.initData();
            dictpaywayRedis.initData();
            dictreceipttypeRedis.initData();
            dictsectionRedis.initData();
            dictsexRedis.initData();
            orgRedis.initData();
            orgExamSuiteRedis.initData();
            orgBranchRedis.initData();
            orgBranchRestRedis.initData();
            orgBranchImgRedis.initData();
            orgExamSuiteTypeRedis.initData();
            orgLevelRedis.initData();
            orgExamFeeItemRedis.initData();
            orgExamFeeItemSuiteRedis.initData();
            orgExamFeeItemDetailRedis.initData();
            orgBranchSuiteRedis.initData();
            orgExamItemRedis.initData();
            orgDeptRedis.initData();
            orgUserRedis.initData();
            orgExamItemUserRedis.initData();
            orgUserManagerService.initData();
            orgExamItemTypeRedis.initData();
            firstImgDataService.initData();
            customerOrderCartService.initData();
            customerOrderService.initData();
            dictRelationshipRedis.initData();
            orgExamItemValueRedis.initData();
            orgConnRedis.initData();
            orgCooperationRedis.initData();
            orgIncomeRedis.initData();
            orgInvestRedis.initData();
            orgSoftRedis.initData();
            orgVisitRedis.initData();
            orgWebRedis.initData();
            orgSaleRedis.initData();
            dictConclusionRedis.initData();
            dictConclusionDeptTypeRedis.initData();
            dictconclusionGroupRedis.initData();
            dictConclusionResultClassRedis.initData();
            orgExamSuiteHidRedis.initData();
            orgExamSuiteImgRedis.initData();
        	
            System.out.println("完成初始化字典表缓存数据...");
            return InterfaceResultUtil.getReturnMapSuccess(null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("初始化字典表缓存数据出错...");
        return InterfaceResultUtil.getReturnMapError(null);
    }

    @ApiOperation(value = "清除全部数据", httpMethod = "GET", response = JSONObject.class, notes = "清除全部数据")
    @RequestMapping("/clearData")
    @ResponseBody
    public JSONObject clearData() {
        try {
            System.out.println("开始初始化字典表清空数据...");
            redisService.destroy();
            System.out.println("完成初始化字典表清空数据...");
            return InterfaceResultUtil.getReturnMapSuccess(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return InterfaceResultUtil.getReturnMapError(null);
    }
    

}
