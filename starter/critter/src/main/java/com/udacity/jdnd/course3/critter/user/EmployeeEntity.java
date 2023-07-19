package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.common.BaseUserEntity;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
@SuperBuilder
public class EmployeeEntity extends BaseUserEntity {
    @ElementCollection
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    private List<String> skills;
    @ElementCollection
    @CollectionTable(name = "employee_availables", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<DayOfWeek> daysAvailable;
    @ManyToMany
    private List<ScheduleEntity> schedules;
}
