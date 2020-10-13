package com.daop.javabase.generic;

import org.junit.jupiter.api.Test;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.generic
 * @Description:
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 **/
public class GenericDemo {
    @Test
    public void demoTest() {
        GenericChild genericChild = new GenericChild();
        genericChild.test(1);
    }
}
