/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.agent;
import org.springframework.stereotype.Component; import java.util.List; import java.util.Map;
/**
 * 企业智能投标协同平台运行边界；默认演示执行器不连接真实模型、业务系统或外部通信渠道。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface AgentRuntime {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 AgentResult run(AgentRequest request);
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 record AgentRequest(String objective,Map<String,String> context){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 record AgentStep(String name,String status,String evidence){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 record AgentResult(String runtime,String summary,List<AgentStep> steps,Map<String,Object> metrics){}
}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component class DemoAgentRuntime implements AgentRuntime {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public AgentResult run(AgentRequest request){
  return new AgentResult("bid-copilot-demo","已完成招标条款拆解与应答覆盖检查，价格承诺和资质偏离等待投标负责人确认。",List.of(new AgentStep("条款拆解","COMPLETED","识别 48 条强制要求"),new AgentStep("证据匹配","COMPLETED","关联 16 份资质与案例"),new AgentStep("商务审批","PENDING","等待价格与交期确认")),Map.of("evidenceItems",16,"suggestedActions",5,"objectiveLength",request.objective().length()));
 }
}
