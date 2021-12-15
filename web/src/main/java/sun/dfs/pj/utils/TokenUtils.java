package sun.dfs.pj.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sun.dfs.pj.exception.ResultCode;
import sun.dfs.pj.exception.TokenException;

import java.util.Calendar;


public class TokenUtils {
    private static final String KEY = "awdcfhtrjed";
    public static String getToken(String id,String user,String pwd){
        Calendar calendar = Calendar.getInstance();
        calendar.add(11,24*7);
        String sign = JWT.create()
                .withClaim("id", id)
                .withClaim("username", user)
                .withClaim("password", pwd)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(KEY));
        return sign;
    }
    public static DecodedJWT verify(String token){
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);

        }catch (AlgorithmMismatchException e) {
            throw new TokenException(ResultCode.TOKEN_NULL);
//                return "算法不匹配";
        } catch (SignatureVerificationException e) {
            throw new TokenException(ResultCode.TOKEN_NULL);
//                return "算法签名无效";
        } catch (TokenExpiredException e) {
            //                return "令牌过期";
            throw new TokenException(ResultCode.TOKEN_EXPIRES);

        } catch (Exception e){
            throw new TokenException(ResultCode.TOKEN_NULL);
//                return "token无效";
        }
        return decodedJWT;


          }


}
