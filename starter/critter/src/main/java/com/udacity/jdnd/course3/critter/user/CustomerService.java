package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.IPetRepository;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final IPetRepository petRepository;

    public CustomerEntity save(CustomerEntity entity) {
        return customerRepository.save(entity);
    }

    public List<CustomerEntity> getAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity getByPet(long petId) {
        return petRepository.findById(petId).map(PetEntity::getOwner).orElse(null);
    }

    public List<ScheduleEntity> getSchedules(long customerId) {
        List<PetEntity> petEntities = customerRepository
                .findById(customerId)
                .map(CustomerEntity::getPets)
                .orElse(new ArrayList<>());

        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        petEntities.forEach(petEntity -> scheduleEntities.addAll(petEntity.getSchedules()));

        return scheduleEntities;
    }

    public Optional<CustomerEntity> findById(long ownerId) {
        return customerRepository.findById(ownerId);
    }
}
