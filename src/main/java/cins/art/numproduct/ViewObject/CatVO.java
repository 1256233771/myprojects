package cins.art.numproduct.ViewObject;

import cins.art.numproduct.DTO.CatProduct;
import lombok.Data;

import java.util.List;

@Data
public class CatVO {
    private String emailAddress;
    private String userAddress;
    private List<CatProduct> catProductList;
}
