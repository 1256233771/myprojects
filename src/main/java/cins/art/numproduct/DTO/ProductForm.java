package cins.art.numproduct.DTO;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class ProductForm {
    @NotNull(message = "作品标识必传")
    @ApiModelProperty(value = "区块链生成的画作地址，唯一标识")
    private String picAddress;
    @NotNull(message = "作品名称不能为空")
    private String name;
    @NotNull(message = "图片文件上传不能为空")
    private MultipartFile picFile;
    private MultipartFile videoFile;//允许为空
    @NotNull(message = "创作时间不能为空")
    @ApiModelProperty(value = "画作创作时间")
    private String createTime;
    @NotNull(message = "画作宽度")
    private Integer width;
    @NotNull(message = "画作高度")
    private Integer height;
    @NotNull(message = "作品价格不能为空")
    private float price;
    @NotNull(message = "作者名不为空")
    private String author;
    @NotNull(message = "画作状态设置不能为空")
    private Integer productStatus;//0非卖品,1出售,2竞标拍卖
    private String category;
    private String style;
    private String theme;
    private String description;


}