package com.px.coinage.workshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.coinage.app.req.DeliveryUpdateReq;
import com.px.coinage.app.req.DeliveryWareConfirmReq;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.workshop.model.Delivery;

/**
 * -发货单主表Service
 */
public interface IDeliveryService extends IService<Delivery> {
    void wareConfirm(DeliveryWareConfirmReq req, CacheUserVO user);

    void update(DeliveryUpdateReq req, CacheUserVO user);

    void print(Integer id, CacheUserVO user);

}
