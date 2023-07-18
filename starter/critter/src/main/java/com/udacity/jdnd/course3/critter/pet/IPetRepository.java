package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPetRepository extends JpaRepository<PetEntity, Long> {
}
