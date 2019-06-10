package com.paychex.specifications;

import org.testng.annotations.*;

public class Hello extends BaseTest {

    @BeforeSuite
    public void BeforeSuite(){
        System.out.println("---------------BeforeSuite");
    }

    @BeforeClass
    public void BeforeClass(){
        System.out.println("---------------BeforeClass");
    }

    @BeforeMethod
    public void BeforeMethod(){
        System.out.println("---------------BeforeMethod");
    }

    @BeforeTest
    public void BeforeTest(){
        System.out.println("---------------BeforeTest");
    }





    @Test
    public void m1(){
        System.out.println("---------------m1");
    }

    @Test
    public void m2(){
        System.out.println("---------------m2");
    }

    @Test
    public void m3(){
        System.out.println("---------------m3");
    }
}
