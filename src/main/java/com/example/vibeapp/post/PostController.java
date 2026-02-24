package com.example.vibeapp.post;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
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
    public String getPostList(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 5;
        List<PostListDto> posts = postService.findPagedPosts(page, pageSize);
        int totalPages = postService.getTotalPages(pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "post/posts";
    }

    @GetMapping("/posts/{no}")
    public String getPostDetail(@PathVariable("no") Long no, Model model) {
        PostResponseDto post = postService.findPostByNo(no);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String showPostNewForm(Model model) {
        model.addAttribute("postCreateDto", new PostCreateDto(null, null));
        return "post/post_new_form";
    }

    @GetMapping("/posts/{no}/edit")
    public String showPostEditForm(@PathVariable("no") Long no, Model model) {
        PostResponseDto post = postService.findPostByNo(no);
        PostUpdateDto postUpdateDto = new PostUpdateDto(post.title(), post.content());

        model.addAttribute("post", post);
        model.addAttribute("postUpdateDto", postUpdateDto);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/add")
    public String addPost(@Valid PostCreateDto createDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_new_form";
        }
        postService.addPost(createDto);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{no}/save")
    public String savePost(@PathVariable("no") Long no, @Valid PostUpdateDto updateDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postService.findPostByNo(no)); // 기존 정보 유지 (보여주기용)
            return "post/post_edit_form";
        }
        postService.updatePost(no, updateDto);
        return "redirect:/posts/" + no;
    }

    @PostMapping("/posts/{no}/delete")
    public String deletePost(@PathVariable("no") Long no) {
        System.out.println("게시글 삭제 요청 수신: 번호=" + no);
        postService.deletePost(no);
        return "redirect:/posts";
    }
}
