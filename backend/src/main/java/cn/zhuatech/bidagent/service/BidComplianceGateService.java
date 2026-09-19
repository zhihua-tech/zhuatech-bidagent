/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class BidComplianceGateService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result evaluate(Request request) {
        List<String> blockers = new ArrayList<>();
        if (!request.mandatoryDocumentsComplete()) blockers.add("强制投标文件不完整");
        if (!request.conflictDeclarationSigned()) blockers.add("利益冲突声明未签署");
        if (!request.signingAuthorityValid()) blockers.add("签署授权无效");
        if (request.hoursToDeadline() < 2) blockers.add("距离截标不足两小时，需应急复核");
        if (request.expectedMarginBps() < request.minimumMarginBps()) blockers.add("预期毛利低于授权底线");
        if (!request.securityReviewPassed()) blockers.add("信息安全与数据条款复核未通过");
        String decision = blockers.isEmpty() ? "SUBMIT" : request.hoursToDeadline() < 2 ? "ESCALATE" : "BLOCKED";
        return new Result(request.bidNo(), decision, blockers.isEmpty(), List.copyOf(blockers),
                List.of("DOCUMENTS", "CONFLICT", "AUTHORITY", "MARGIN", "SECURITY"));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String bidNo, @Min(0) int hoursToDeadline,
                          @Min(-10000) int expectedMarginBps, @Min(-10000) int minimumMarginBps,
                          boolean mandatoryDocumentsComplete, boolean conflictDeclarationSigned,
                          boolean signingAuthorityValid, boolean securityReviewPassed) {
        /**
         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
         */
        public Request {
            if (bidNo == null || bidNo.isBlank()) throw new IllegalArgumentException("bidNo is required");
            if (hoursToDeadline < 0) throw new IllegalArgumentException("hoursToDeadline must be non-negative");
        }
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String bidNo, String decision, boolean submissionAllowed,
                         List<String> blockers, List<String> controlsChecked) {}
}
