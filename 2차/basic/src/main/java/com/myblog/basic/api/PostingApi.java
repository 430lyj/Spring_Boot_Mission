package com.myblog.basic.api;

import com.myblog.basic.domain.Board;
import com.myblog.basic.domain.Posting;
import com.myblog.basic.domain.User;
import com.myblog.basic.service.BoardService;
import com.myblog.basic.service.PostingService;
import com.myblog.basic.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostingApi {
    private final BoardService boardService;
    private final PostingService postingService;
    private final UserService userService;

    @GetMapping("/board/{board_id}/postings/")
    public List<PostingDto> getPostingsByBoard (@PathVariable("board_id") Long board_id){
        List<PostingDto> result = postingService.findPostings(board_id).stream()
                .map(p -> new PostingDto(p))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("postings/{posting_id}")
    public PostingDto getPosting (@PathVariable("posting_id") Long posting_id){
        return new PostingDto(postingService.read(posting_id));
    }

    @PostMapping("/board/{board_id}/postings")
    public CreatePostingResponse createPosting(@PathVariable("board_id") Long board_id,
                                 @RequestBody @Valid createPostingRequest request) {
        User user = userService.findOne(Long.parseLong(request.getWriter_id()));
        Long id = postingService.save(request.getTitle(), request.getBody(), user, board_id, request.getPassword());
        return new CreatePostingResponse(id);
    }

    @PutMapping("postings/{posting_id}")
    public UpdateDeletePostingResponse updatePosting(@PathVariable("posting_id") Long posting_id,
                                               @RequestBody @Valid updatePostingRequest request){
        if (postingService.read(posting_id).getPassword().equals(request.getPassword())){
            postingService.update(posting_id, request.getTitle(), request.getBody(), request.getBoard_id());
            return new UpdateDeletePostingResponse("200");
        }
        return new UpdateDeletePostingResponse("비밀번호가 일치하지 않습니다.");
    }

    @DeleteMapping("postings/{posting_id}")
    public UpdateDeletePostingResponse deletePosting(@PathVariable("posting_id") Long posting_id,
                              @RequestBody @Valid deletePostingRequest request){
        if (postingService.read(posting_id).getPassword().equals(request.getPassword())){
            postingService.delete(posting_id);
            return new UpdateDeletePostingResponse("200");
        }
        return new UpdateDeletePostingResponse("비밀번호가 일치하지 않습니다.");
    }

    @Data
    static class PostingDto {
        private Long id;
        private String title;
        private String body;
        private User writer;
        public PostingDto(Posting p) {
            this.id = p.getId();
            this.body = p.getBody();
            this.title = p.getTitle();
            this.body = p.getBody();
            this.writer = p.getWriter();
        }
    }

    @Data
    static class createPostingRequest {
        @NotEmpty private String title;
        @NotEmpty private String writer_id;
        @NotEmpty private String password;
        private String body;

    }

    @Data
    static class CreatePostingResponse {
        Long id;
        public CreatePostingResponse(Long id){
            this.id = id;
        }
    }

    @Data
    static class updatePostingRequest {
        @NotEmpty private String password;
        private String title;
        private String body;
        private Long board_id;
    }

    @Data
    static class UpdateDeletePostingResponse {
        String status;
        public UpdateDeletePostingResponse(String status){
            this.status = status;
        }
    }

    @Data
    static class deletePostingRequest {
        @NotEmpty String password;
    }

}
