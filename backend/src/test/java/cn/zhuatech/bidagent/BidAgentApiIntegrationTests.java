/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent;
import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc; import java.util.regex.*; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest @AutoConfigureMockMvc class BidAgentApiIntegrationTests {
 @Autowired MockMvc mvc; private String operatorToken; private String plannerToken;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @BeforeEach void login()throws Exception{operatorToken=token("operator","Demo@2026");plannerToken=token("planner","Demo@2026");}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 private String token(String u,String p)throws Exception{String json=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\""+u+"\",\"password\":\""+p+"\"}")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();Matcher matcher=Pattern.compile("\\\"token\\\":\\\"([^\\\"]+)\\\"").matcher(json);if(!matcher.find())throw new AssertionError("登录响应中缺少 token");return matcher.group(1);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void operatorCanReadWorkspace()throws Exception{mvc.perform(get("/api/shopfloor/dashboard").header("Authorization","Bearer "+operatorToken)).andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.metrics[0].label").value("本周投标项目"));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void plannerCanReadQueue()throws Exception{mvc.perform(get("/api/admin/work-orders").header("Authorization","Bearer "+plannerToken)).andExpect(status().isOk()).andExpect(jsonPath("$.data.length()").value(3));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void operatorCanRunPreview()throws Exception{mvc.perform(post("/api/shopfloor/agent-preview").header("Authorization","Bearer "+operatorToken).contentType(MediaType.APPLICATION_JSON).content("{\"objective\":\"核对招标条款覆盖情况\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.data.runtime").value("bid-copilot-demo")).andExpect(jsonPath("$.data.steps.length()").value(3));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void operatorCanUseDomainGuard()throws Exception{mvc.perform(post("/api/shopfloor/proposal-guard").header("Authorization","Bearer "+operatorToken).contentType(MediaType.APPLICATION_JSON).content("{\"tenderNo\":\"BID-2026-081\",\"requiredClauses\":48,\"referencedClauses\":48,\"containsPriceCommitment\":false,\"qualificationGap\":false,\"bidOwnerApproved\":false}")).andExpect(status().isOk()).andExpect(jsonPath("$.data.route").value("DRAFT_READY")).andExpect(jsonPath("$.data.coverage").value(100));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void anonymousRequestIsDenied()throws Exception{mvc.perform(get("/api/admin/dashboard")).andExpect(status().isUnauthorized());}
}
