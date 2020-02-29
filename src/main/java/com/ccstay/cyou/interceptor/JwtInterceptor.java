package com.ccstay.cyou.interceptor;

import com.ccstay.cyou.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，用于Jwt验证token
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    //注入JwtUtil
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


       // System.out.println("经过了JwtInterceptor拦截器...");

//        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
//            System.out.println("跨域的第一次请求，直接放行");
//            //response.setStatus(HttpStatus.NO_CONTENT.value());
//                   //   HttpServletResponse response=requestContext.getResponse();
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,JwtAuthorization");//Access-Control-Allow-Headers配置允许前端提交的请求头信息
//            response.addHeader("Access-Control-Expose-Headers", "GID,SID");//Access-Control-Expose-Headers配置允许前端获取到的请求头信息
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            return true;
//        }
        //获取头信息，约定头信息key为Authorization
        final String authorizationHeader = request.getHeader("JwtAuthorization");

        //判断authorizationHeader不为空,并且是"Bearer "开头的
        if(null !=authorizationHeader && authorizationHeader.startsWith("Bearer ")){



            //获取令牌，The part after "Bearer "
            final String token=authorizationHeader.substring(7);
            //获取载荷
            Claims claims = null;
            try {
                claims = jwtUtil.parseJWT(token);


            //判断载荷是否为空
            if(null != claims){
                //判断令牌中的自定义载荷中的角色是否是admin（管理员）
                if("admin".equals(claims.get("roles"))){
                    request.setAttribute("admin_claims",claims);
                }
                //判断令牌中的自定义载荷中的角色是否是user（普通用户）
                if("user".equals(claims.get("roles"))){
                    request.setAttribute("user_claims",claims);
                }
            }
            } catch (Exception e) {
                throw new RuntimeException("令牌不正确！");
            }
        }
        return true;
    }
}
