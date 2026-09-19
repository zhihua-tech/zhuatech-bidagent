/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.config;
import cn.zhuatech.bidagent.model.*; import cn.zhuatech.bidagent.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration public class DataInitializer {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository records,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
  OperatingUnit first=units.save(new OperatingUnit("BID-EAST","华东投标组","商务投标中心",1800)),second=units.save(new OperatingUnit("BID-SOL","解决方案组","技术中心",1200)),third=units.save(new OperatingUnit("BID-RISK","商务风控组","商务投标中心",900));
  WorkRecord a=records.save(new WorkRecord("BID-260808-018","TENDER-CLOUD","某制造集团云平台建设项目",first,48,31,4,LocalDate.now().plusDays(2),WorkRecord.Status.RELEASED,"TENDER-V3")); WorkRecord b=records.save(new WorkRecord("BID-260808-012","TENDER-ERP","连锁零售 ERP 升级项目",second,36,36,0,LocalDate.now(),WorkRecord.Status.COMPLETED,"TENDER-V5")); WorkRecord c=records.save(new WorkRecord("BID-260808-021","TENDER-AI","园区企业知识助手项目",third,42,19,3,LocalDate.now().plusDays(3),WorkRecord.Status.RUNNING,"TENDER-V2"));
  resources.saveAll(List.of(new ResourceRegister("BID-KB-01","投标案例与资质库",second,ResourceRegister.Status.RUNNING,96),new ResourceRegister("BID-CLAUSE-02","招标条款解析器",first,ResourceRegister.Status.RUNNING,93),new ResourceRegister("BID-GUARD-03","承诺与偏离审查器",third,ResourceRegister.Status.ALARM,78)));
  reviews.saveAll(List.of(new ReviewRecord("REV-BID-028",a,"商务承诺",18,2,ReviewRecord.Result.PENDING,"沈闻"),new ReviewRecord("REV-BID-017",b,"条款覆盖",36,0,ReviewRecord.Result.PASSED,"周珩"),new ReviewRecord("REV-BID-039",c,"资质偏离",12,3,ReviewRecord.Result.FAILED,"方澄")));
  String demo=encoder.encode("Demo@2026"); users.saveAll(List.of(new UserAccount("operator",demo,"周珩",UserAccount.Role.DOMAIN_USER,"BID-EAST"),new UserAccount("planner",demo,"沈闻",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"投标评审负责人",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));
 };}}
