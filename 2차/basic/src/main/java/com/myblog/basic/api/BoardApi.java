package com.myblog.basic.api;

import com.myblog.basic.domain.Board;
import com.myblog.basic.service.BoardService;
import com.myblog.basic.service.PostingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class BoardApi {
    private final BoardService boardService;
    private final PostingService postingService;

    @GetMapping("/board/{board_id}")
    public Board board (@PathVariable("board_id") Long board_id){
        return boardService.read(board_id);
    }

    @PostMapping("/board/post")
    public CreateBoardResponse saveBoard(@RequestBody @Valid CreateBoardRequest request){
        Board board = new Board();
        Long id = boardService.save(request.getTitle());
        return new CreateBoardResponse(id);
    }

    @PutMapping("/board/{board_id}")
    public UpdateBoardResponse updateBoard(@PathVariable("board_id") Long board_id,
                                            @RequestBody @Valid UpdateBoardRequest request){
        String title = request.getTitle();
        boardService.update(board_id, title);
        return new UpdateBoardResponse(board_id, title);
    }

    @DeleteMapping("/board/{board_id}")
    public void deleteBoard(@PathVariable("board_id") Long board_id){
        boardService.delete(board_id);
    }

    @Data
    static class CreateBoardRequest {
        @NotEmpty
        private String title;
    }

    @Data
    static class CreateBoardResponse {
        private Long id;

        public CreateBoardResponse(Long id){
            this.id = id;
        }
    }

    @Data
    static class UpdateBoardRequest {
        @NotEmpty
        private String title;
    }

    @Data
    static class UpdateBoardResponse {
        private Long id;
        private String title;
        public UpdateBoardResponse(Long id, String title){
            this.id = id;
            this.title = title;
        }
    }
}
