package br.order.controller.customerOrder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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

import br.crm.common.utils.ExcelUtil;
import br.crm.common.utils.InterfaceResultUtil;
import br.crm.service.customer.order.CustomerOrderService;
import br.crm.service.customer.order.CustomerOrderStatusService;
import br.crm.vo.customer.order.CustomerOrderVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

@Controller
@RequestMapping("/customerOrder")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private CustomerOrderStatusService customerOrderStatusService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getCustomerOrderControllerByPage 
    * @Description: TODO(管理平台分页获取订单列表)
    * @param page
    * @param rows
    * @param customerOrderVo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "分页获取订单信息", httpMethod = "POST", response = JSONObject.class, notes = "分页获取订单信息")
    @RequestMapping(value = "/getCustomerOrderByPage")
    @ResponseBody
    public JSONObject getCustomerOrderControllerByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "customerOrderVo", value = "customerOrderVo,订单对象") CustomerOrderVo customerOrderVo) {
        JSONObject message = new JSONObject();
        try {
            PageInfo<CustomerOrderVo> pageInfo = customerOrderService.getCustomerOrderByPage(page, rows, customerOrderVo);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "customerOrder");
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
    * @Title: getCustomerOrder 
    * @Description: TODO(管理平台订单详情)
    * @param customerOrderId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "获取订单信息详情", httpMethod = "post", response = JSONObject.class, notes = "获取订单信息详情")
    @RequestMapping(value = "/getCustomerOrder")
    @ResponseBody
    public JSONObject getCustomerOrder(@ApiParam(required = true, name = "orderNo", value = "orderNo,订单号") String orderNo,
            @ApiParam(required = true, name = "cartId", value = "cartId,购物车id") String cartId, @ApiParam(required = true, name = "patrentId", value = "patrentId,体检人id") String patientId) {
        JSONObject message = new JSONObject();
        try {
            /*SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(new Date(Long.valueOf(examTime)));*/
            CustomerOrderVo customerOrderVo = customerOrderService.getCustomerOrderById(orderNo, cartId, patientId);
            message.put("data", customerOrderVo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    @ApiOperation(value = "查询所有状态", httpMethod = "GET", response = JSONObject.class, notes = "查询所有状态")
    @RequestMapping(value = "/getCustomerOrderStatusList")
    @ResponseBody
    public JSONObject getCustomerOrderStatusList() {
        JSONObject message = new JSONObject();
        List<Map<String, String>> map = customerOrderStatusService.getCustomerOrderStatusList();
        message.put("data", map);
        return InterfaceResultUtil.getReturnMapSuccess(message);
    }

    /**
     * 导出订单记录
    * @Title: getCustomerOrderPayInfoExport 
    * @Description: TODO
    * @param @param from
    * @param @param fileName
    * @param @return    
    * @return JSONObject    
    * @throws
     */
    @ApiOperation(value = "导出订单记录", httpMethod = "GET", response = JSONObject.class, notes = "导出订单记录")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void getCustomerOrderExport(HttpServletResponse response, CustomerOrderVo customerOrderVo) {
        try {
            List<CustomerOrderVo> list = customerOrderService.exportCustomerOrderPayInList(customerOrderVo);
            String columnNames[] = { "订单号", "体检机构", "门店", "体检套餐", "体检类型", "购买人", "体检时间", "金额", "支付状态", "创建时间" };// 列名
            String keys[] = { "order_no", "org_name", "branch_name", "suite_name", "exam_type", "pay_name", "exam_time", "amount", "pay_type", "create_time" };// map中的key 
            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sheetName", "订单列表");
            listMap.add(map);
            for (CustomerOrderVo order : list) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                    mapValue.put("order_no", order.getOrderNo());
                    mapValue.put("org_name", order.getOrgName());
                    mapValue.put("branch_name", order.getBranchName());
                    mapValue.put("suite_name", order.getBranchName());
                    mapValue.put("exam_type", order.getExamType());
                    mapValue.put("pay_name", order.getCustomerPatientName());
                    mapValue.put("exam_time", order.getExamTime());
                    mapValue.put("amount", order.getSinglePrice());
                    mapValue.put("pay_type", order.getCustomerOrderStatusName());
                    mapValue.put("create_time", format.format(order.getCustomerOrderCreateTime()));
                    listMap.add(mapValue);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            System.out.println("导出成功！");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ExcelUtil.createWorkBook(listMap, keys, columnNames, null, null, null).write(os);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((System.currentTimeMillis() + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
