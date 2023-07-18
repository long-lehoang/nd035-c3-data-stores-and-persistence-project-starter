package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.mapper.PetMapper;
import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PetService {
    private final IPetRepository petRepository;
    private final ICustomerRepository customerRepository;

    public PetDTO save(PetDTO petDTO) {
        //find customer
        Optional<CustomerEntity> customerEntity = customerRepository.findById(petDTO.getOwnerId());

        if (customerEntity.isEmpty()) {
            return new PetDTO();
        }

        //prepare pet entity
        PetEntity petEntity = new PetEntity();
        petEntity.setName(petDTO.getName());
        petEntity.setType(petDTO.getType());
        petEntity.setOwner(customerEntity.get());
        petEntity.setNotes(petDTO.getNotes());
        petEntity.setBirthDate(petDTO.getBirthDate());

        //save
        PetEntity savedEntity = petRepository.saveAndFlush(petEntity);

        //update customer
        List<PetEntity> pets = customerEntity.get().getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(petEntity);
        customerEntity.get().setPets(pets);

        //save customer
        customerRepository.saveAndFlush(customerEntity.get());

        //convert to dto
        return PetMapper.INSTANCE.toDto(savedEntity);
    }

    public PetDTO findById(long petId) {
        Optional<PetEntity> entity = petRepository.findById(petId);

        if (entity.isEmpty()) {
            return null;
        }

        //convert to dto
        return PetMapper.INSTANCE.toDto(entity.get());
    }

    public List<PetDTO> getAll() {
        return petRepository.findAll().stream().map(PetMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public List<PetDTO> getByOwner(long ownerId) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(ownerId);
        if (customerEntity.isEmpty()) {
            return new ArrayList<>();
        }

        return customerEntity.get().getPets().stream().map(PetMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedules(long petId) {
        List<ScheduleEntity> scheduleEntities = petRepository.findById(petId).map(PetEntity::getSchedules).orElse(new ArrayList<>());
        return scheduleEntities.stream().map(ScheduleMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
