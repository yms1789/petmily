//package com.pjt.petmily.domain.noti.entity;
//
//import com.pjt.petmily.domain.user.User;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Noti {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Enumerated(EnumType.STRING)
//    private NotiType notiType;
//
//    @ManyToOne
//    @JoinColumn(name="fromUserId")
//    private User fromUser;
//
//    @ManyToOne
//    @JoinColumn(name="toUserId")
//    private User toUser;
//
//    @Column(updatable = false)
//    private LocalDateTime createDate;
//
//    private boolean isChecked=false;
//}