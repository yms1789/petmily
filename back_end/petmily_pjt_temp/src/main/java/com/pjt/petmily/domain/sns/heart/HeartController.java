package com.pjt.petmily.domain.sns.heart;


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
    public ResponseResult<?> insert(@RequestBody @Valid HeartRequestDto heartRequestDto) throws Exception {
        heartService.insert(heartRequestDto);
        return success(null);
    }

    @DeleteMapping(value = "/board/heart")
    public ResponseResult<?> delete(@RequestBody @Valid HeartRequestDto heartRequestDto) {
        heartService.delete(heartRequestDto);
        return success(null);
    }
}
