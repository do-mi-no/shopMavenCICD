package org.design.framework.test;

import org.design.framework.testComponents.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomNumbersTest {

    @Test(dataProvider = "getRandomInts", retryAnalyzer = Retry.class)
    public void batchRndTest(Integer num) {
        System.out.println("batchRndNum = " + num);
//        Assert.assertEquals(num, 1);
    }

//    @Test(priority = 3)
//    public void alwaysFailedTest() {
////        Assert.fail();
//        Assert.assertTrue(false);
//    }

    @Test()
    public void alwaysPassedTest() {
        Assert.assertTrue(true);
    }

    @Test(retryAnalyzer = Retry.class)
    public void simpleRandomRetryTest() {
        int rnd = new Random().nextInt(3); //0,1,2
//        System.out.println("simpleRndTest = " + rnd);
        Assert.assertEquals(rnd, 1);
    }

    @DataProvider
    public Iterator<Integer> getRandomInts() {
        List<Integer> list = new ArrayList<>();
        list = new Random().ints(10, 0, 2)
                .boxed().collect(Collectors.toList());
//        list.addAll(List.of(0,1,0,1,0,1));
//        System.out.println("listRnd = " + list);
        return list.iterator();
    }

}