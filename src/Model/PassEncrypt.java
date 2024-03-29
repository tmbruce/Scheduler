/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
This class encrypts passwords with the SHA-256 Algorithm.
*/
public class PassEncrypt {
    
    public static String encryptPassword(String password){
        String hashedPass = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
                hashedPass = sb.toString();
        }
        catch(NoSuchAlgorithmException e){
    }
        return hashedPass;
    }
}
