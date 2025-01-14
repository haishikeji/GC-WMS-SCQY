package com.px.coinage.workshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.coinage.app.req.ShopConfirmReq;
import com.px.coinage.app.req.WareConfirmReq;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.workshop.model.WorkshopOutbound;
import com.px.coinage.workshop.req.WorkshopOutboundAddReq;
import com.px.coinage.workshop.req.WorkshopOutboundReq;

import java.text.ParseException;
import java.util.List;

/**
 * -I车间出库单据（库房解缴|返检单据|）Service
 */
public interface IWorkshopOutboundService extends IService<WorkshopOutbound> {

    /**
     * 内部出库
     *
     * @param req
     * @param user
     */
    void eliminateOut(WorkshopOutboundReq req, CacheUserVO user) throws ParseException;

    /**
     * 外部出库
     *
     * @param req
     * @param user
     */
    void shopOut(WorkshopOutboundReq req, CacheUserVO user) throws ParseException;

    void returnCheckWareOut(WareConfirmReq req, CacheUserVO user);

    void returnCheckShopIn(WareConfirmReq req, CacheUserVO user);

    List<String> wareIn(ShopConfirmReq req, CacheUserVO user);

    void create(WorkshopOutboundAddReq req, CacheUserVO user);

    String creadeOutOrderNo(String securities, Integer deptId);

    void delete(Integer id);

    void update(WorkshopOutboundAddReq req, CacheUserVO user);

}
