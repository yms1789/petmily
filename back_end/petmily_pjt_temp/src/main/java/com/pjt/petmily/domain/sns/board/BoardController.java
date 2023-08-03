package com.pjt.petmily.domain.sns.board;

import com.pjt.petmily.domain.pet.PetException;
import com.pjt.petmily.domain.pet.dto.PetInfoEditDto;
import com.pjt.petmily.domain.sns.board.dto.BoardRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/board/save")
    @Operation(summary = "게시글 작성", description = "SNS 게시글 작성&저장")
    public ResponseEntity<String> boardSave(@RequestPart BoardRequestDto boardRequestDto,
                                            @RequestPart(value="file", required = false) List<MultipartFile> boardImgFiles) throws Exception {

        boardService.boardSave(boardRequestDto, boardImgFiles);

        return new ResponseEntity<>("게시글 저장 성공", HttpStatus.OK);
    }

    @GetMapping(value = "board/all")
    @Operation(summary = "게시글 전체 조회")
    public ResponseEntity<List> getAllBoard(){
        List<Board> boardList = boardService.getAllBoard();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping(value = "board/{boardId}")
    @Operation(summary = "게시글 단일 조회")
    public ResponseEntity<String> getOneBoard(@PathVariable Long boardId){
        return null;
    }

    @PutMapping(value = "/board/{boardId}")
    @Operation(summary = "게시글 수정", description = "게시글 수정")
    public ResponseEntity<String> boardUpdate(@PathVariable Long boardId,
                                              @RequestPart BoardRequestDto boardRequestDto,
                                              @RequestPart(value="file") List<MultipartFile> boardImgFiles) throws Exception {
        boardService.boardUpdate(boardId, boardRequestDto, boardImgFiles);

        return new ResponseEntity<>("게시글 수정 성공", HttpStatus.OK);
    }

    @DeleteMapping(value = "/board/{boardId}")
    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "게시글 삭제 실패"),
            @ApiResponse(responseCode = "404", description= "게시글 없음")
    })
    public ResponseEntity<String> boardDelete(@PathVariable Long boardId) {
        try {
            boardService.boardDelete(boardId);
            return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
        } catch (BoardException.BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BoardException.BoardDeletionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
