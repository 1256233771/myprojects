package cins.art.numproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theme implements Serializable{
    private String id;
    private String picUrl;
    private String title;
    private String description;
    private Date uploadTime;
}
