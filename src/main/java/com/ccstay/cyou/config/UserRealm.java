//package com.ccstay.cyou.config;
//
//import com.ccstay.cyou.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.xml.soap.SOAPEnvelope;
//
////@Configuration
//public class UserRealm extends AuthorizingRealm {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    /**
//     * 授权
//     *
//     * @param principalCollection
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.err.println("开始授权");
//
//
////        //添加授权字符串
////        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
////      //  info.addStringPermission("user:add");
////        Subject subject= SecurityUtils.getSubject();
////        User currentUser = (User)subject.getPrincipal();
////        //设置当前用户的权限
////        info.addStringPermission(currentUser.getPerms());
////        return info;
//        return null;
//    }
//
//
//
//    /**
//     * 认证
//     *
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.err.println("开始认证");
////        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
////        User user = userService.findByName(token.getUsername());
////
////        if (user == null) {
////            return null;
////        }
//        //获取头信息，约定头信息key为Authorization
////
////        final String authorizationHeader = authenticationToken.getHeader("JwtAuthorization");
////
////        //判断authorizationHeader不为空,并且是"Bearer "开头的
////        if(null !=authorizationHeader && authorizationHeader.startsWith("Bearer ")){
////
////
////
////            //获取令牌，The part after "Bearer "
////            final String token=authorizationHeader.substring(7);
////            //获取载荷
////            Claims claims = null;
////            try {
////                claims = jwtUtil.parseJWT(token);
////
////
////                //判断载荷是否为空
////                if(null != claims){
////                    //判断令牌中的自定义载荷中的角色是否是admin（管理员）
////                    if("admin".equals(claims.get("roles"))){
////                        request.setAttribute("admin_claims",claims);
////                    }
////                    //判断令牌中的自定义载荷中的角色是否是user（普通用户）
////                    if("user".equals(claims.get("roles"))){
////                        request.setAttribute("user_claims",claims);
////                    }
////                }
////            } catch (Exception e) {
////                throw new RuntimeException("令牌不正确！");
////            }
////        }
////        SecurityUtils.getSubject().getSession().setAttribute("loginUser",user);
////        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
//        return null;
//    }
//}
