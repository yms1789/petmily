package com.pjt.petmily.domain.sns.board;

import com.pjt.petmily.domain.pet.dto.PetInfoEditDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/save")
    @Operation(summary = "게시글 작성", description = "SNS 게시글 작성&저장")
    public ResponseEntity<String> PetInfoSave(@RequestPart PetInfoEditDto petInfoEditDto,
                                              @RequestPart(value="file") MultipartFile file) throws Exception {

        return new ResponseEntity<>("게시글 저장 성공", HttpStatus.OK);
    }
}
