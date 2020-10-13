package com.daop.javabase.generic;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.generic
 * @Description:
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 **/
public class GenericParent<T> {

    public void test(T genericObj) {
        System.out.println(genericObj);
    }

}
