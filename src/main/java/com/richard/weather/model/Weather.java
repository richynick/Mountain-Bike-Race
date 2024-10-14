package com.richard.weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="weatherTable")
@EntityListeners(AuditingEntityListener.class)
public class Weather {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;

    private long cityId;

    private String cityName;

    private String weather;

    private double temp;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}
