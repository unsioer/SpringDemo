package xmu.oomall.domain.coupon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xmu.oomall.domain.GroupOnRule;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.domain.order.Order;
import xmu.oomall.domain.order.OrderItem;
import xmu.oomall.util.JacksonUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷
 * @Date: Created in 13:47 2019/11/5
 * @Modified By:
 **/
public class CouponRule {
    private static final Log logger = LogFactory.getLog(JacksonUtil.class);

    private CouponRulePo realObj;
        /**
     * 用TreeSet存储该优惠卷可以用的商品id
     */
    private Set<Integer> goodsIds = new TreeSet<Integer>();


    /**
     * 获得能用于此优惠卷的明细
     * @param items 订单明细
     * @return 适用的订单明细
     */
    private List<OrderItem> getValidItems(List<OrderItem> items){
        List<OrderItem> validItems = new ArrayList<OrderItem>(items.size());
        for (OrderItem item: items){
            Goods goods = item.getProduct().getDesc();
            if (this.isUsedOnGoods(goods.getId())){
                validItems.add(item);
            }
        }
        return validItems;

    }

    /**
     * 获得折扣策略
     * JSON格式:{ name：“XXX”, obj:{XXX}}
     * @return 折扣策略对象
     */
    public AbstractCouponStrategy getStrategy() {
        String jsonString = this.realObj.getStrategy();
        String strategyName = JacksonUtil.parseString(jsonString, "name");

        AbstractCouponStrategy strategy = null;
        try {
            strategy = (AbstractCouponStrategy) JacksonUtil.parseObject(jsonString, "obj", Class.forName(strategyName));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return strategy;
    }

    /**
     * 设置折扣策略
     * JSON格式:{ strategy：“XXX”, obj:{XXX}}
     * @param strategy 策略对象
     *
     */
    public void setStrategy(AbstractCouponStrategy strategy) {

        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
        jsonObj.put("name", strategy.getClass().getName());
        jsonObj.put("obj",strategy);
        String jsonString = JacksonUtil.toJson(jsonObj);
        realObj.setStrategy(jsonString);
    }


    /**
     * 判断商品是否能用于这张优惠卷
     *
     * @param goodsId 商品的id
     * @return
     */
    public boolean isUsedOnGoods(Integer goodsId) {
        if (this.goodsIds.size() == 0) {
            this.goodsIds.addAll(this.getGoodsIds());
        }

        if (this.goodsIds.contains(Goods.ALL_GOODS.getId())){
            return true;
        } else {
            return this.goodsIds.contains(goodsId);
        }
    }

    /**
     * 获得适用商品id列表
     * { gIDs：[xxx,xxx,xxx,xxx,xxx]}
     * @return 商品id列表
     */
    public List<Integer> getGoodsIds() {
        String jsonString = realObj.getGoodsIds();
        return JacksonUtil.parseIntegerList(jsonString, "gIDs");
    }

    /**
     * 设置适用商品id列表
     * { gIDs：[xxx,xxx,xxx,xxx,xxx]}
     */
    public void setGoodsIds(List<Integer> goodsIds) {
        Map<String,Object> idMap = new HashMap<String, Object>(1);
        idMap.put("gIDs", goodsIds);
        realObj.setGoodsIds(JacksonUtil.toJson(idMap));
        this.goodsIds.clear();
    }

    /**
     * 获得优惠的费用
     * @param order 订单
     * @return
     */
    public BigDecimal getReductPrice(Order order){
        List<OrderItem> validItems = this.getValidItems(order.getItems());
        return this.getStrategy().cacuDiscount(validItems);

    }

    /**
     * 构造函数
     */
    public CouponRule() {
        this.realObj = new CouponRulePo();
    }


    /****************************************************
     * 生成代码
     ****************************************************/
    public CouponRule(CouponRulePo realObj) {
        this.realObj = realObj;
    }


    @Override
    public String toString() {
        return realObj.toString();
    }

    @Override
    public boolean equals(Object o) {
        return realObj.equals(o);
    }

    @Override
    public int hashCode() {
        return realObj.hashCode();
    }

    public Integer getId() {
        return realObj.getId();
    }

    public void setId(Integer id) {
        realObj.setId(id);
    }

    public String getName() {
        return realObj.getName();
    }

    public void setName(String name) {
        realObj.setName(name);
    }

    public String getBrief() {
        return realObj.getBrief();
    }

    public void setBrief(String brief) {
        realObj.setBrief(brief);
    }

    public String getPicUrl() {
        return realObj.getPicUrl();
    }

    public void setPicUrl(String picUrl) {
        realObj.setPicUrl(picUrl);
    }

    public String getDesc() {
        return realObj.getDesc();
    }

    public void setDesc(String desc) {
        realObj.setDesc(desc);
    }

    public LocalDateTime getBeginTime() {
        return realObj.getBeginTime();
    }

    public void setBeginTime(LocalDateTime beginTime) {
        realObj.setBeginTime(beginTime);
    }

    public Integer getValidPeriod() {
        return realObj.getValidPeriod();
    }

    public void setValidPeriod(Integer validPeriod) {
        realObj.setValidPeriod(validPeriod);
    }

    public Integer getQuantity() {
        return realObj.getQuantity();
    }

    public void setQuantity(Integer quantity) {
        realObj.setQuantity(quantity);
    }

    public CouponRulePo getRealObj() {
        return realObj;
    }


    public LocalDateTime getAddTime() {
        return realObj.getAddTime();
    }

    public LocalDateTime getUpdateTime() {
        return realObj.getUpdateTime();
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        realObj.setUpdateTime(updateTime);
    }

    public Boolean getDeleted() {
        return realObj.getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        realObj.setDeleted(deleted);
    }

}