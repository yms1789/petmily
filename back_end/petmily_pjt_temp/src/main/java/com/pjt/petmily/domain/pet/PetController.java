package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.pet.dto.PetInfoEditDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/pet/save")
    @Operation(summary = "반려동물 정보 입력", description = "반려동물 정보 입력 및 수정")
    public ResponseEntity<String> PetInfoSave(@RequestPart PetInfoEditDto petInfoEditDto,
                                                @RequestPart(value="file") MultipartFile file) throws Exception {
        petService.petInfoSave(petInfoEditDto, file);

        return new ResponseEntity<>("반려동물 정보 저장 성공", HttpStatus.OK);
    }

    @PatchMapping("/pet/{petId}")
    @Operation(summary = "반려동물 정보 수정", description = "반려동물 정보 수정")
    public ResponseEntity<String> PetInfoSave(@PathVariable Long petId,
                                              @RequestPart PetInfoEditDto petInfoEditDto,
                                              @RequestPart(value="file") MultipartFile file) throws Exception {
        petService.petInfoUpdate(petId, petInfoEditDto, file);

        return new ResponseEntity<>("반려동물 정보 수정 성공", HttpStatus.OK);
    }


    @DeleteMapping("/pet/{petId}")
    @Operation(summary = "반려동물 정보 삭제", description = "반려동물 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "반려동물 정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "반려동물 정보 삭제 실패"),
            @ApiResponse(responseCode = "404", description= "반려동물 정보 없음")
    })
    public ResponseEntity<String> deletePetInfo(@PathVariable Long petId) {
        try {
            petService.petInfoDelete(petId);
            return new ResponseEntity<>("Pet information deleted successfully", HttpStatus.OK);
        } catch (PetException.PetNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PetException.PetDeletionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
