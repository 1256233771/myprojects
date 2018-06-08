package cins.art.numproduct.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CatProduct {
    private String catDetailId;
    private String picAddress;
    private String userAddress;
    private String name;
    private String author;
    private BigDecimal price;
    private Integer width;
    private Integer height;
    private String picUrl;
}
