package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

}

