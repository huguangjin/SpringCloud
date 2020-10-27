package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "-----testA";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "testB");
        return "-----testB";
    }

    @GetMapping("/testD")
    public String testD() {
        //暂停几秒钟线程
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("testD 测试RT");

        log.info("testD 测试异常比例");
        int age=10/0;
        return "-------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age=10/0;
        return "-------testE";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value="p1",required = false)String p1,
                             @RequestParam(value="p2",required = false)String p2){
        return "testHotKey";
    }
    public String deal_testHotKey(String p1, String p2, BlockException exception){
        return "-----deal_testHotKey,o(╥﹏╥)o";
    }

    public static void main(String[] args) {
            int[] nums={-1, 0, 1, 2, -1, -4};
            Arrays.sort(nums);
            List<List<Integer>> ls = new ArrayList<>();

            for (int i = 0; i < nums.length - 2; i++) {
                if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {  // 跳过可能重复的答案

                    int l = i + 1, r = nums.length - 1, sum = 0 - nums[i];
                    while (l < r) {
                        if (nums[l] + nums[r] == sum) {
                            ls.add(Arrays.asList(nums[i], nums[l], nums[r]));
                            while (l < r && nums[l] == nums[l + 1]) l++;
                            while (l < r && nums[r] == nums[r - 1]) r--;
                            l++;
                            r--;
                        } else if (nums[l] + nums[r] < sum) {
                            while (l < r && nums[l] == nums[l + 1]) l++;   // 跳过重复值
                            l++;
                        } else {
                            while (l < r && nums[r] == nums[r - 1]) r--;
                            r--;
                        }
                    }
                }
            }
        System.out.println(ls);
    }
}