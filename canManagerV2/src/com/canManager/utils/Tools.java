package com.canManager.utils;

public class Tools {
    
    public static int byteToIntLsb(byte firstByte, byte secondByte) {
        int result = (secondByte << 8) + firstByte;
        
        return result;
    }

    public static int byteToIntLsb(byte firstByte, byte secondByte, byte thirdByte, byte fourthByte){
        int result = (fourthByte << 24) + (thirdByte << 16) + (secondByte << 8) + firstByte;
        
        return result;
    }
}
