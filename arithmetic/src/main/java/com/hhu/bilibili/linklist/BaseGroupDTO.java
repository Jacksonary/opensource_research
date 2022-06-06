package com.hhu.bilibili.linklist;

import java.util.Objects;
import lombok.Data;

/**
 * @author jacks
 * @date 2021/7/14
 * @description 这个对象用作查询条件的分组
 */
@Data
public class BaseGroupDTO {
    private Integer domainId;
    private Long orgId;
    private Integer type;

    public static void main(String[] args) {
        BaseGroupDTO b1 = new BaseGroupDTO(1,1L, 2);
        BaseGroupDTO b2 = new BaseGroupDTO(1,1L, 2);
        System.out.println(b1.equals(b2));
    }

    public BaseGroupDTO(Integer domainId, Long orgId, Integer type) {
        this.domainId = domainId;
        this.orgId = orgId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseGroupDTO)) {
            return false;
        }
        BaseGroupDTO that = (BaseGroupDTO)o;
        return Objects.equals(domainId, that.domainId) && Objects.equals(orgId, that.orgId)
            && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainId, orgId, type);
    }

    @Override
    public String toString() {
        return "BaseGroupDTO{" + "domainId=" + domainId + ", orgId=" + orgId + ", type=" + type + '}';
    }
}
