package org.example.mediqback.addfamily;

import org.example.mediqback.addfamily.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddFamilyRepository extends JpaRepository<Family, Long> {
}
