package com.zhaogang.other.cat;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhaogang.other.test.feishu.AbstractFeiShuMessage;
import com.zhaogang.other.test.feishu.FSPostMessageBody;
import com.zhaogang.other.test.feishu.FSTextMessageDTO;
import com.zhaogang.other.test.feishu.FeiShuMsgTypeEnum;
import com.zhaogang.other.test.feishu.FeiShuReboot;

import lombok.Data;
import lombok.ToString;

/**
 * cat 的告警服务端
 * 
 * @author jacks
 * @date 2022/3/21
 */
@RestController
@RequestMapping("/cat_alarms")
public class CatAlarmController {
    private static final String secret = "ownQkqwZsn4PbM292eTKIg";
    private static final String hookAddress =
        "https://open.feishu.cn/open-apis/bot/v2/hook/a608da89-1f55-40ba-b7cc-fc4f0a065860";
    private static final String MSG_TEMPLATE =
        "{\"post\":{\"zh_cn\":{\"title\":\"%s\",\"content\":[[{\"tag\":\"text\",\"text\":\"%s\"}],[{\"tag\":\"text\",\"text\":\"%s \"},{\"tag\":\"a\",\"href\":\"%s\",\"text\":\"%s\"}],[{\"tag\":\"text\",\"text\":\"%s\"}],[{\"tag\":\"at\",\"user_id\":\"all\",\"user_name\":\"所有人\"}]]}}}";
    private static final String catAddress = "localhost:8080";
    private static final Logger LOGGER = LoggerFactory.getLogger(CatAlarmController.class);

    @Resource
    private HttpClient httpClient;

