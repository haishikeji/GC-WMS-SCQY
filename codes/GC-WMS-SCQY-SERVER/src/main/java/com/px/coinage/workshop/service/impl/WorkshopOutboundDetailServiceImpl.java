package com.px.coinage.workshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.coinage.workshop.dao.WorkshopOutboundDetailDao;
import com.px.coinage.workshop.model.WorkshopOutboundDetail;
import com.px.coinage.workshop.req.vo.BaseInfoVO;
import com.px.coinage.workshop.service.IWorkshopOutboundDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * -ServiceImpl
 */
@Service
public class WorkshopOutboundDetailServiceImpl extends ServiceImpl<WorkshopOutboundDetailDao, WorkshopOutboundDetail>
        implements IWorkshopOutboundDetailService {
    @Override
    public WorkshopOutboundDetail getBySerialNumber(String serialNumber, Integer outId) {
        LambdaQueryWrapper<WorkshopOutboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkshopOutboundDetail::getSerialNumber, serialNumber);
        queryWrapper.eq(WorkshopOutboundDetail::getOutId, outId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<BaseInfoVO> listVO(Integer outId) {
        LambdaQueryWrapper<WorkshopOutboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkshopOutboundDetail::getOutId, outId);
        return this.baseMapper.queryVO(queryWrapper);
    }

    @Override
    public long myCount(String serialNumber) {
        QueryWrapper<WorkshopOutboundDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rfid_workshop_outbound_detail.serial_number", serialNumber);
        // 单据类型： 1: 解缴单  2: 返检单
        queryWrapper.eq("rfid_workshop_outbound.order_type", 2);
        queryWrapper.eq("rfid_workshop_outbound.status", 1);
        return this.baseMapper.myCount(queryWrapper);
    }

    @Override
    public List<WorkshopOutboundDetail> querylist(QueryWrapper<WorkshopOutboundDetail> queryWrapper) {
        return this.baseMapper.querylist(queryWrapper);
    }


}
