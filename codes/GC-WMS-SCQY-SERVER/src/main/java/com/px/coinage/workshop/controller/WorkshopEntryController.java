package com.px.coinage.workshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.coinage.core.annotation.ActiveFastJsonProfile;
import com.px.coinage.core.annotation.CurrentUser;
import com.px.coinage.core.aop.annotation.Operation;
import com.px.coinage.core.aop.annotation.Permission;
import com.px.coinage.core.controller.BaseController;
import com.px.coinage.core.interceptor.Update;
import com.px.coinage.core.req.IdReq;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.workshop.model.WorkshopEntry;
import com.px.coinage.workshop.req.WorkshopEntryListReq;
import com.px.coinage.workshop.req.WorkshopEntryReq;
import com.px.coinage.workshop.service.IWorkshopEntryService;
import com.px.coinage.workshop.vo.WorkshopEntryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * -车间入库单（仅记录入库单，及返检单）Controller
 */
@Api(value = "车间入库单（仅记录入库单，及返检单）接口", tags = {"车间入库单（仅记录入库单，及返检单）接口"})
@RestController
@RequestMapping(value = "/workshopEntry")
public class WorkshopEntryController extends BaseController {

    @Autowired
    private IWorkshopEntryService workshopEntryService;

    //=========pc接口

    /**
     * 车间入库单（仅记录入库单，及返检单）分页列表
     */
    @ApiOperation(value = "车间入库单（仅记录入库单，及返检单）分页列表")
    @PostMapping("/list")
    @ActiveFastJsonProfile(groups = "list", clazz = WorkshopEntry.class)
    public IPage<WorkshopEntry> list(@RequestBody WorkshopEntryListReq req, @CurrentUser CacheUserVO user) {
        LambdaQueryWrapper<WorkshopEntry> queryWrapper = new LambdaQueryWrapper<>();
        Page<WorkshopEntry> page = new Page<>(req.getPage(), req.getRows());
        IPage<WorkshopEntry> data = this.workshopEntryService.page(page, queryWrapper);
        return data;
    }


    /**
     * 车间入库单（仅记录入库单，及返检单）详情
     */
    @ApiOperation(value = "车间入库单（仅记录入库单，及返检单）-通过ID查询单条数据")
    @PostMapping("/get")
    @ActiveFastJsonProfile(groups = "get", clazz = WorkshopEntry.class)
    public WorkshopEntryVO get(@RequestBody @ApiParam(value = "主键请求bean") @Valid IdReq req) {
        WorkshopEntryVO data = null;
        WorkshopEntry model = this.workshopEntryService.getById(req.getId());
        data = new WorkshopEntryVO();
        BeanUtils.copyProperties(model, data);
        return data;
    }


    /**
     * 修改车间入库单（仅记录入库单，及返检单）
     */
    @ApiOperation(value = "修改车间入库单（仅记录入库单，及返检单）")
    @Operation(value = "修改车间入库单（仅记录入库单，及返检单）")
    @PostMapping("/update")
    @Permission("workshopEntry:update")
    public Object update(@RequestBody @ApiParam(value = "修改请求bean") @Validated({Update.class}) WorkshopEntryReq req,
                         @CurrentUser CacheUserVO user) {
        // WorkshopEntry model = this.workshopEntryService.getById(req.getId());
        // BeanUtils.copyProperties(req, model);
        // // model.setUpdateBy(user.getName());
        // this.workshopEntryService.updateById(model);
        return "";
    }

    /**
     * 删除车间入库单（仅记录入库单，及返检单）
     */
    @ApiOperation(value = "删除车间入库单（仅记录入库单，及返检单）")
    @Operation(value = "删除车间入库单（仅记录入库单，及返检单）")
    @PostMapping("/delete")
    @Permission("workshopEntry:delete")
    public Object delete(@RequestBody @ApiParam(value = "主键请求bean") @Valid IdReq req, @CurrentUser CacheUserVO user) {
        WorkshopEntry model = this.workshopEntryService.getById(req.getId());
        // model.setDelFlag(Constants.STATUS_FORBIDDEN);
        // this.workshopEntryService.updateById(model);
        this.workshopEntryService.removeById(req.getId());
        return "";
    }

    // private static String[] codes = {"id", "orderNo", "orderType", "securitiesId", "securities",
    // "racketNum", "racketTotal", "boxNum", "boxTotal", "piecesTotal", "createTime", "operatorId",
    // "operatorCn", "checkId", "checker", "dept", "deptId"};
    // private static String[] names = {"主键ID", "入库单单号，当是机台入库的。", "1.车间入库", "品名ID", "品名", "整拍子数量",
    // "拍子总枚数（拍子数量*每拍子箱子数量*每箱子枚数）", "零箱数", "箱子含有总枚数（箱子数量*每箱枚数）", "零枚数量", "入库时间", "操作人ID", "操作人姓名",
    // "复核人id", "复核人", "目的地部门", "目的地部门ID"};

    // @Autowired
    // private CustomConfig customConfig;

    /**
     * 导出车间入库单（仅记录入库单，及返检单）
     */
    // @PostMapping("/exp")
    // public Object exp(@RequestBody IdsListReq req){
    // FileVO data = new FileVO();
    // LambdaQueryWrapper<WorkshopEntry> queryWrapper = new LambdaQueryWrapper<>();
    // List<WorkshopEntry> list = this.workshopEntryService.list(queryWrapper);
    // ExcelExportUtil<WorkshopEntry> excelExport = new ExcelExportUtil<>();
    // String fileName = "车间入库单（仅记录入库单，及返检单）.xlsx";
    // data.setName(fileName);
    // FileVO resp = excelExport.exportData(names, codes, list, fileName, customConfig.getAttaPath(),
    // customConfig.getAttaUrl());
    // data.setUrl(resp);
    // return data;
    // }

    /**
     * 导入车间入库单（仅记录入库单，及返检单）
     */
    // @PostMapping("/imp")
    // public Object imp(@RequestParam(required=true) MultipartFile file, @CurrentUser CacheUserVO
    // user){
    // Workbook workbook = ExcelUtil.readExcel(file);
    // List<WorkshopEntryAddReq> list = ExcelImport.convertToList(WorkshopEntryAddReq.class, workbook,
    // codes, names, 1, 1);
    // this.validData(list);
    // this.workshopEntry.batchAdd(list, user);
    // return "";
    // }


}
