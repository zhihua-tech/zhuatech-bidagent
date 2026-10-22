/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.workbench;

import static cn.zhuatech.bidagent.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "asOf", "requiredTypes", "documents");
    var asOf = date(text(r, "asOf", 10));
    var required = strings(r, "requiredTypes", 1, 100);
    if (new HashSet<>(required).size() != required.size())
      throw new IllegalArgumentException("必需类型重复");
    Map<String, Map<String, Object>> docs = new HashMap<>();
    for (var d : rows(r, "documents", 0, 100)) {
      keys(d, "type", "version", "validFrom", "validTo");
      String type = text(d, "type", 100);
      id(d, "version");
      var from = date(text(d, "validFrom", 10));
      var to = date(text(d, "validTo", 10));
      if (from.isAfter(to)) throw new IllegalArgumentException("文件有效期倒置");
      if (docs.putIfAbsent(type, d) != null) throw new IllegalArgumentException("同一类型只能选择一个待审版本");
    }
    List<Map<String, Object>> out = new ArrayList<>();
    int valid = 0;
    for (String type : required) {
      var d = docs.get(type);
      String status =
          d == null
              ? "MISSING"
              : asOf.isBefore(date(d.get("validFrom").toString()))
                  ? "NOT_YET_VALID"
                  : asOf.isAfter(date(d.get("validTo").toString())) ? "EXPIRED" : "VALID";
      if (status.equals("VALID")) valid++;
      out.add(Map.of("type", type, "status", status, "version", d == null ? "" : d.get("version")));
    }
    return Map.of(
        "completeAndCurrent",
        valid == required.size(),
        "validCount",
        valid,
        "documents",
        out,
        "authenticityVerified",
        false);
  }
}
