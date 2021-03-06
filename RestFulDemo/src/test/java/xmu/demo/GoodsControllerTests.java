package xmu.demo;

import com.alibaba.fastjson.JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import xmu.demo.model.Goods;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc    //配置模拟的MVC，这样可以不启动服务器测试
public class GoodsControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test   //标识此方法为测试方法
    public void getDetailTest() throws Exception {
        this.mvc.perform(get("/wx/goods/1/detail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(content().json("{\"errno\":0,\"data\":{\"id\":1,\"goodsSn\":\"111111\"," +
                        "\"name\":\"测试商品\",\"categoryId\":null,\"brandId\":null,\"gallery\":null,\"keywords\":null,\"brief\":null," +
                        "\"isOnSale\":null,\"sortOrder\":null,\"picUrl\":null,\"shareUrl\":null,\"isNew\":null,\"isHot\":true,\"unit\":null," +
                        "\"counterPrice\":null,\"retailPrice\":null,\"addTime\":null,\"updateTime\":null,\"deleted\":null,\"detail\":null," +
                        "\"goodsAttributeList\":[{\"id\":null,\"goodsId\":null,\"attribute\":\"属性1\",\"value\":\"值1\",\"addTime\":null," +
                        "\"updateTime\":null,\"deleted\":null},{\"id\":null,\"goodsId\":null,\"attribute\":\"属性2\",\"value\":\"值2\"," +
                        "\"addTime\":null,\"updateTime\":null,\"deleted\":null}],\"goodsSpecificationList\":[{\"id\":null,\"goodsId\":null," +
                        "\"specification\":\"规格1\",\"value\":\"规格值1\",\"picUrl\":null,\"addTime\":null,\"updateTime\":null," +
                        "\"deleted\":null},{\"id\":null,\"goodsId\":null,\"specification\":\"规格2\",\"value\":\"规格值2\"," +
                        "\"picUrl\":null,\"addTime\":null,\"updateTime\":null,\"deleted\":null}]},\"errmsg\":\"成功\"}"));
    }

    @Test
    public void searchTest() throws Exception {
        this.mvc.perform(get("/wx/goods/search?name=墨迹"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(content().json("{\"errno\":0,\"data\":{\"id\":0,\"goodsSn\":\"111111\"," +
                        "\"name\":\"墨迹\",\"categoryId\":null,\"brandId\":null,\"gallery\":null,\"keywords\":null,\"brief\":null," +
                        "\"isOnSale\":null,\"sortOrder\":null,\"picUrl\":null,\"shareUrl\":null,\"isNew\":null,\"isHot\":true,\"unit\":null," +
                        "\"counterPrice\":null,\"retailPrice\":null,\"addTime\":null,\"updateTime\":null,\"deleted\":null,\"detail\":null," +
                        "\"goodsAttributeList\":[{\"id\":null,\"goodsId\":null,\"attribute\":\"属性1\",\"value\":\"值1\",\"addTime\":null," +
                        "\"updateTime\":null,\"deleted\":null},{\"id\":null,\"goodsId\":null,\"attribute\":\"属性2\",\"value\":\"值2\"," +
                        "\"addTime\":null,\"updateTime\":null,\"deleted\":null}],\"goodsSpecificationList\":[{\"id\":null,\"goodsId\":null," +
                        "\"specification\":\"规格1\",\"value\":\"规格值1\",\"picUrl\":null,\"addTime\":null,\"updateTime\":null," +
                        "\"deleted\":null},{\"id\":null,\"goodsId\":null,\"specification\":\"规格2\",\"value\":\"规格值2\"," +
                        "\"picUrl\":null,\"addTime\":null,\"updateTime\":null,\"deleted\":null}]},\"errmsg\":\"成功\"}"));

    }

    @Test
    public void createGoodTest() throws Exception {
        Goods g = new Goods();
        g.setId(1);
        g.setGoodsSn("111111");
        g.setIsHot(true);
        g.setName("测试商品");

        String goodJson = JSON.toJSONString(g);

        this.mvc.perform(post("/wx/goods").contentType("application/json;charset=UTF-8").content(goodJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(content().json("{\"errno\":0,\"data\":{\"id\":1,\"goodsSn\":\"111111\"," +
                        "\"name\":\"测试商品\",\"categoryId\":null,\"brandId\":null,\"gallery\":null,\"keywords\":null,\"brief\":null," +
                        "\"isOnSale\":null,\"sortOrder\":null,\"picUrl\":null,\"shareUrl\":null,\"isNew\":null,\"isHot\":true,\"unit\":null," +
                        "\"counterPrice\":null,\"retailPrice\":null,\"addTime\":null,\"updateTime\":null,\"deleted\":null,\"detail\":null," +
                        "\"goodsAttributeList\":null,\"goodsSpecificationList\":null},\"errmsg\":\"成功\"}"));

    }
}
