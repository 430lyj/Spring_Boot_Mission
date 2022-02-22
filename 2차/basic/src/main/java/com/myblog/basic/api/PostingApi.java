package com.myblog.basic.api;

import com.myblog.basic.domain.Board;
import com.myblog.basic.domain.Posting;
import com.myblog.basic.service.BoardService;
import com.myblog.basic.service.PostingService;
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
        Long id = postingService.save(request.getTitle(), request.getBody(), request.getWriter(), board_id, request.getPassword());
        return new CreatePostingResponse(id);
    }

    @PutMapping("postings/{posting_id}")
    public UpdatePostingResponse updatePosting(@PathVariable("posting_id") Long posting_id,
                                               @RequestBody @Valid updatePostingRequest request){
        if (postingService.read(posting_id).getPassword().equals(request.getPassword()){
            postingService.update(posting_id, request.getTitle(), request.getBody(), request.getWriter(), request.getBoard_id());
            return new UpdatePostingResponse("200");
        }
        return new UpdatePostingResponse("비밀번호가 일치하지 않습니다.");

    }

    @Data
    private class PostingDto {
        private Long id;
        private String title;
        private String body;
        private String writer;
        public PostingDto(Posting p) {
            this.id = p.getId();
            this.body = p.getBody();
            this.title = p.getTitle();
            this.body = p.getBody();
            this.writer = p.getWriter();
        }
    }

    @Data
    private class createPostingRequest {
        @NotEmpty private String title;
        @NotEmpty private String writer;
        @NotEmpty private String password;
        private String body;

    }

    @Data
    private class CreatePostingResponse {
        Long id;
        public CreatePostingResponse(Long id){
            this.id = id;
        }
    }

    @Data
    private class updatePostingRequest {
        @NotEmpty private String password;
        private String title;
        private String writer;
        private String body;
        private Long board_id;
    }

    @Data
    private class UpdatePostingResponse {
        String status;
        public UpdatePostingResponse(String status){
            this.status = status;
        }
    }
}
