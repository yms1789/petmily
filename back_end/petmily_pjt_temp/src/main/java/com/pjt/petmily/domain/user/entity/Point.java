package com.pjt.petmily.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@Entity
@Table(name = "point")
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(name = "point_content", nullable = false)
    private String pointContent;

    @Column(name = "point_type", nullable = false)
    private Boolean pointType;

    @Column(name = "point_cost", nullable = false)
    private Integer pointCost;

    @Column(name = "point_usage_date", nullable = false)
    private LocalDateTime pointUsageDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
