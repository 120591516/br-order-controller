package br.order.controller.org;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import br.crm.pojo.org.OrganizationReview;
import br.crm.service.org.OrgReviewService;
import br.order.common.utils.InterfaceResultUtil;

@Controller
@RequestMapping("/orgreview")
public class OrgReviewController {

	@Autowired
	private OrgReviewService orgReviewService;

	@ApiOperation(value = "根据机构的Id查询审核信息", httpMethod = "GET", response = JSONObject.class, notes = "根据机构的Id查询审核信息")
	@RequestMapping("/getOrganizationReviewByOrgId")
	@ResponseBody
	public JSONObject getOrganizationReviewByOrgId(@ApiParam(required = true, name = "orgId", value = "orgId,机构id") String orgId) {

		JSONObject message = new JSONObject();
		try {
			JSONObject dataResult = new JSONObject();
			List<OrganizationReview> organizationReview = orgReviewService.getOrganizationReviewByOrgId(orgId);
			dataResult.put("list", organizationReview);
			if (CollectionUtils.isNotEmpty(organizationReview)) {
				dataResult.put("current", organizationReview.get(0));
			}
			message.put("data", dataResult);
			return InterfaceResultUtil.getReturnMapSuccess(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InterfaceResultUtil.getReturnMapError(message);
	}

}
