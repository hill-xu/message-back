package com.videoChat.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.videoChat.back.entity.Friends;
import com.videoChat.back.mapper.FriendsMapper;
import com.videoChat.back.service.IFriendsService;
import org.springframework.stereotype.Service;

@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements IFriendsService {

}
