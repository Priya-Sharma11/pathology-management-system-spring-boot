package com.example.Pathology.Service;

import com.example.Pathology.Entity.Appointment;
import com.example.Pathology.Entity.Test;
import com.example.Pathology.Entity.User;
import com.example.Pathology.Repository.AppointmentRepository;
import com.example.Pathology.Repository.TestRepository;
import com.example.Pathology.Repository.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentByUserId(Long userId){
        return appointmentRepository.findByUserId(userId);
    }

    public Appointment createAppointment(Appointment appointment) {

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // After saving, generate the confirmation slip (optional)
        generateConfirmationSlip(savedAppointment.getId());

        return savedAppointment;
    }

    public Appointment updateAppointment(Long id, Appointment appointment) {
        appointment.setId(id);
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public String generateConfirmationSlip(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        String directoryPath = "confirmation_slips";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }

        Document document = new Document();
        String filePath = "confirmation_slips/appointment_" + appointment.getId() + ".pdf";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Appointment Confirmation Slip"));
            document.add(new Paragraph("Appointment ID: " + appointment.getId()));
            document.add(new Paragraph("User: " + appointment.getUser().getName()));
            document.add(new Paragraph("Test: " + appointment.getTest().getTestName()));
            document.add(new Paragraph("Date: " + appointment.getAppointmentDate()));
            document.add(new Paragraph("Status: " + appointment.getStatus()));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }

}
