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
import br.crm.pojo.customer.order.CustomerOrderPayInfo;
import br.crm.service.customer.order.CustomerOrderPayInfoService;
import br.crm.vo.order.CustomerOrderPayInfoVo;
import br.order.controller.common.CommonController;
import br.order.service.BrOperationService;
import br.order.vo.BrRoleVo;

@Controller
@RequestMapping("/payInfo")
public class CustomerOrderPayInfoController {

    @Autowired
    private CustomerOrderPayInfoService customerOrderPayInfoService;

    @Autowired
    private BrOperationService brOperationService;

    @Autowired
    private CommonController commonController;

    /** 
    * @Title: getCustomerOrderPayInfoByPage 
    * @Description: TODO(分页查询管理平台的消费记录)
    * @param page
    * @param rows
    * @param customerOrderPayInfoVo
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "分页查询管理平台的消费记录", httpMethod = "POST", response = JSONObject.class, notes = "分页查询管理平台的消费记录")
    @RequestMapping(value = "/getPayInfoByPage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCustomerOrderPayInfoByPage(@ApiParam(required = true, name = "page", value = "page,当前页") @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @ApiParam(required = true, name = "rows", value = "rows,每页显示条数") @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @ApiParam(required = true, name = "customerOrderPayInfo", value = "customerOrderPayInfo,订单支付明细对象") CustomerOrderPayInfoVo customerOrderPayInfoVo) {
        JSONObject message = new JSONObject();
        try {
            String orgId = null;
            PageInfo<CustomerOrderPayInfo> pageInfo = customerOrderPayInfoService.getCustomerOrderPayInfoByPage(page, rows, customerOrderPayInfoVo, orgId);
            List<BrRoleVo> rolesList = commonController.getUserBySession().getRoles();
            Map<String, Object> operationList = brOperationService.getOperationByRole(rolesList, "payInfo");
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
    * @Title: getCustomerOrderPayInfo 
    * @Description: 查看消费记录详情
    * @param customerOrderPayInfoId
    * @return    设定文件 
    * @return JSONObject    返回类型 
    */
    @ApiOperation(value = "查询消费记录详情", httpMethod = "GET", response = JSONObject.class, notes = "查询消费记录详情")
    @RequestMapping(value = "/getPayInfo", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getCustomerOrderPayInfo(@ApiParam(required = true, name = "customerOrderPayInfoId", value = "customerOrderPayInfoId,订单支付明细id") String customerOrderPayInfoId) {
        JSONObject message = new JSONObject();
        try {
            CustomerOrderPayInfoVo customerOrderPayInfoVo = customerOrderPayInfoService.getCustomerOrderPayInfoById(customerOrderPayInfoId);
            message.put("data", customerOrderPayInfoVo);
            return InterfaceResultUtil.getReturnMapSuccess(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return InterfaceResultUtil.getReturnMapError(message);
    }

    /**
     * 导出支付记录
    * @Title: getCustomerOrderPayInfoExport 
    * @Description: TODO
    * @param @param from
    * @param @param fileName
    * @param @return    
    * @return JSONObject    
    * @throws
     */
    @ApiOperation(value = "导出支付记录", httpMethod = "GET", response = JSONObject.class, notes = "导出支付记录")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void getCustomerOrderPayInfoExport(HttpServletResponse response) {
        try {
            List<CustomerOrderPayInfo> list = customerOrderPayInfoService.exportCustomerOrderPayInList(null);
            String columnNames[] = { "订单号", "金额", "支付方式", "购买时间" };// 列名
            String keys[] = { "order_no", "amount", "pay_type", "create_time" };// map中的key 
            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sheetName", "支付列表");
            listMap.add(map);
            for (CustomerOrderPayInfo pay : list) {
                try {
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mapValue.put("order_no", pay.getOrderNo());
                    mapValue.put("amount", String.valueOf(pay.getOrderPayAmount()));
                    mapValue.put("pay_type", pay.getPayWayName());
                    mapValue.put("create_time", format.format(pay.getCustomerOrderPayInfoCreateTime()));
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
