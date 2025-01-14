package com.px.coinage.workshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.coinage.workshop.dao.DeliveryDetailDao;
import com.px.coinage.workshop.model.DeliveryDetail;
import com.px.coinage.workshop.service.IDeliveryDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * -ServiceImpl
 */
@Service
public class DeliveryDetailServiceImpl extends ServiceImpl<DeliveryDetailDao, DeliveryDetail>
        implements IDeliveryDetailService {
    @Override
    public List<DeliveryDetail> queryList(QueryWrapper<DeliveryDetail> queryWrapper) {
        return this.baseMapper.querylist(queryWrapper);
    }
}
