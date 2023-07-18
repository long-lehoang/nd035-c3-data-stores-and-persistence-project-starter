package com.udacity.jdnd.course3.critter.pet;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return petService.save(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petService.findById(petId);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getAll();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getByOwner(ownerId);
    }
}
