package com.ecnu.compiler.rbac.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.mapper.UserMapper;
import com.ecnu.compiler.utils.UserUtils;
import com.ecnu.compiler.utils.domain.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SessionService {
    @Resource
    private UserMapper userMapper;

    /**
     * check user login request
     * @return user info || null if invalidate
     */
    public User checkLogin(User userParam){
       List<User> users = userMapper.selectList(
               new EntityWrapper<User>().eq("email",userParam.getEmail())
       );
       if(ObjectUtils.isEmpty(users)){ return null; }
       User user = users.get(0);
       if(user.checkPassword(userParam.getPwd())){
           UserUtils.registerCurrentUser(user);
           user.setTimes(user.getTimes()+1);
           userMapper.updateById(user);
           return user;
       }else { return null; }
    }

    public User registerUser(User userParam) {
        List<User> users = userMapper.selectList(
                new EntityWrapper<User>().eq("email",userParam.getEmail())
        );

        // CONFLICT
        if(!ObjectUtils.isEmpty(users)){ return null; }

        userParam.setUserType(Constants.USER_COMMON);
        userParam.setPwd(DigestUtils.md5DigestAsHex(userParam.getPwd().getBytes()));
        userParam.setVolume(Constants.VOLUME_COMMON);
        Timestamp time = new Timestamp(new Date().getTime());
        userParam.setGmtCreated(time);
        userParam.setGmtModified(time);
        userParam.setLastLogin(time);
        Integer uid = userMapper.insert(userParam);

        return ObjectUtils.isEmpty(uid)?null:userParam;
    }
}
