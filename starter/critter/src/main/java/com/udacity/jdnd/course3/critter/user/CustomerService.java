package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.mapper.CustomerMapper;
import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.pet.IPetRepository;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final IPetRepository petRepository;

    public CustomerDTO save(CustomerDTO customerDTO) {
        CustomerEntity entity = new CustomerEntity();
        entity.setPhoneNumber(customerDTO.getPhoneNumber());
        entity.setName(customerDTO.getName());

        CustomerEntity saved = customerRepository.save(entity);

        return CustomerMapper.INSTANCE.toDto(saved);
    }

    public List<CustomerDTO> getAll() {
        List<CustomerEntity> list = customerRepository.findAll();
        return list.stream().map(CustomerMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public CustomerDTO getByPet(long petId) {
        return petRepository.findById(petId).map(PetEntity::getOwner).map(CustomerMapper.INSTANCE::toDto).orElse(null);
    }

    public List<ScheduleDTO> getSchedules(long customerId) {
        List<PetEntity> petEntities = customerRepository
                .findById(customerId)
                .map(CustomerEntity::getPets)
                .orElse(new ArrayList<>());

        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        petEntities.forEach(petEntity -> scheduleEntities.addAll(petEntity.getSchedules()));

        return scheduleEntities.stream().map(ScheduleMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
