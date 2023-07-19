package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PetService petService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleEntity entity = ScheduleMapper.INSTANCE.toEntity(scheduleDTO);
        entity.setEmployees(employeeService.findAllById(scheduleDTO.getEmployeeIds()));
        entity.setPets(petService.findAllById(scheduleDTO.getPetIds()));
        return ScheduleMapper.INSTANCE.toDto(scheduleService.save(entity));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream().map(ScheduleMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return petService.getSchedules(petId)
                .stream().map(ScheduleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return employeeService.getSchedules(employeeId)
                .stream().map(ScheduleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return customerService.getSchedules(customerId)
                .stream()
                .map(ScheduleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
