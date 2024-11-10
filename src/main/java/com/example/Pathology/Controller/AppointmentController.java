package com.example.Pathology.Controller;

import com.example.Pathology.Entity.Appointment;
import com.example.Pathology.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/appointments")
@CrossOrigin("http://localhost:5173/")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable Long userId){
        List<Appointment> appointments = appointmentService.getAppointmentByUserId(userId);
        if(appointments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download-slip/{appointmentId}")
    public ResponseEntity<Resource> downloadConfirmationSlip(@PathVariable Long appointmentId) {
        String filePath = appointmentService.generateConfirmationSlip(appointmentId);

        Resource resource = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"confirmation_slip_" + appointmentId + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }



}
