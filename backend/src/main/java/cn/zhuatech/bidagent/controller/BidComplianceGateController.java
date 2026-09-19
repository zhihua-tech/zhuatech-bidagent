/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.controller;
import cn.zhuatech.bidagent.common.ApiResponse;
import cn.zhuatech.bidagent.service.BidComplianceGateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/bids")
public class BidComplianceGateController {
    private final BidComplianceGateService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BidComplianceGateController(BidComplianceGateService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/compliance-gate")
    public ApiResponse<BidComplianceGateService.Result> evaluate(@Valid @RequestBody BidComplianceGateService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
