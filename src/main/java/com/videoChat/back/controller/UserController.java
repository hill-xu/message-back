package com.videoChat.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videoChat.back.customAuthentication.CustomWebAuthenticationDetails;
import com.videoChat.back.entity.Friends;
import com.videoChat.back.entity.User;
import com.videoChat.back.service.impl.FriendsServiceImpl;
import com.videoChat.back.service.impl.UserServiceImpl;
import com.videoChat.back.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hill
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserServiceImpl userServiceImpl;

  @Autowired
  private FriendsServiceImpl friendsServiceImpl;

  @PostMapping(value = "/register")
  public boolean register(@RequestBody @Validated(User.register.class) User user) throws Exception {
    LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<User>();
    userWrapper.eq(User::getUsername, user.getUsername());
    User UserByName = userServiceImpl.getOne(userWrapper);
    if (UserByName != null) {
      throw new Exception("用户名已存在");
    }
    user.setPassword(MD5Utils.genMD5(user.getPassword()));
    return userServiceImpl.save(user);
  }

  @GetMapping(value = "/getFriends")
  public Collection<User> getFriends() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
    Integer id = details.getUserInfo().getId();
    LambdaQueryWrapper<Friends> friendsQueryWrapper = new LambdaQueryWrapper<Friends>();
    friendsQueryWrapper.select(Friends::getFriendId).eq(Friends::getSelfId, id);
    List<Long> idList = friendsServiceImpl.listObjs(friendsQueryWrapper, o -> {
      return (Long) o;
    });
    return userServiceImpl.listByIds(idList);
  }

  @GetMapping(value = "/getUserInfo")
  public User getUserInfo() throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
    Integer id = details.getUserInfo().getId();
    LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<User>();
    userWrapper.eq(User::getId, id);
    User user = userServiceImpl.getOne(userWrapper);
    if (user == null) {
      throw new Exception("该用户不存在");
    }
    return user;
  }

}
