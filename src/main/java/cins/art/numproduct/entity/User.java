package cins.art.numproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    private String userAddress;//区块链返回的用户的唯一标识,做主键
    private String emailAddress;
    private String password;
    private String role;//权限
}