    /**
     * alter-manager 发送 post 请求，注意请求体的参数格式
     */
    @PostMapping("/prometheus_alert")
    public void prometheusAlert(@RequestBody PrometheusMessage message) {
        System.out.println("================== prometheusAlert ========================");
//        System.out.println(JSON.toJSONString(message));

         // 单个报警请求外层和内层一样
        JSONObject commonAnnotations = message.getCommonAnnotations();
        String summary = commonAnnotations.getString("summary");
        String description = commonAnnotations.getString("description");
//        System.out.println(LocalDateTime.now() + "_" + summary + ": " + description);


        // 聚合告警需要解析内部内容
        List<PrometheusAlertItem> alerts = message.getAlerts();
        if (CollectionUtils.isEmpty(alerts)) {
            System.out.println("alters is empty");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (PrometheusAlertItem alert : alerts) {
            JSONObject annotations = alert.getAnnotations();
            // summary: description 格式拼凑
            sb.append(annotations.get("summary")).append(" ").append(annotations.getString("description")).append("\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * 用 form-data 接受数据
     */
    @PostMapping(path = "/mail", produces = {"text/plain"})
    public String mailAlarms(MailAlarmMessage message) throws IOException {
        System.out.println("=================== mailAlarms =======================");
        String value = message.getValue();
        System.out.println(value);
        System.out.println();

        boolean hasLink = value.contains("<a href=");

        AbstractFeiShuMessage msg =
            hasLink ? genPostMessage(message) : FeiShuReboot.genTextMessage(new FSTextMessageDTO(value, "all", "所有人"));
        System.out.println("消息体：" + JSON.toJSONString(msg));

        if (msg == null) {
            return "failed";
        }

        // 2.生成一个get请求
        HttpPost httpPost = new HttpPost(hookAddress);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setEntity(new StringEntity(JSON.toJSONString(msg), StandardCharsets.UTF_8));
        HttpResponse execute = httpClient.execute(httpPost);

        if (execute != null) {
            try {
                // 通过这个方法进行连接的释放，一定要做
                EntityUtils.consume(execute.getEntity());
            } catch (IOException e) {
                LOGGER.error(">> consume entity failed", e);
            }
        }

        // 响应内容必须和告警服务端的 successCode 保持一致（并且是 String 类型），否则 cat 断言失败
        return "200";
    }

    @PostMapping(path = "/wechat", produces = {"text/plain"})
    public String wechatAlarms(WeChatAlarmMessage message) {
        System.out.println("=================== wechatAlarms =======================");
        System.out.println(message.toString());
        System.out.println();

        // 响应内容必须和告警服务端的 successCode 保持一致（并且是 String 类型），否则 cat 断言失败
        return "success";
    }

    /**
     * cat 原始信息： PingController.MailAlarmMessage(key=title,body, re=test@test.com, to=jacksonary@163.com, value=[CAT
     * Transaction告警] [项目: cat] [监控项: URL-All-count],[CAT Transaction告警: cat URL All] : [实际值:14 ] [最大阈值: 1
     * ][告警时间:2022-03-22 15:28:00]<br/>
     * [时间: 2022-03-22 15:28]
     * <a href='http://cat-web-server/cat/r/t?domain=cat&type=URL&name=All&date=2022032215'>点击此处查看详情</a><br/>
     * <br/>
     * [告警间隔时间]5分钟)
     *
     * @param msgDTO
     * @return
     */
    private FSPostMessageBody genPostMessage(MailAlarmMessage msgDTO) {
        if (msgDTO == null || StringUtils.isBlank(msgDTO.getValue())) {
            return null;
        }

        long timeMillis = System.currentTimeMillis() / 1000;
        String sign = FeiShuReboot.genSign(timeMillis);
        if (StringUtils.isBlank(sign)) {
            return null;
        }

        String[] split = msgDTO.getValue().replace("\n", "<br/>").split("<br/>");

        boolean hasLink = false;
        boolean isFirst = true;
        List<String> items = new ArrayList<>(8);
        for (String s : split) {
            if (isFirst) {
                String[] titleAndBody = s.split(",");
                items.add(titleAndBody[0]);
                items.add(titleAndBody[1]);
                isFirst = false;
                continue;
            }

            if (hasLink) {
                items.add(s);
                continue;
            }

            int i = s.indexOf("<a href='");
            if (i == -1) {
                items.add(s);
                continue;
            }
            String replace = s.substring(i + 9, s.indexOf("'>")).replace("cat-web-server", catAddress);
            items.add(replace);
            items.add(s.substring(s.indexOf(">") + 1, s.indexOf("</a>")));
            hasLink = true;
        }

        String msgContent = String.format(MSG_TEMPLATE, items.toArray(new String[items.size()]));

        FSPostMessageBody msg = new FSPostMessageBody();
        msg.setTimestamp(String.valueOf(timeMillis));
        msg.setSign(sign);
        msg.setMsgType(FeiShuMsgTypeEnum.POST.getCode());
        msg.setContent(msgContent);

        return msg;
    }

    /**
     * 这里定义对象时一定要和告警服务端配置里面的参数名保持一致才能接收到
     */
    @ToString
    @Data
    static class MailAlarmMessage {
        String key;
        String re;
        String to;
        String value;
    }

    @ToString
    @Data
    static class WeChatAlarmMessage {
        String domain;
        String email;
        String title;
        String content;
        String type;
    }

    /**
     * @formatter:off 
     * prometheus 告警信息的对象
     * {
     *   "version": "4",
     *   "groupKey": <string>,    // key identifying the group of alerts (e.g. to deduplicate)
     *   "status": "<resolved|firing>",
     *   "receiver": <string>,
     *   "groupLabels": <object>,
     *   "commonLabels": <object>,
     *   "commonAnnotations": <object>,
     *   "externalURL": <string>,  // backlink to the Alertmanager.
     *   "alerts": [
     *     {
     *       "labels": <object>,
     *       "annotations": <object>,
     *       "startsAt": "<rfc3339>",
     *       "endsAt": "<rfc3339>"
     *     }
     *   ]
     * }
     *
     * 示例报警：
     * CatAlarmController.PrometheusMessage(version=4, groupKey={}/{alertname="rds_nebula"}:{job="m56-dev-supernova-middleware-monitor-cloudwatch-exporter"}, status=firing, receiver=weiguo, groupLabels={"job":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter"}, commonLabels={"container":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter","severity":"none","instance":"10.192.33.14:5000","pod":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter-84mx9z","alertname":"rds_nebula","dimension_DBInstanceIdentifier":"m56-dev-nebula","endpoint":"metrics","account_id":"143967271279","service":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter","tag_Name":"m56-dev-nebula","name":"arn:aws-cn:rds:cn-north-1:143967271279:db:m56-dev-nebula","namespace":"dev","prometheus":"cattle-monitoring-system/rancher-monitoring-prometheus","job":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter","region":"cn-north-1"}, commonAnnotations={"message":"rds_nebulan_dev 可用空间不足 20G"}, externalURL=https://rancher.momenta.works/k8s/clusters/c-7xr9b/api/v1/namespaces/cattle-monitoring-system/services/http:rancher-monitoring-alertmanager:9093/proxy, alerts=[CatAlarmController.PrometheusAlertItem(labels={"container":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter","severity":"none","instance":"10.192.33.14:5000","pod":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter-84mx9z","alertname":"rds_nebula","dimension_DBInstanceIdentifier":"m56-dev-nebula","endpoint":"metrics","account_id":"143967271279","service":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter","tag_Name":"m56-dev-nebula","name":"arn:aws-cn:rds:cn-north-1:143967271279:db:m56-dev-nebula","namespace":"dev","prometheus":"cattle-monitoring-system/rancher-monitoring-prometheus","job":"m56-dev-supernova-middleware-monitor-cloudwatch-exporter","region":"cn-north-1"}, annotations={"message":"rds_nebulan_dev 可用空间不足 20G"}, startsAt=2022-03-25T03:48:39.301Z, endsAt=0001-01-01T00:00:00Z)])
     *
     * @formatter:on 
     */
    @ToString
    @Data
    static class PrometheusMessage implements Serializable {
        private static final long serialVersionUID = 3456522544142241586L;
        private String version;
        private String groupKey;
        private String status;
        private String receiver;
        private JSONObject groupLabels;
        private JSONObject commonLabels;
        private JSONObject commonAnnotations;
        private String externalURL;
        /**
         * 如果有 group，会把结果放在这里面
         */
        private List<PrometheusAlertItem> alerts;
    }

    @ToString
    @Data
    static class PrometheusAlertItem implements Serializable {
        private static final long serialVersionUID = 8300402357375973473L;
        private JSONObject labels;
        private JSONObject annotations;
        /**
         * 时间格式遵循 RFC3339，形如 2022-03-25T03:48:39.301Z
         */
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
    }

}
