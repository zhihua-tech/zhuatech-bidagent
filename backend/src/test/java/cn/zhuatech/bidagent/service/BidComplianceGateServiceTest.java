/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class BidComplianceGateServiceTest {
    private final BidComplianceGateService service = new BidComplianceGateService();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void allowsControlledBidSubmission() {
        var r = service.evaluate(new BidComplianceGateService.Request("BID-001", 24, 2800, 2000, true, true, true, true));
        assertEquals("SUBMIT", r.decision()); assertTrue(r.submissionAllowed()); assertTrue(r.blockers().isEmpty());
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void escalatesLateNonCompliantBid() {
        var r = service.evaluate(new BidComplianceGateService.Request("BID-002", 1, 1200, 2000, false, false, false, false));
        assertEquals("ESCALATE", r.decision()); assertEquals(6, r.blockers().size()); assertFalse(r.submissionAllowed());
    }
}
