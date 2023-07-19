package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.common.BaseEntity;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules")
@SuperBuilder
public class ScheduleEntity extends BaseEntity {
    private LocalDate date;
    @ManyToMany
    private List<EmployeeEntity> employees;
    @ElementCollection
    @CollectionTable(name = "schedule_activities", joinColumns = @JoinColumn(name = "schedule_id"))
    private List<String> activities;
    @ManyToMany
    private List<PetEntity> pets;
}
