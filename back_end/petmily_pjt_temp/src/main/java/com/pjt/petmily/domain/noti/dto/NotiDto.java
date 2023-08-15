package com.pjt.petmily.domain.noti.dto;

import com.pjt.petmily.domain.noti.entity.Noti;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotiDto {

    private int id;
    private String notiType;
    private String fromUserEmail;
    private String fromUserNickname;
    private String fromUserProfileImg;
    private String toUserEmail;
    private LocalDateTime createDate;
    private boolean isChecked;

    // 기본 생성자, getter, setter 생략 ...

    // Noti 엔터티를 NotiDto로 변환하는 static 메서드
    public static NotiDto fromEntity(Noti noti) {
        NotiDto dto = new NotiDto();
        dto.setId(noti.getId());
        dto.setNotiType(noti.getNotiType().toString());
        dto.setFromUserEmail(noti.getFromUser().getUserEmail());
        dto.setFromUserNickname(noti.getFromUser().getUserNickname());
        dto.setFromUserProfileImg(noti.getFromUser().getUserProfileImg());
        dto.setToUserEmail(noti.getToUser().getUserEmail());
        dto.setCreateDate(noti.getCreateDate());
        dto.setIsChecked(noti.isChecked());

        return dto;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}
