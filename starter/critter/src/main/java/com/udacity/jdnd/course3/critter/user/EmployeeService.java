package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.mapper.EmployeeMapper;
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

    public EmployeeEntity save(EmployeeEntity entity) {
        return employeeRepository.save(entity);
    }

    public Optional<EmployeeEntity> findById(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        employeeEntity.ifPresent(e -> {
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        });
    }

    public List<EmployeeEntity> getAvailables(EmployeeRequestDTO employeeDTO) {
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
        return matchingSkillEmployees
                .stream()
                .filter(employee -> new HashSet<>(employee.getDaysAvailable()).contains(employeeDTO.getDate().getDayOfWeek()))
                .collect(Collectors.toList());
    }

    public List<ScheduleEntity> getSchedules(long employeeId) {
        return employeeRepository
                .findById(employeeId)
                .map(EmployeeEntity::getSchedules)
                .orElse(new ArrayList<>());
    }

    public List<EmployeeEntity> findAllById(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }
}
