package cins.art.numproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBid {
    private Integer id;
    private String picAddress;
    private String userAddress;
    private BigDecimal price;
}
