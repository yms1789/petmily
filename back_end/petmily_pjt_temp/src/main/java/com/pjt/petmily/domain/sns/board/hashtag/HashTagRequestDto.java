package com.pjt.petmily.domain.sns.board.hashtag;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HashTagRequestDto {

    private List<String> hashTagNames;
}
