package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
//    @Query("SELECT NEW com.example.CustomDTO(e.id, e.name, s.skill) " +
//            "FROM Employee e " +
//            "LEFT JOIN e.skills s " +
//            "LEFT JOIN e.schedules sc " +
//            "WHERE s.skill IN :skills " +
//            "AND sc.day IN :days " +
//            "AND sc.type = 'EMPLOYEE'")
//    List<CustomDTO> findBySkillsAndDays(@Param("skills") String[] skills, @Param("days") String[] days);

}
