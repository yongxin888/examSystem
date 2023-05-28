package com.examsystem.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: token工具类
 * @DATE: 2023/5/26 13:12
 */
public class JWTUtil {
    /**
     * JWT的唯一身份标识
     */
    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     * 秘钥
     */
    public static final String JWT_SECRET = "huangyongxin!2023#";

    /**
     * 过期时间，单位毫秒
     */
    public static final int EXPIRE_TIME = 60 * 60 * 1000;

    //创建Token
    public static String createJwt(String subject){

        //添加头部信息
        Map<String,Object> header = new HashMap<>();
        header.put("typ","JWT");
        //  header.put("alg", "HS256");

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //添加载荷信息
        Map<String,Object> claims = new HashMap<>();
        claims.put("username", "admin");

        //生成JWT的时间
        long nowTime = System.currentTimeMillis();
        Date issuedAt = new Date(nowTime);

        JwtBuilder builder = Jwts.builder()
                .setHeader(header) // 设置头部信息
                .setClaims(claims) // 如果有私有声明，一定要先设置自己创建的这个私有声明，这是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明
                .setId(JWT_ID) // jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setIssuedAt(issuedAt) // iat(issuedAt)：jwt的签发时间
                .setSubject(subject) // sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
                .signWith(signatureAlgorithm, JWT_SECRET); // 设置签名，使用的是签名算法和签名使用的秘钥

        //设置过期时间
        if(EXPIRE_TIME > 0){
            long exp = nowTime + EXPIRE_TIME;
            builder.setExpiration(new Date(exp));
        }

        return builder.compact();
    }

    //解析Token
    public static Claims parseJwt(String token){
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
        String jwt = createJwt("1");
        System.out.println(jwt);

        Claims claims = parseJwt(jwt);
        System.out.println(claims.getSubject());

    }
}
