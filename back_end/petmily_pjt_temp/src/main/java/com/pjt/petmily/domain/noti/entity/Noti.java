package com.pjt.petmily.domain.noti.entity;

import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "noti")
public class Noti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private NotiType notiType;

    @ManyToOne
    @JoinColumn(name="fromUserId")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name="toUserId")
    private User toUser;

    @Column(updatable = false)
    private LocalDateTime createDate;

    @Builder.Default
    private boolean isChecked = false;

}