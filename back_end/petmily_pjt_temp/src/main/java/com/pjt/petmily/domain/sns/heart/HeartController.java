package com.pjt.petmily.domain.sns.heart;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.pjt.petmily.global.HttpResponseEntity.ResponseResult;

import static com.pjt.petmily.global.HttpResponseEntity.success;


@Slf4j
@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping(value = "/board/heart")
    @Operation(summary = "게시글 좋아요")
    public ResponseResult<?> insert(@RequestBody @Valid HeartRequestDto heartRequestDto) throws Exception {
        heartService.insert(heartRequestDto);
        return success(null);
    }

    @DeleteMapping(value = "/board/heart")
    @Operation(summary = "게시글 좋아요 취소")
    public ResponseResult<?> delete(@RequestBody @Valid HeartRequestDto heartRequestDto) {
        heartService.delete(heartRequestDto);
        return success(null);
    }
}
