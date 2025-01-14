package com.px.coinage.workshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.coinage.workshop.model.DeliveryPlan;
import com.px.coinage.workshop.model.DeliveryPlanDetail;
import com.px.coinage.workshop.req.DeliveryPlanReq;

import java.util.List;

/**
 * -Service
 */
public interface IDeliveryPlanService extends IService<DeliveryPlan> {

    boolean validSecurities(Integer getSecuritiesId, Integer id);

    /**
     * @param model
     * @param detailList 计划明细
     * @param type       是否修改跳号状态 true是 false否
     * @return
     */
    DeliveryPlan getModel(DeliveryPlan model, List<DeliveryPlanDetail> detailList, boolean type, Integer oldId);

    void updateModel(DeliveryPlanReq req);

    void addModel(DeliveryPlanReq req);

    void updateCrown(Integer id);

    void updateModelNew(DeliveryPlanReq req);

//    void add(DeliveryPlanReq req, CacheUserVO user)
//    void update(DeliveryPlanReq req, CacheUserVO user)

}
