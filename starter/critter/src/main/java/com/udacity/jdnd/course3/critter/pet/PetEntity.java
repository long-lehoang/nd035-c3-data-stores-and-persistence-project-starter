package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.common.BaseEntity;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
public class PetEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    private LocalDate birthDate;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private CustomerEntity owner;
    @ManyToMany
    private List<ScheduleEntity> schedules;
}
