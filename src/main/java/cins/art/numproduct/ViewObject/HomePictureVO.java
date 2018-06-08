package cins.art.numproduct.ViewObject;

import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.Theme;
import lombok.Data;

import java.util.List;

@Data
public class HomePictureVO {
    private List<Product> oilPaintings;//油画,4
    private List<Product> newProducts;//新上架的作品,10
    private List<Theme> themes;//主题数目,2
}
