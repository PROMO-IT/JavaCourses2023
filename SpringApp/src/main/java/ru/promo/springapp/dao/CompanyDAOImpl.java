package ru.promo.springapp.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Component("companyDAOImpl")
public class CompanyDAOImpl implements CompanyDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(Company company) {
        entityManager.persist(company);
    }

    @Override
    public Company findById(long id) {
        Company company = entityManager.find(Company.class, id);
        return company;
    }

    @Override
    public List<Company> findByType(CompanyType type) {
        return entityManager.createQuery("select c from Company c where c.companyType = :type", Company.class)
                .setParameter("type", type)
                .getResultList();
    }
}
