package com.px.coinage.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.px.coinage.core.annotation.CurrentUser;
import com.px.coinage.core.aop.annotation.Operation;
import com.px.coinage.core.constants.Constants;
import com.px.coinage.core.controller.BaseController;
import com.px.coinage.core.interceptor.Create;
import com.px.coinage.core.interceptor.Update;
import com.px.coinage.core.req.IdReq;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.core.vo.OptVO;
import com.px.coinage.sys.model.Org;
import com.px.coinage.sys.req.OrgListReq;
import com.px.coinage.sys.req.OrgReq;
import com.px.coinage.sys.service.IOrgService;
import com.px.coinage.sys.vo.OrgListVO;
import com.px.coinage.sys.vo.OrgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织机构Controller
 */
@Api(value = "组织机构接口", tags = "015_组织机构")
@RestController
@RequestMapping(value = "/org")
public class OrgController extends BaseController {

    @Autowired
    private IOrgService orgService;

    /**
     * 组织机构详情
     */
    @ApiOperation(value = "组织机构详情")
    @PostMapping("/get")
    public OrgVO get(@RequestBody @Valid IdReq req) {
        OrgVO data = null;
        Org model = this.orgService.getById(req.getId());
        data = new OrgVO();
        BeanUtils.copyProperties(model, data);
        return data;
    }

    /**
     * 组织机构分页列表
     */
    @ApiOperation(value = "组织机构分页列表")
    @PostMapping("/list")
    public IPage<OrgListVO> list(@RequestBody OrgListReq req, @CurrentUser CacheUserVO user) {
        IPage<OrgListVO> data = this.orgService.queryPageList(req, user.getTenantId());
        return data;
    }

    /**
     * 新增组织机构
     */
    @ApiOperation(value = "新增组织机构")
    @Operation(value = "新增组织机构")
    @PostMapping("/add")
    public Object add(@RequestBody @Validated({Create.class}) OrgReq req, @CurrentUser CacheUserVO user) {
        Org model = new Org();
        BeanUtils.copyProperties(req, model);
        model.setTenantId(user.getTenantId());
        model.setCreateBy(user.getRealName());
        this.orgService.save(model);
        return "";
    }

    /**
     * 修改组织机构
     */
    @ApiOperation(value = "修改组织机构")
    @Operation(value = "修改组织机构")
    @PostMapping("/update")
    public Object update(@RequestBody @Validated({Update.class}) OrgReq req, @CurrentUser CacheUserVO user) {
        Org model = this.orgService.getById(req.getId());
        BeanUtils.copyProperties(req, model);
        this.orgService.updateById(model);
        return "";
    }

    /**
     * 修改组织机构状态
     */
    @ApiOperation(value = "修改组织机构状态")
    @Operation(value = "修改组织机构状态")
    @PostMapping("/status")
    public Integer status(@RequestBody @Valid IdReq req, @CurrentUser CacheUserVO user) {
        Org model = this.orgService.getById(req.getId());
        if (model.getStatus() != null && model.getStatus().intValue() == Constants.STATUS_FORBIDDEN) {
            model.setStatus(Constants.STATUS_USABLE);
        } else {
            model.setStatus(Constants.STATUS_FORBIDDEN);
        }
        this.orgService.updateById(model);
        return model.getStatus();
    }

    /**
     * 删除组织机构
     */
    @ApiOperation(value = "删除组织机构")
    @Operation(value = "删除组织机构")
    @PostMapping("/delete")
    public Object delete(@RequestBody @Valid IdReq req, @CurrentUser CacheUserVO user) {
        Org model = this.orgService.getById(req.getId());
        model.setDelFlag(Constants.STATUS_FORBIDDEN);
        this.orgService.updateById(model);
        return "";
    }

    /**
     * 组织机构选项
     */
    @ApiOperation(value = "组织机构选项")
    @GetMapping("/opt")
    public List<OptVO> opt(@CurrentUser CacheUserVO user) {
        List<OptVO> data = this.orgService.opt(user.getTenantId());
        return data;
    }

}
