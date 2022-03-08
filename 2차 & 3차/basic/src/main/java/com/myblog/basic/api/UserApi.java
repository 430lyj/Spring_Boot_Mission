package com.myblog.basic.api;


import com.myblog.basic.domain.User;
import com.myblog.basic.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/user/{user_id}")
    public User getOneUser (@PathVariable("user_id") Long user_id){
        return userService.findOne(user_id);
    }

    @PostMapping ("/user")
    public PostPutUserResponse postOneUser (@Valid @RequestBody PostPutUserRequest request){
        userService.save(request.getName(), Long.parseLong(request.getAge()), request.getEmail());
        String status = "정상적으로 저장되었습니다.";
        return new PostPutUserResponse(status);
    }

    @PutMapping("/user/{user_id}")
    public PostPutUserResponse putOneUser (@PathVariable("user_id") Long user_id, @Valid @RequestBody PostPutUserRequest request){
        userService.put(user_id, request.getName(), Long.parseLong(request.getAge()), request.getEmail());
        String status = "정상적으로 저장되었습니다.";
        return new PostPutUserResponse(status);
    }

    @DeleteMapping("/user/{user_id}")
    public DeleteUserResponse deleteOneUser(@PathVariable("user_id") Long user_id, @Valid @RequestBody PostPutUserRequest request){
        userService.delete(user_id);
        String status = "정상적으로 삭제되었습니다.";
        return new DeleteUserResponse(status);
    }

    @Data
    static class PostPutUserRequest {
        @NotEmpty private String age;
        @NotEmpty private String name;
        @NotEmpty private String email;
    }

    @Data
    static class PostPutUserResponse {
        private String status;
        public PostPutUserResponse(String status){
            this.status = status;
        }
    }

    @Data
    static class DeleteUserResponse {
        private String status;
        public DeleteUserResponse(String status){
            this.status = status;
        }
    }
}
