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

    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        // Get pets
        List<PetEntity> petEntities = petRepository.findAllById(scheduleDTO.getPetIds());
        // Get Employees
        List<EmployeeEntity> employeeEntities = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setPets(petEntities);
        schedule.setActivities(scheduleDTO.getActivities().stream().map(EmployeeSkill::toString).collect(Collectors.toList()));
        schedule.setDate(scheduleDTO.getDate());
        schedule.setEmployees(employeeEntities);
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

        return ScheduleMapper.INSTANCE.toDto(scheduleRepository.save(savedSchedule));
    }

    public List<ScheduleDTO> findAll() {
        return scheduleRepository.findAll().stream().map(ScheduleMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
