package Entities;

import javax.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {
    private String name;
    private Diagnose diagnose;
    public Medicament() {
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
