package cins.art.numproduct.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductChangeDTO {
    private String picAddress;
    private String name;
    private String author;
    private String category;
    private String style;
    private String theme;
    private BigDecimal price;
    private Integer productStatus;
}
