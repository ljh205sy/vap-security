package com.lxg.springboot.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author liujinhui
 * date 2021/6/12 10:08
 */

public class BcryPasswordEncodTest {

    @Test
    public void bcryEncodeTest(){
        String secret = new BCryptPasswordEncoder().encode("secret");
        System.out.println(secret);
        Assert.assertNotNull("secret不为空",secret);
    }

}


