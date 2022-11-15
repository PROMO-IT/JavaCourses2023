package ru.promo.springapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.promo.springapp.model.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Profile findByLogin(String login);
}
