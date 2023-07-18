package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByDate(LocalDate date);
}
