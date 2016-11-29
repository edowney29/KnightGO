package com.ucf.knightgo;

import android.test.ActivityUnitTestCase;

import org.junit.Test;

import static org.junit.Assert.*;
import com.ucf.knightgo.FormationActivity.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{


    @Test
    public void conversionTest() throws Exception{

        int [] intArray=new int[]{123,2221,2532,312,4322,5442,611,72,8111};
        int [] combinedArray = new int[intArray.length*2];
        byte[] byteArray = FormationActivity.int2byte(intArray);
        int [] testIntArray = FormationActivity.byte2int(byteArray);

        //for(int i = 0; i < testIntArray.length;i++)
        //    System.out.print(testIntArray[i] + ",");

        assertArrayEquals(testIntArray,intArray);
    }
}

