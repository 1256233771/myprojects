package cins.art.numproduct.service;

import cins.art.numproduct.entity.User;

import java.security.Principal;

public interface UserService {
    public User findUserByUserAddress(String userAddress);
    public User findUserByEmail(String emailAddress);
    public Object addUser(User user);
    public User getCurrentUser(Principal principal);
    public boolean checkPrincipal(Principal principal,String userAddress);
}
