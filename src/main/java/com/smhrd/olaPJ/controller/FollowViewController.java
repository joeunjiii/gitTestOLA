package com.smhrd.olaPJ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FollowViewController {

    @GetMapping("/follow/list")
    public String followPage() {
        return "follow";  // templates/follow.html 렌더링
    }
}