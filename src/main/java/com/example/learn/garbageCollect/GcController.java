package com.example.learn.garbageCollect;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gc")
public class GcController{

    @GetMapping("/user")
    public String processUserData() throws InterruptedException {
        ArrayList<UserProfile> users = queryUsers();
        for (UserProfile user: users) {
        }
        return "end";
    }

    /**
     * 模拟批量查询用户场景
     * @return
     */
    private ArrayList<UserProfile> queryUsers() {
        ArrayList<UserProfile> users = new ArrayList<>();
        for (Integer i = 0; i < 5000; i++) {
            users.add(new UserProfile(i,"wang"));
        }
        return users;
    }
}