package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.mapper.EmployeeMapper;
import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final IEmployeeRepository employeeRepository;

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setName(employeeDTO.getName());
        entity.setSkills(employeeDTO.getSkills().stream().map(Enum::toString).collect(Collectors.toList()));
        entity.setDaysAvailable(employeeDTO.getDaysAvailable());
        EmployeeEntity saved = employeeRepository.save(entity);

        return EmployeeMapper.INSTANCE.toDto(saved);
    }

    public EmployeeDTO findById(long employeeId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        return employeeEntity.map(EmployeeMapper.INSTANCE::toDto).orElse(null);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        employeeEntity.ifPresent(e -> {
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        });
    }

    public List<EmployeeDTO> getAvailables(EmployeeRequestDTO employeeDTO) {
        // Get available employee by skill
        List<EmployeeEntity> employees = employeeRepository.findAll(); // List of all employees
        List<String> searchSkills = employeeDTO.getSkills() // List of skills to search
                .stream()
                .map(EmployeeSkill::toString)
                .collect(Collectors.toList());

        List<EmployeeEntity> matchingSkillEmployees = employees
                .stream()
                .filter(employee -> new HashSet<>(employee.getSkills()).containsAll(searchSkills))
                .collect(Collectors.toList());

        // Filter available employee by schedule
        List<EmployeeEntity> matchingAvailableEmployees = matchingSkillEmployees
                .stream()
                .filter(employee -> new HashSet<>(employee.getDaysAvailable()).contains(employeeDTO.getDate().getDayOfWeek()))
                .collect(Collectors.toList());

        return matchingAvailableEmployees.stream().map(EmployeeMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedules(long employeeId) {
        List<ScheduleEntity> scheduleEntities = employeeRepository
                .findById(employeeId)
                .map(EmployeeEntity::getSchedules)
                .orElse(new ArrayList<>());
        return scheduleEntities.stream().map(ScheduleMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
