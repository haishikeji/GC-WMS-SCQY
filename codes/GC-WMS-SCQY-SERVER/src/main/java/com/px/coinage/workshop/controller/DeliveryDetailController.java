package com.px.coinage.workshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.coinage.core.annotation.ActiveFastJsonProfile;
import com.px.coinage.core.annotation.CurrentUser;
import com.px.coinage.core.controller.BaseController;
import com.px.coinage.core.req.IdReq;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.workshop.model.DeliveryDetail;
import com.px.coinage.workshop.req.DeliveryDetailListReq;
import com.px.coinage.workshop.service.IDeliveryDetailService;
import com.px.coinage.workshop.vo.DeliveryDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * -Controller
 */
@Api(value = "接口", tags = {"接口"})
@RestController
@RequestMapping(value = "/deliveryDetail")
public class DeliveryDetailController extends BaseController {

    @Autowired
    private IDeliveryDetailService deliveryDetailService;

    /**
     * 分页列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("/list")
    @ActiveFastJsonProfile(groups = "list", clazz = DeliveryDetail.class)
    public List<DeliveryDetail> list(@RequestBody DeliveryDetailListReq req, @CurrentUser CacheUserVO user) {
        QueryWrapper<DeliveryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delivery_id", req.getDeliveryId());
        queryWrapper.orderByAsc("serial_number");
        List<DeliveryDetail> data = this.deliveryDetailService.queryList(queryWrapper);
        return data;
    }

    @ApiOperation(value = "-通过ID查询单条数据")
    @PostMapping("/get")
    @ActiveFastJsonProfile(groups = "get", clazz = DeliveryDetail.class)
    public DeliveryDetailVO get(@RequestBody @ApiParam(value = "主键请求bean") @Valid IdReq req) {
        DeliveryDetailVO data = null;
        DeliveryDetail model = this.deliveryDetailService.getById(req.getId());
        data = new DeliveryDetailVO();
        BeanUtils.copyProperties(model, data);
        return data;
    }

}
