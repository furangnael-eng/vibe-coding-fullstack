package com.example.vibeapp.controller;

import com.example.vibeapp.domain.Post;
import com.example.vibeapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String list(Model model) {
        List<Post> posts = postService.getPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPost(no);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/posts/new")
    public String postNewForm() {
        return "post_new_form";
    }

    @GetMapping("/posts/{no}/edit")
    public String postEditForm(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPost(no);
        model.addAttribute("post", post);
        return "post_edit_form";
    }

    @PostMapping("/posts/add")
    public String addPost(@RequestParam("title") String title, @RequestParam("content") String content) {
        System.out.println("새 게시글 등록 요청 수신: 제목=" + title + ", 내용=" + content);
        postService.addPost(title, content);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{no}/save")
    public String savePost(@PathVariable("no") Long no, @RequestParam("title") String title,
            @RequestParam("content") String content) {
        System.out.println("게시글 수정 요청 수신: 번호=" + no + ", 제목=" + title + ", 내용=" + content);
        postService.updatePost(no, title, content);
        return "redirect:/posts/" + no;
    }
}
