package ru.promo.springapp.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@JsonIncludeProperties(value = {"id", "title", "description", "salary", "status"})
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private double salary;

    private String description;

    @Enumerated(EnumType.STRING)
    private VacancyStatus status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", salary=" + salary +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
