/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.canManager.utils;

/**
 *
 * @author adrien.pillonel
 */
public class Log {
    
    public static void msg(int i, String str)
    {
        if(i==0)
            System.out.print("[ V ] ");
        else if(i==1)
            System.out.print("[ X ] ");

        System.out.print(new Exception().getStackTrace()[1].getClassName() + " | ");

        System.out.println(str);
    }
}

//log4j