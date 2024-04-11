package com.careerit.jfs.java14features;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;

public class Appointment {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private String id;
    private String location;
    private String doctor;
    private String patient;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;

    public Appointment(AppointmentBuilder builder) {
        if(StringUtils.isEmpty(id)){
            this.id=IdGenerator.generateId();
        }
        this.location=builder.location;
        this.doctor=builder.doctor;
        this.patient=builder.patient;
        this.date=builder.date;
        this.startTime=builder.startTime;
        this.endTime=builder.endTime;
        this.status=builder.status;

    }

    static class AppointmentBuilder{

       private String id;
       private String location;
       private String doctor;
       private String patient;
       private LocalDate date;
       private LocalDateTime startTime;
       private LocalDateTime endTime;
       private Status status;

       public static AppointmentBuilder builder(){
           return new AppointmentBuilder();
       }

       public AppointmentBuilder loction(String locatoin){
           this.location=location;

           return this;
       }
       public AppointmentBuilder doctor(String doctor){
           this.doctor=doctor;

           return this;
       }
       public AppointmentBuilder patient(String patient){
           this.patient=patient;

           return this;
       }
       public AppointmentBuilder date(LocalDate date){
           this.date=date;

           return this;
       }
       public AppointmentBuilder startTime(LocalDateTime startTime){
           this.startTime=startTime;

           return this;
       }
       public AppointmentBuilder endTime(LocalDateTime endTime){
           this.endTime=endTime;

           return this;
       }
       public AppointmentBuilder status(Status status){
           this.status=status;

           return this;
       }

   }
}
