package com.ccstay.cyou.service;


import com.aliyuncs.exceptions.ClientException;
import com.ccstay.cyou.dao.UserRepository;
import com.ccstay.cyou.pojo.User;
import com.ccstay.cyou.util.BCryptPasswordEncoder;
import com.ccstay.cyou.util.JwtUtil;
import com.ccstay.cyou.util.SmsUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;
import com.ccstay.cyou.util.IdWorker;


import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
    private HttpServletRequest request;
@Autowired
private JwtUtil jwtUtil;
    /**
     * 增加
     *
     * @param user
     */
    public void saveUser(User user) {
        user.setId(idWorker.nextId() + "");
        userRepository.save(user);
    }
    public static boolean IsMobilePhone(String InPut)
{
String reg = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
return Pattern.matches(reg, InPut);
}

    /**
     * 用户自助注册保存用户
     *
     * @param user

     */
    public void addUser(User user) {



        //2.保存用户到数据库
        user.setId(idWorker.nextId() + "");
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      user.setAvatar("http://tb.himg.baidu.com/sys/portrait/item/tb.1.caedc45c.DdIyVhrXVhVL--oPhGnseg?t=1582125308");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
      user.setUpdatedate(new Date());//更新日期
		user.setLastdate(new Date());//最后登陆日期
        userRepository.save(user);

        //3.用户注册成功后，清除redis中的验证码
        redisTemplate.delete("sms.checkcode" + user.getMobile());
    }

    /**
     * 修改
     *
     * @param user
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteUserById(String id)
    {
     String head=   request.getHeader("Authorization");
        if(head==null|| StringUtils.isEmpty(head)){
            throw  new  RuntimeException("权限不足");
        }
        if(!head.startsWith("Bearer ")){
            throw  new  RuntimeException("权限不足");
        }
        String token=head.substring(7);

        try {
            Claims claims = jwtUtil.parseJWT(token);
           String roles= claims.get("roles").toString();
           if (roles==null||!roles.equals("admin")){
               throw  new  RuntimeException("权限不足");
           }
        }catch (Exception e){
            throw  new  RuntimeException("权限不足");
        }
        userRepository.deleteById(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findUserById(String id) {
        return userRepository.findById(id).get();
    }

    /**
     * 根据条件查询列表
     *
     * @param whereMap
     * @return
     */
    public List<User> findUserList(Map whereMap) {
        //构建Spec查询条件
        Specification<User> specification = getUserSpecification(whereMap);
        //Specification条件查询
        return userRepository.findAll(specification);
    }

    /**
     * 组合条件分页查询
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findUserListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<User> specification = getUserSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userRepository.findAll(specification, pageRequest);
    }

    /**
     * 根据参数Map获取Spec条件对象
     *
     * @param searchMap
     * @return
     */
    private Specification<User> getUserSpecification(Map searchMap) {

        return (Specification<User>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

    //注入RedisTemplate
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private  String template_code;
    //你的accessKeySecret
    @Value("${aliyun.sms.sign_name}")
    private  String sign_name ;
    /**
     * 发送短信验证码（保存手机验证码到redis和mq中）
     *
     * @param mobile
     */
    public void saveSmsCheckcode(String mobile) {
        //1.生成随机6位验证码
        String checkcode = RandomStringUtils.randomNumeric(6);
        //修正

        //System.out.println(mobile + "随机生成的验证码是：" + checkcode);

        //2.保存手机号和验证码
        //1）redis,过期时间为5分钟
        redisTemplate.opsForValue().set("sms.checkcode" + mobile, checkcode, 5, TimeUnit.MINUTES);
        //2）mq
        //封装数据的map
//        Map<String, Object> map = new HashMap<>();
//        map.put("mobile", mobile);
//        map.put("checkcode", checkcode + "");
        // 使用默认直连交换机 
        //rabbitTemplate.convertAndSend("sms.checkcode", map);
        try {
            smsUtil.sendSms(mobile, sign_name, template_code, "{\"code\":" + checkcode + "}");
        }catch (
    ClientException e){
        e.printStackTrace();
    }
    }

//    public User login(User user) {
//          userRepository.findByMobile(user.getMobile());
//    }

    public User login(String mobile, String password) {
        User user=   userRepository.findByMobile(mobile);
        if (user!=null){
            if (bCryptPasswordEncoder.matches(password,user.getPassword())){
                return user;
            }
        }
        return  null;
    }

    public boolean checkRegMb(String mobile) {
       if (userRepository.findByMobile(mobile)==null){
           return true;
       }
       return false;
    }
}
