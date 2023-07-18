package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return scheduleService.save(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return petService.getSchedules(petId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return employeeService.getSchedules(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return customerService.getSchedules(customerId);
    }
}
