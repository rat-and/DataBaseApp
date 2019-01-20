/**
 * Created by IRGeekSauce on 11/26/15.
 */

import javafx.beans.property.*;


public class Student {

    private StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
    public String getFirstName() {
        return firstName.get();
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    private StringProperty lastName = new SimpleStringProperty(this, "lastName", "");
    public String getLastName() {return lastName.get();}
    public StringProperty lastNameProperty() {return lastName;}
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    private StringProperty major = new SimpleStringProperty(this, "major", "");
    public String getMajor() {
        return major.get();
    }
    public StringProperty majorProperty() {
        return major;
    }
    public void setMajor(String major) {
        this.major.set(major);
    }

    private DoubleProperty gradepoint = new SimpleDoubleProperty(this, "gradePoint", 0.0);
    public double getGradepoint() {
        return gradepoint.get();
    }
    public DoubleProperty gradepointProperty() {
        return gradepoint;
    }
    public void setGradepoint(double gradepoint) {
        this.gradepoint.set(gradepoint);
    }

    private StringProperty uin = new SimpleStringProperty(this, "uin", "");
    public String getUin() {
        return uin.get();
    }
    public StringProperty uinProperty() {
        return uin;
    }
    public void setUin(String uin) {
        this.uin.set(uin);
    }

    private StringProperty netID = new SimpleStringProperty(this, "netID", "");
    public String getNetID() {
        return netID.get();
    }
    public StringProperty netIDProperty() {
        return netID;
    }
    public void setNetID(String netID) {
        this.netID.set(netID);
    }

    private IntegerProperty age = new SimpleIntegerProperty(this, "age", 0);
    public int getAge() {
        return age.get();
    }
    public IntegerProperty ageProperty() {
        return age;
    }
    public void setAge(int age) {
        this.age.set(age);
    }

    private StringProperty gender = new SimpleStringProperty(this, "gender", "");
    public String getGender() {
        return gender.get();
    }
    public StringProperty genderProperty() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender.set(gender);
    }


    //for console printing purposes
    public String toString() {

        return "First Name: " + getFirstName() + " | Last Name: " + getLastName() + " | UIN: " + getUin() + " | NetID: " + getNetID() + " | Major: " + getMajor() + " | Age: " + getAge() + " | GPA: " + getGradepoint() + " | Gender: " + getGender();
    }
}

