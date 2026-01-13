package org.example.controllers.people.export;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.repositories.people_repo.GymVisitorRepository;
import org.example.service.gym_people.data_exporters.GymVisitorsExcelExportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class ExportPeopleToExcel {

    public final GymVisitorRepository gymVisitorRepository;
    public final GymVisitorsExcelExportService excelExporter;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        excelExporter.export(response, gymVisitorRepository.findAll());
    }
}
