//package com.himedia.rentmon_back.security.util;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.InvalidClaimException;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//import javax.crypto.SecretKey;
//import java.io.UnsupportedEncodingException;
//import java.time.ZonedDateTime;
//import java.util.Date;
//import java.util.Map;
//
//public class JWTUtil {
//    private static String  key = "1234567890123456789012345678901234567890";
//
//    public static String generateToken(Map<String, Object> claims , int i){
//        SecretKey key =null;
//        try {
//            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//
//        String jwtStr = Jwts.builder()
//                .setHeader(Map.of("typ","JWT"))
//                .setClaims(claims)
//                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(i).toInstant())).signWith(key)
//                .compact();
//
//        return jwtStr;
//
//    }
//
//
//    public static Map<String, Object> validateToken(String accessToken) throws CustomJWTException {
//        Map<String, Object> claim = null;
//        try{
//            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
//
//            claim = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(accessToken).getBody();
//
//        }catch(ExpiredJwtException expiredJwtException){
//            throw new CustomJWTException("Expired");
//        }catch(InvalidClaimException invalidClaimException){
//            throw new CustomJWTException("Invalid");
//        }catch(JwtException jwtException){
//            throw new CustomJWTException("JWTError");
//        }catch(Exception e){
//            throw new CustomJWTException("Error");
//        }
//        return claim;
//    }
//}
