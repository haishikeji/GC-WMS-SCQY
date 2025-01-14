package com.px.coinage.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.px.coinage.app.req.DeliveryUpdateReq;
import com.px.coinage.app.req.DeliveryWareConfirmReq;
import com.px.coinage.core.annotation.ActiveFastJsonProfile;
import com.px.coinage.core.annotation.CurrentUser;
import com.px.coinage.core.controller.BaseController;
import com.px.coinage.core.util.HttpClientUtil;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.workshop.model.Delivery;
import com.px.coinage.workshop.model.DeliveryDetail;
import com.px.coinage.workshop.model.DeliveryPlan;
import com.px.coinage.workshop.model.DeliveryPlanDetail;
import com.px.coinage.workshop.req.OrderNoReq;
import com.px.coinage.workshop.service.IDeliveryDetailService;
import com.px.coinage.workshop.service.IDeliveryPlanDetailService;
import com.px.coinage.workshop.service.IDeliveryPlanService;
import com.px.coinage.workshop.service.IDeliveryService;
import com.px.coinage.workshop.vo.DeliveryPlanVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * -发货单主表Controller
 */
@Api(value = "App-发货单主表接口", tags = {"App-发货单主表接口"})
@RestController
@RequestMapping(value = "/app/delivery")
@Slf4j
public class AppDeliveryController extends BaseController {

    public final String deliveryBeginEventUrl = "http://127.0.0.1:9995/relay/delivery/begin";

    public final String deliveryEndEventUrl = "http://127.0.0.1:9995/relay/delivery/end";
    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private IDeliveryDetailService deliveryDetailService;

    @Autowired
    private IDeliveryPlanService deliveryPlanService;

    @Autowired
    private IDeliveryPlanDetailService deliveryPlanDetailService;

    // ============7. 发货登记
    @ApiOperation(value = "7.0 app获取所有待出库单号")
    @GetMapping("/list")
    @ActiveFastJsonProfile(groups = "list", clazz = Delivery.class)
    public List<DeliveryPlan> list() {
        LambdaQueryWrapper<DeliveryPlan> queryWrapper = new LambdaQueryWrapper<>();
        // 发货计划单状态  1:初始化 2 已发货计划
        queryWrapper.eq(DeliveryPlan::getStatus, 1);
        queryWrapper.orderByAsc(DeliveryPlan::getPlanDeliveryDate);
        List<DeliveryPlan> data = this.deliveryPlanService.list(queryWrapper);
        return data;
    }

    @ApiOperation(value = "7.1 app根据发货单获取发货信息接口")
    @PostMapping("/getInitInfoByOrderNo")
    public DeliveryPlanVO shopGetInfoByOrderNo(@RequestBody @Valid OrderNoReq req) {
        DeliveryPlanVO data = new DeliveryPlanVO();
        LambdaQueryWrapper<DeliveryPlan> queryWrapper = new LambdaQueryWrapper<>();
        // 发货单状态  1:初始化 2 已发货
        queryWrapper.eq(DeliveryPlan::getOrderNo, req.getOrderNo());
        queryWrapper.eq(DeliveryPlan::getStatus, 1);
        DeliveryPlan model = this.deliveryPlanService.getOne(queryWrapper);
        BeanUtils.copyProperties(model, data);
        List<DeliveryPlanDetail> list = deliveryPlanDetailService.list(new LambdaQueryWrapper<DeliveryPlanDetail>().eq(DeliveryPlanDetail::getPlanId, model.getId()));
        data.setDetailList(list);
//        DeliveryVO data = new DeliveryVO();
//        LambdaQueryWrapper<Delivery> queryWrapper = new LambdaQueryWrapper<>();
//        // 发货单状态  1:初始化 2 已发货
//        queryWrapper.eq(Delivery::getOrderNo, req.getOrderNo());
//        queryWrapper.eq(Delivery::getStatus, 1);
//        Delivery model = this.deliveryService.getOne(queryWrapper);
//        BeanUtils.copyProperties(model, data);
//        List<DeliveryDetail> list = deliveryDetailService.list(new LambdaQueryWrapper<DeliveryDetail>().eq(DeliveryDetail::getDeliveryId, model.getId()));
//        data.setDetailList(list);

        //add by hliu date =20240226,触发库房灯控：发货登记开始
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("orderNo", req.getOrderNo());
                    jsonObj.put("securitiesId", model.getSecuritiesId());
                    HttpClientUtil.post(deliveryBeginEventUrl, jsonObj.toJSONString());

                    log.info("deliveryBeginEvent url :{}, params : {}", deliveryBeginEventUrl, jsonObj.toJSONString());


                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("deliveryBeginEvent is error, req:{},error:{}", req, e.getMessage());
                }
            }
        }).start();

        return data;
    }

    @ApiOperation(value = "7.2 app发货登记")
    @PostMapping("/wareConfirm")
    public Object shopWareConfirm(@RequestBody @Valid DeliveryWareConfirmReq req, @CurrentUser CacheUserVO user) {
        this.deliveryService.wareConfirm(req, user);

        // add by hliu date =20240226 触发主库灯控：发货登记结束事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClientUtil.get(deliveryEndEventUrl, null, null);
                } catch (Exception e) {
                    log.error("deliveryEndEvent is error, req:{},error:{}", req, e.getMessage());

                }
            }
        }).start();

        return "";
    }

    @ApiOperation(value = "7.3.0 app获取所有已出库单号")
    @GetMapping("/finishList")
    @ActiveFastJsonProfile(groups = "list", clazz = Delivery.class)
    public List<Delivery> finishList() {
        LambdaQueryWrapper<Delivery> queryWrapper = new LambdaQueryWrapper<>();
        // 发货单状态  1:初始化 2 已发货
        queryWrapper.eq(Delivery::getStatus, 2);
        List<Delivery> data = this.deliveryService.list(queryWrapper);
        if (data != null && data.size() > 0) {
            for (Delivery vo : data) {
                vo.setDetailList(this.getDetailList(vo.getId()));
            }
        }
        return data;
    }

    private List<DeliveryDetail> getDetailList(Integer deliveryId) {
        LambdaQueryWrapper<DeliveryDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeliveryDetail::getDeliveryId, deliveryId);
        return this.deliveryDetailService.list(queryWrapper);
    }

    @ApiOperation(value = "7.3.2 app修改发货单（回退登记）")
    @PostMapping("/update")
    @ActiveFastJsonProfile(groups = "list", clazz = Delivery.class)
    public Object update(@RequestBody @Valid DeliveryUpdateReq req, @CurrentUser CacheUserVO user) {
        this.deliveryService.update(req, user);
        return "";
    }

}
