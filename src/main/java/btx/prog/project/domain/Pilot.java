package btx.prog.project.domain;

import java.time.LocalDate;

/**
 * Instances of this class represents a pilot. Besides inheriting from Employee it has an extra enum containing
 * ranks of a pilots.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class Pilot extends Employee {


    public enum Rank {
        CAPTAIN, FIRSTOFFICER, SENIORFIRSTOFFICER, TRAININGCAPTAIN
    }

    private Rank rank;

    /**
     * The constructor contains everything from Employee.
     *
     * @param firstName
     * @param lastName
     * @param gender
     * @param birthdate
     * @param hiredSince
     * @param salary
     * @param rank
     */
    public Pilot(String firstName, String lastName, Gender gender, LocalDate birthdate, LocalDate hiredSince,
                 double salary, Rank rank) {
        super(firstName, lastName, gender, birthdate, hiredSince, salary);
        this.rank = rank;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }


    @Override
    public String toString() {

        return "Pilot ( " + super.toString() + " the rank of pilot is " + this.rank + " )";
    }


}
