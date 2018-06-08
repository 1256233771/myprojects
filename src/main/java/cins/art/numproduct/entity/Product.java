package cins.art.numproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private String picAddress;//唯一标识
    private String userAddress;
    private String name;
    private String pictureUrl;
    @JsonIgnore
    private String videoUrl;
    private String creteTime;//创作时间,前端上传数据
    private Date uploadTime;//上传时间,后端保存数据时写入时间
    private Integer width;
    private Integer height;
    private BigDecimal price;
    private String author;
    private String category;//分类
    private String style;//风格
    private String theme;//主题
    private Integer productStatus;//0非卖品,1出售,2竞标拍卖
    @JsonIgnore
    private String description;
}
