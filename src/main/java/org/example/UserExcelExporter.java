package org.example;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.example.entities.GymPerson;
import org.example.entities.GymSeasonTicket;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;

public class UserExcelExporter {

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFSheet analyticSheet;
    private final List<GymPerson> gymPersonList;
    private final GymPersonRepository gymPersonRepository;
    private final GymSeasonTicketRepository gymSeasonTicketRepository;

    public UserExcelExporter(List<GymPerson> gymPersonList,
                             GymPersonRepository gymPersonRepository,
                             GymSeasonTicketRepository gymSeasonTicketRepository) {
        this.gymPersonList = gymPersonList;
        this.gymPersonRepository = gymPersonRepository;
        this.gymSeasonTicketRepository = gymSeasonTicketRepository;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        String[] headers = {"User ID", "E-mail", "Full Name", "Phone Number",
                "Telegram account", "Age", "Gender", "Ticket Type"};

        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], style);
        }
    }

    private void writeHeaderForAnalyticChart() {
        analyticSheet = workbook.createSheet("Analytic Diagram");
        Row row = analyticSheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Ticket Type", style);
        createCell(row, 1, "Amount", style);
    }

    private int writeDataLinesForAnalyticChart() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (GymSeasonTicket ticket : gymSeasonTicketRepository.findAll()) {
            Row row = analyticSheet.createRow(rowCount++);
            int columnCount = 0;

            String ticketType = ticket.getTicketType() != null ? ticket.getTicketType() : "NO TICKET TYPE";

            long count = gymPersonRepository.countBySeasonTicket_TicketType(ticketType);

            createCell(row, columnCount++, ticketType, style);
            createCell(row, columnCount++, (double) count, style);
        }

        return rowCount;
    }

    private void analyticDiagram(int lastRow) {
        XSSFDrawing drawing = analyticSheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 10, 25);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Ticket Type Analytic");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(
                analyticSheet, new CellRangeAddress(1, lastRow - 1, 0, 0)
        );
        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(
                analyticSheet, new CellRangeAddress(1, lastRow - 1, 1, 1)
        );

        XDDFChartData data = new XDDFPieChartData(chart.getCTChart().getPlotArea().addNewPieChart());
        data.setVaryColors(true);
        data.addSeries(categories, values);
        chart.plot(data);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        if (sheet != null) sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) cell.setCellValue((Integer) value);
        else if (value instanceof Long) cell.setCellValue((Long) value);
        else if (value instanceof Boolean) cell.setCellValue((Boolean) value);
        else if (value instanceof Double) cell.setCellValue((Double) value);
        else cell.setCellValue(value != null ? value.toString() : "");

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (GymPerson person : gymPersonList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, person.getId(), style);
            createCell(row, columnCount++, person.getEmail(), style);
            createCell(row, columnCount++, person.getName(), style);
            createCell(row, columnCount++, person.getPhoneNumber(), style);
            createCell(row, columnCount++, person.getTelegramAccount(), style);
            createCell(row, columnCount++, person.getAge(), style);
            createCell(row, columnCount++, person.getGender() != null ? person.getGender().toString() : "NO GENDER", style);
            createCell(row, columnCount++,
                    person.getSeasonTicket() != null
                            ? (person.getSeasonTicket().getTicketType() != null
                            ? person.getSeasonTicket().getTicketType()
                            : "NO TICKET TYPE")
                            : "NO TICKET", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        writeHeaderForAnalyticChart();
        int lastRow = writeDataLinesForAnalyticChart();
        analyticDiagram(lastRow);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
