package co.edu.uniandes.csw.uniandes.api.JWT;

import com.google.gson.*;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import org.apache.commons.codec.binary.Base64;


/**
 * Created by oscarkiyoshigegarcesaparicio on 17/09/14.
 */

public class JsonWebToken {

    /**
     * Encode an Object in the JWT way. <JsonHeaders>.<JsonPayloadIn>.<Hash>
     *     The JsonPayloadIn es a Json that must include 'exp' key, this key is expiration DATE.
     * @param payloadIn This is the Payload
     * @param key Secret Key to create the Hash.
     * @param algorithm Chosen algorithm to create the Hash.
     * @return Token
     */
    public static String encode(Object payloadIn, String key, JwtHashAlgorithm algorithm) {

        JsonObject jsonHeader = new JsonObject();
        jsonHeader.add("typ",new JsonPrimitive("JWT"));
        jsonHeader.add("alg", new JsonPrimitive(algorithm.toString()));

        String jsonPayloadIn = (new Gson()).toJson(payloadIn);

        byte[] header = new byte[0];
        byte[] payload = new byte[0];

        try {
            header = jsonHeader.toString().getBytes("UTF-8");
            payload =  jsonPayloadIn.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String toSign = (Base64.encodeBase64URLSafeString(header))+"."+(Base64.encodeBase64URLSafeString(payload));

        byte[] sign = HashAlgorithm.getHash(key, toSign, algorithm);

        String returnS= toSign+"."+(Base64.encodeBase64URLSafeString(sign));
        return returnS;

    }

    /**
     * Method to decode a token.
     * @param token
     * @param key Secret Key used to decode the Hash.
     * @param verify Boolean that allows to verify Token, The developer can choose if he wants to verify the token
     *               correctness.
     * @return String decoded.
     * @throws SignatureException This Exceptions is throw if something goes wrong with Signature.
     */
    public static String decode(String token, String key, boolean verify) throws SignatureException {

        Gson gsonUtil = new Gson();

        String[] parts = token.split("\\.");
        String header = parts[0];
        String payload = parts[1];
        String noDecodedCrypto = parts[2];

        JsonObject jsonHeader = gsonUtil.fromJson(new String(Base64.decodeBase64(header)), JsonObject.class);
        JsonObject jsonPayload = gsonUtil.fromJson(new String(Base64.decodeBase64(payload)), JsonObject.class);


        if(verify){
            String toSign = (header+"."+payload);
            JwtHashAlgorithm algorithm = JwtHashAlgorithm.valueOf(jsonHeader.get("alg").getAsString());
            verifyToken(key,toSign,noDecodedCrypto,algorithm);

        }
        return jsonPayload.toString();
    }

//    /**
//     * Main to prove the features.
//     * @param args
//     */
//    public static void main(String[] args){
//
//        JsonObject jsonHeader = new JsonObject();
//        jsonHeader.add("user",new JsonPrimitive("Oscar Kiyoshige"));
//        jsonHeader.add("exp", new JsonPrimitive("123123123123123"));
//
//        System.out.println(encode(jsonHeader, "Porfavor", JwtHashAlgorithm.HS512));
//        try {
//            System.out.println(decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiT3NjYXIgS2l5b3NoaWdlIiwiZXhwIjoiMTIzMTIzMTIzMTIzMTIzIn0.uql7l-k74kaSGvN_I43p6YOs-U_ahgtzkYYYn_WKhWk", "Porfavor", true));
//        }catch (Exception e){
//            System.err.println(e.getMessage());
//        }
//    }

    /**
     *
     * @param key
     * @param toSign
     * @param signature
     * @param algorithm
     * @return
     * @throws SignatureException
     */
    public static boolean verifyToken(String key, String toSign, String signature, JwtHashAlgorithm algorithm) throws SignatureException {

        byte[] toSignVer = HashAlgorithm.getHash(key,toSign,algorithm);

        String encodedSign = Base64.encodeBase64URLSafeString(toSignVer);

        if(!encodedSign.equals(signature)){
            throw new SignatureException("Invalid Signature");
        }

        return true;

    }
}
