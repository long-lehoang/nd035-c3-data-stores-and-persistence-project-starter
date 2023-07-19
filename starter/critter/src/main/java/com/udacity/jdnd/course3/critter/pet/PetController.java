package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.mapper.PetMapper;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@AllArgsConstructor
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetEntity entity = PetMapper.INSTANCE.toEntity(petDTO);
        entity.setOwner(customerService.findById(petDTO.getOwnerId()).orElse(null));
        return PetMapper.INSTANCE.toDto(petService.save(entity));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petService.findById(petId).map(PetMapper.INSTANCE::toDto).orElse(null);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getAll().stream().map(PetMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getByOwner(ownerId).stream().map(PetMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
