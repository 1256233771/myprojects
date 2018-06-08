package cins.art.numproduct.service;

import cins.art.numproduct.DTO.ProductChangeDTO;
import cins.art.numproduct.DTO.ProductForm;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.User;

import java.util.List;

public interface ProductService {
    public Object addProduct(Product product);
    public Product findByPicAddress(String picAddress);
    public List<Product> findByStyle(String style);
    public List<Product> findByUser(User user);
    public Object uploadProduct(ProductForm productForm, User user);
    public List<Product> findAllByCategory(String category);
    public List<Product> findBySelections(Integer productStatus,String minPrice,
                                          String maxPrice,Integer width, Integer height,
                                          String author,String category,String style,
                                          String theme,Integer pageNum,Integer orderRule);
    public List<Product> findByCategoryTimeLimit(String category,String endTime,Integer limit);
    public List<Product> findByCompareStr(String compareStr);
    public Product setStatus(String picAddress,Integer status);
    public List<Product> getRandomProducts(Integer limit);
    public Object updateSomeInfo(ProductChangeDTO changeDTO,User user);
    public boolean changePicOwner(String picAddress, User user);
}
