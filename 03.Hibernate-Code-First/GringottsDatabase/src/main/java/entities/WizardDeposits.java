package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposits extends BaseEntity{
    private String first_name;
    private String last_name;
    private String notes;
    private Integer age;
    private String magicWandCreator;
    private Short magicWandSize;
    private String depositGroup;
    private LocalDateTime depositStartDate;
    private Double depositAmount;
    private Double depositInterest;
    private Double depositCharge;
    private LocalDateTime depositExpirationDate;
    private Boolean isDepositExpired;

    public WizardDeposits() {
    }

    @Column(name = "first_name", length = 50)
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Column(name = "last_name", nullable = false, length = 60)
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Column(name = "notes", columnDefinition = "TEXT")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "magic_wand_creator", length = 100)
    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    @Column(name = "magic_wand_size", nullable = false)
    public Short getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(Short magicWandSize) {
        this.magicWandSize = magicWandSize;
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }

    @Column(name = "deposit_start_date")
    public LocalDateTime getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(LocalDateTime depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    @Column(name = "deposit_amount", precision = 10, scale = 3)
    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Column(name = "deposit_interest")
    public Double getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(Double depositInterest) {
        this.depositInterest = depositInterest;
    }

    @Column(name = "deposit_charge")
    public Double getDepositCharge() {
        return depositCharge;
    }

    public void setDepositCharge(Double depositCharge) {
        this.depositCharge = depositCharge;
    }

    @Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
    }

    @Column(name = "is_deposit_expired")
    public Boolean getDepositExpired() {
        return isDepositExpired;
    }

    public void setDepositExpired(Boolean depositExpired) {
        isDepositExpired = depositExpired;
    }
}