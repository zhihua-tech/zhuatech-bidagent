/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标书建议安全门：价格承诺、资质缺口和偏离项必须由投标负责人确认。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class BidProposalGuardService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ProposalRequest(
            @NotBlank String tenderNo,
            @Min(0) @Max(500) int requiredClauses,
            @Min(0) @Max(500) int referencedClauses,
            boolean containsPriceCommitment,
            boolean qualificationGap,
            boolean bidOwnerApproved) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ProposalDecision(
            boolean draftAllowed,
            String route,
            int coverage,
            List<String> controls) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ProposalDecision inspect(ProposalRequest request) {
        int coverage = request.requiredClauses() == 0 ? 0
                : Math.min(100, request.referencedClauses() * 100 / request.requiredClauses());
        boolean humanReview = request.containsPriceCommitment()
                || request.qualificationGap()
                || coverage < 90;
        boolean allowed = !humanReview || request.bidOwnerApproved();
        String route = request.qualificationGap() ? "QUALIFICATION_REVIEW"
                : request.containsPriceCommitment() ? "COMMERCIAL_APPROVAL"
                : coverage < 90 ? "CLAUSE_COMPLETION" : "DRAFT_READY";
        return new ProposalDecision(allowed, route, coverage, List.of(
                "逐条引用招标文件原文与页码",
                "价格、交期和服务承诺禁止自动发布",
                "保留偏离表、审批意见和最终版本指纹"));
    }
}
