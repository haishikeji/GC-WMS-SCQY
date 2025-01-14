package com.px.coinage.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.coinage.core.annotation.ActiveFastJsonProfile;
import com.px.coinage.core.annotation.CurrentUser;
import com.px.coinage.core.aop.annotation.Operation;
import com.px.coinage.core.constants.Constants;
import com.px.coinage.core.controller.BaseController;
import com.px.coinage.core.interceptor.Create;
import com.px.coinage.core.interceptor.Update;
import com.px.coinage.core.req.IdReq;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.core.vo.OptVO;
import com.px.coinage.sys.model.Tenant;
import com.px.coinage.sys.req.TenantListReq;
import com.px.coinage.sys.req.TenantReq;
import com.px.coinage.sys.service.ITenantService;
import com.px.coinage.sys.vo.TenantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 租户Controller
 */
@Api(value = "租户接口", tags = "011_租户")
@RestController
@RequestMapping(value = "/tenant")
public class TenantController extends BaseController {

    @Autowired
    private ITenantService tenantService;

    /**
     * 查询租户分页列表
     */
    @ApiOperation(value = "查询租户分页列表")
    @PostMapping("/list")
    @ActiveFastJsonProfile(groups = "list", clazz = Tenant.class)
    public IPage<Tenant> list(@RequestBody TenantListReq req) {
        LambdaQueryWrapper<Tenant> queryWrapper = new LambdaQueryWrapper<>();
        // if (StringUtils.isNotBlank(req.getId())) {
        // queryWrapper.like(Tenant::getName, req.getName());
        // }
        // queryWrapper.and(wq -> {
        // // 拼接sql
        // wq.like(Tenant::getName, req.getName())
        // .or()
        // .like(Tenant::getRealName, req.getName());
        // })
        Page<Tenant> page = new Page<>(req.getPage(), req.getRows());
        IPage<Tenant> data = this.tenantService.page(page, queryWrapper);
        return data;
    }

    /**
     * 租户详情
     */
    @ApiOperation(value = "租户详情")
    @PostMapping("/get")
    @ActiveFastJsonProfile(groups = "get", clazz = Tenant.class)
    public TenantVO get(@RequestBody @ApiParam(value = "主键请求bean") @Valid IdReq req) {
        TenantVO data = null;
        Tenant model = this.tenantService.getById(req.getId());
        data = new TenantVO();
        BeanUtils.copyProperties(model, data);
        return data;
    }

    /**
     * 租户选项
     */
    @ApiOperation(value = "租户选项")
    @GetMapping("/opt")
    public List<OptVO> opt() {
        List<OptVO> data = this.tenantService.opt();
        return data;
    }

    /**
     * 新增租户
     *
     * @param req {@link TenantReq}
     * @return
     */
    @ApiOperation(value = "新增租户")
    @Operation(value = "新增租户")
    @PostMapping("/add")
    public Object add(@RequestBody @ApiParam(value = "新增请求bean") @Validated({Create.class}) TenantReq req,
                      @CurrentUser CacheUserVO user) {
        // 验证是否租户管理员
        this.validAdmin(user);
        Tenant model = new Tenant();
        BeanUtils.copyProperties(req, model);
        model.setStatus(Constants.STATUS_USABLE);
        this.tenantService.save(model);
        return "";
    }

    /**
     * 修改租户
     */
    @ApiOperation(value = "修改租户")
    @Operation(value = "修改租户")
    @PostMapping("/update")
    public Object update(@RequestBody @ApiParam(value = "修改请求bean") @Validated({Update.class}) TenantReq req,
                         @CurrentUser CacheUserVO user) {
        // 验证是否租户管理员
        this.validAdmin(user);
        Tenant model = this.tenantService.getById(req.getId());
        BeanUtils.copyProperties(req, model);
        this.tenantService.updateById(model);
        return "";
    }

    /**
     * 修改租户状态
     */
    @ApiOperation(value = "修改租户状态")
    @Operation(value = "修改租户状态")
    @PostMapping("/status")
    public Integer status(@RequestBody @Valid IdReq req, @CurrentUser CacheUserVO user) {
        // 验证是否租户管理员
        this.validAdmin(user);
        Tenant model = this.tenantService.getById(req.getId());
        if (null != model.getStatus() && model.getStatus().intValue() == Constants.STATUS_FORBIDDEN) {
            model.setStatus(Constants.STATUS_USABLE);
        } else {
            model.setStatus(Constants.STATUS_FORBIDDEN);
        }
        this.tenantService.updateById(model);
        return model.getStatus();
    }

}
