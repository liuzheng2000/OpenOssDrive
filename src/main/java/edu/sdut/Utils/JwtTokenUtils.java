package edu.sdut.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 22:19
 */
public class JwtTokenUtils {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "openossjwt";
    private static final String ISS = "echisan";

    /**
     *  过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 36000L;

    /**
     *  // 添加角色的key
     */
    private static final String ROLE_CLAIMS = "rol";

    /**
     * 通过用户名创建token
     * @param username
     * @return
     */
    public static String createToken(String username,String role){
        long expiration = EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }


    /**
     * 创建分享文件的Token
     * @param username
     * @param FileNameAndPath
     * @return
     */
    public static String createShareFileToken(String username,HashMap FileNameAndPath){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .setClaims(FileNameAndPath)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }




    /**
     * 解析 获取token主体
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUserName(String token){
        return getTokenBody(token).getSubject();
    }


    /**
     * 从token中获取用户名
     * @param httpServletRequest
     * @return
     */
    public static String getUserNameByHttpServletRequest(HttpServletRequest httpServletRequest){
        return JwtTokenUtils.getUserName( httpServletRequest.getHeader("Authorization"));
    }


    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public static String getUserRole(String token){
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    /**
     * 判断当前token是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }


}
