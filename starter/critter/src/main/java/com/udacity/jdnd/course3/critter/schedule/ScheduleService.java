package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.pet.IPetRepository;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.IEmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ScheduleService {
    private final IScheduleRepository scheduleRepository;
    private final IPetRepository petRepository;
    private final IEmployeeRepository employeeRepository;

    public ScheduleEntity save(ScheduleEntity schedule) {
        // Get pets
        List<PetEntity> petEntities = schedule.getPets();
        // Get Employees
        List<EmployeeEntity> employeeEntities = schedule.getEmployees();

        ScheduleEntity savedSchedule = scheduleRepository.saveAndFlush(schedule);

        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        scheduleEntities.add(savedSchedule);

        // Update Pets
        petEntities.forEach(petEntity -> {
            if (Objects.isNull(petEntity.getSchedules())) {
                petEntity.setSchedules(scheduleEntities);
            } else {
                petEntity.getSchedules().add(savedSchedule);
            }
        });
        petRepository.saveAll(petEntities);

        // Update Employees
        employeeEntities.forEach(employeeEntity -> {
            if (Objects.isNull(employeeEntity.getSchedules())) {
                employeeEntity.setSchedules(scheduleEntities);
            } else {
                employeeEntity.getSchedules().add(savedSchedule);
            }
        });
        employeeRepository.saveAll(employeeEntities);

        return scheduleRepository.save(savedSchedule);
    }

    public List<ScheduleEntity> findAll() {
        return scheduleRepository.findAll();
    }
}
