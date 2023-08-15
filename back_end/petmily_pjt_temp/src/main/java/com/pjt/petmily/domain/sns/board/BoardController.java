package com.pjt.petmily.domain.sns.board;


import com.pjt.petmily.domain.sns.board.dto.*;
import com.pjt.petmily.domain.sns.board.hashtag.HashTagRequestDto;
import com.pjt.petmily.domain.sns.board.hashtag.HashTagService;
import com.pjt.petmily.domain.user.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PointService pointService;

    @GetMapping(value = "board/all")
    @Operation(summary = "게시글 전체 조회", description = "execute누르면 전체 조회 확인 가능")
    public ResponseEntity<List> getAllBoard(@RequestParam String currentUserEmail) {
        List<ResponseBoardAllDto> boardList = boardService.getAllBoard(currentUserEmail);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }
    @GetMapping(value = "board/all/inf")
    @Operation(summary = "게시글 전체 조회", description = "execute누르면 전체 조회 확인 가능")
    public ResponseEntity<PagedResponseBoardDto> getAllBoard(
            @RequestParam String currentUserEmail,
            @RequestParam Long lastPostId,
            @RequestParam int size) {

        PagedResponseBoardDto pagedResponse = (PagedResponseBoardDto) boardService.getAllBoardPagesBy(lastPostId, size, currentUserEmail);

        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }

    @GetMapping(value = "board/{boardId}")
    @Operation(summary = "게시글 단일 조회")
    public ResponseEntity<ResponseBoardAllDto> getOneBoard(@PathVariable Long boardId,
                                                           @RequestParam String currentUserEmail) {
        ResponseBoardAllDto board = boardService.getOneBoard(boardId, currentUserEmail);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping(value = "/board/save")
    @Operation(summary = "게시글 작성", description = "SNS 게시글 작성&저장")
    public ResponseEntity<BoardSaveDto> boardSave(@RequestPart BoardRequestDto boardRequestDto,
                                                       @RequestPart HashTagRequestDto hashTagRequestDto,
                                                       @RequestPart(value = "file", required = false) List<MultipartFile> boardImgFiles) throws Exception {

        Board savedBoard = boardService.boardSave(boardRequestDto, boardImgFiles, hashTagRequestDto);
        pointService.updatePoint(true, 3, boardRequestDto.getUserEmail(), "게시글작성");

        BoardSaveDto response = buildBoardSaveDto(savedBoard);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/board/{boardId}")
    @Operation(summary = "게시글 수정", description = "게시글 수정")
    public ResponseEntity<String> boardUpdate(@PathVariable Long boardId,
                                              @RequestPart BoardRequestDto boardRequestDto,
                                              @RequestPart HashTagRequestDto hashTagRequestDto,
                                              @RequestPart(value = "file", required = false) List<MultipartFile> boardImgFiles) throws Exception {
        boardService.boardUpdate(boardId, boardRequestDto, boardImgFiles, hashTagRequestDto);

        return new ResponseEntity<>("게시글 수정 성공", HttpStatus.OK);
    }

    @DeleteMapping(value = "/board/{boardId}")
    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "게시글 삭제 실패"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    public ResponseEntity<String> boardDelete(@PathVariable Long boardId, @RequestBody BoardDeleteDto boardDeleteDto) {
        try {
            boardService.boardDelete(boardId, boardDeleteDto);
            return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
        } catch (BoardException.BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BoardException.UnauthorizedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BoardException.BoardDeletionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/board/search/{hashTag}")
    @Operation(summary = "해시태그 검색")
    public ResponseEntity<List<BoardHashtagDto>> getHashTagBoard(@PathVariable String hashTag) {
        List<BoardHashtagDto> boards = boardService.getBoardsByHashTag(hashTag);
        return ResponseEntity.ok(boards);
    }


    public BoardSaveDto buildBoardSaveDto(Board savedBoard) {
        BoardSaveDto boardSavedto = new BoardSaveDto();
        boardSavedto.setBoardId(savedBoard.getBoardId());
        boardSavedto.setBoardContent(savedBoard.getBoardContent());
        boardSavedto.setBoardUploadTime(savedBoard.getBoardUploadTime());
        boardSavedto.setHeartCount(0);
        boardSavedto.setUserEmail(savedBoard.getUser().getUserEmail());
        boardSavedto.setUserProfileImageUrl(savedBoard.getUser().getUserProfileImg());
        boardSavedto.setUserNickname(savedBoard.getUser().getUserNickname());

        return boardSavedto;
    }
}