package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PetService {
    private final IPetRepository petRepository;
    private final ICustomerRepository customerRepository;

    public PetEntity save(PetEntity petEntity) {
        //find customer
        Optional<CustomerEntity> customerEntity = customerRepository.findById(petEntity.getOwner().getId());

        if (customerEntity.isEmpty()) {
            return null;
        }

        //save pet
        PetEntity savedEntity = petRepository.save(petEntity);

        //update customer
        List<PetEntity> pets = customerEntity.get().getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(petEntity);
        customerEntity.get().setPets(pets);
        //save customer
        customerRepository.save(customerEntity.get());

        return savedEntity;
    }

    public Optional<PetEntity> findById(long petId) {
        return petRepository.findById(petId);
    }

    public List<PetEntity> getAll() {
        return petRepository.findAll();
    }

    public List<PetEntity> getByOwner(long ownerId) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(ownerId);
        if (customerEntity.isEmpty()) {
            return new ArrayList<>();
        }

        return customerEntity.get().getPets();
    }

    public List<ScheduleEntity> getSchedules(long petId) {
        return petRepository.findById(petId).map(PetEntity::getSchedules).orElse(new ArrayList<>());
    }

    public List<PetEntity> findAllById(List<Long> petIds) {
        if (petIds == null) {
            return new ArrayList<>();
        }
        return petRepository.findAllById(petIds);
    }
}
