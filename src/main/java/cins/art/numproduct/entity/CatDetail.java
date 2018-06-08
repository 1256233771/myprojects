package cins.art.numproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatDetail implements Serializable{
    private String catDetailId;
    private String userAddress;
    private String picAddress;
}
