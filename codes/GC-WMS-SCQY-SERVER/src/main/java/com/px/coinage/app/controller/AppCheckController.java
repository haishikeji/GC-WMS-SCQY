package com.px.coinage.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.px.coinage.app.req.CheckInfoReq;
import com.px.coinage.app.req.CheckShopOutInConfirmReq;
import com.px.coinage.app.req.CheckWareInConfirmReq;
import com.px.coinage.app.req.CreateCheckOrderNoReq;
import com.px.coinage.app.req.vo.CheckDetailVO;
import com.px.coinage.app.vo.CheckInfoVO;
import com.px.coinage.core.annotation.CurrentUser;
import com.px.coinage.core.controller.BaseController;
import com.px.coinage.core.vo.CacheUserVO;
import com.px.coinage.workshop.model.BaseInfo;
import com.px.coinage.workshop.model.Check;
import com.px.coinage.workshop.req.CheckReq;
import com.px.coinage.workshop.req.OrderNoReq;
import com.px.coinage.workshop.service.ICheckDetailService;
import com.px.coinage.workshop.service.ICheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * -Controller
 */
@Api(value = "App-抽检接口", tags = {"App-抽检"})
@RestController
@RequestMapping(value = "/app/check")
public class AppCheckController extends BaseController {

    @Autowired
    private ICheckService checkService;

    @Autowired
    private ICheckDetailService checkDetailService;

    @ApiOperation(value = "8.2 app生成抽检单号")
    @PostMapping("/createOrderNo")
    public String createOrderNo(@RequestBody @Valid CreateCheckOrderNoReq req) {
        String data = this.checkService.creadeOutOrderNo(req.getSecuritiesId(), req.getDeptId());
        return data;
    }

    @ApiOperation(value = "8.2.1 app获取总库中品名选项")
    @GetMapping("/securitiesOpt")
    public List<BaseInfo> securitiesOpt() {
        List<BaseInfo> data = this.checkService.securitiesOpt();
        return data;
    }


    @ApiOperation(value = "8.3 app抽检登记/抽检出库")
    @PostMapping("/register")
    public Object add(@RequestBody @Valid CheckReq req, @CurrentUser CacheUserVO user) {
        this.checkService.wareOut(req, user);
        return "";
    }

    @ApiOperation(value = "8.4.0 app获取所有抽检单号")
    @GetMapping("/list")
    public List<Check> list() {
        LambdaQueryWrapper<Check> queryWrapper = new LambdaQueryWrapper<>();
        // 1:初识创建 2:抽检中  3: 抽检完成（仅当qty= qty_product时）
        queryWrapper.ne(Check::getStatus, 3);
        List<Check> data = this.checkService.list(queryWrapper);
        return data;
    }

    @ApiOperation(value = "8.4.1 app根据抽检单号获取抽检单信息")
    @PostMapping("/getInfo")
    public CheckInfoVO getInfo(@RequestBody @Valid CheckInfoReq req) {
        CheckInfoVO data = new CheckInfoVO();
        Check model = this.checkService.getByOrderNo(req.getOrderNo());
        BeanUtils.copyProperties(model, data);
        List<CheckDetailVO> initList = this.checkDetailService.getInitList(data.getId());
        data.setInitList(initList);
        // FIXME
        List<CheckDetailVO> opList = this.checkDetailService.getOpList(data.getId());
        data.setOpList(opList);
        return data;
    }

    @ApiOperation(value = "8.4.2 app主库抽检返回登记入库确认")
    @PostMapping("/wareInConfirm")
    public Object wareInConfirm(@RequestBody @Valid CheckWareInConfirmReq req, @CurrentUser CacheUserVO user) {
        this.checkService.wareIn(req, user);
        return "";
    }

    @ApiOperation(value = "9.1.1 app根据抽检出库单，获取抽检单入库列表信息")
    @PostMapping("/getShopInInfo")
    public CheckInfoVO getShopInInfo(@RequestBody @Valid OrderNoReq req) {
        CheckInfoVO data = new CheckInfoVO();
        Check model = this.checkService.getByOrderNo(req.getOrderNo());
        BeanUtils.copyProperties(model, data);
        List<CheckDetailVO> initList = this.checkDetailService.getInitList(data.getId());
        data.setInitList(initList);
        List<CheckDetailVO> shopReceiveList = this.checkDetailService.getShopReceiveList(data.getId());
        data.setShopReceiveList(shopReceiveList);
        List<CheckDetailVO> shopInProList = this.checkDetailService.getShopInProList(data.getId());
        data.setShopInProList(shopInProList);
        return data;
    }

    @ApiOperation(value = "9.1.2 app抽检入库确认")
    @PostMapping("/shopInConfirm")
    public Object shopInConfirm(@RequestBody @Valid CheckShopOutInConfirmReq req, @CurrentUser CacheUserVO user) {
        this.checkService.shopIn(req, user);
        return "";
    }

    @ApiOperation(value = "9.2.1 app根据抽检单号获取抽检出库信息")
    @PostMapping("/getShopOutInfo")
    public CheckInfoVO getShopOutInfo(@RequestBody @Valid OrderNoReq req) {
        CheckInfoVO data = new CheckInfoVO();
        Check model = this.checkService.getByOrderNo(req.getOrderNo());
        BeanUtils.copyProperties(model, data);
        List<CheckDetailVO> initList = this.checkDetailService.getInitList(data.getId());
        data.setInitList(initList);
        List<CheckDetailVO> shopOutMachineList = this.checkDetailService.getShopOutMachineList(data.getId());
        data.setShopOutMachineList(shopOutMachineList);
        List<CheckDetailVO> shopOutList = this.checkDetailService.getShopOutList(data.getId());
        data.setShopOutList(shopOutList);
        return data;
    }

    @ApiOperation(value = "9.2.2 app抽检出库确认")
    @PostMapping("/shopOutConfirm")
    public Object shopOutConfirm(@RequestBody @Valid CheckShopOutInConfirmReq req, @CurrentUser CacheUserVO user) {
        this.checkService.shopOut(req, user);
        return "";
    }

}
