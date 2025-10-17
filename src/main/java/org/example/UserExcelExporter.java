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
import org.example.entities.enums.Gender;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;

import org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls;

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


    private void createAnalyticDiagram(String sheetName,
                                       String firstHeader,
                                       String secondHeader,
                                       List <Object[]> dataList,
                                       String chartTitle){
        XSSFSheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);

        //header - (заголовок)
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight(16);
        headerStyle.setFont(headerFont);

        createCell(headerRow, 0, firstHeader, headerStyle);
        createCell(headerRow, 1, secondHeader, headerStyle);

        int rowCount = 1;

        // стиль для данних, пишу шоб не загубитись в майбутньому
        CellStyle dataStyle = workbook.createCellStyle();
        XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataStyle.setFont(dataFont);

        for (Object[] objects: dataList) {
            Row row = sheet.createRow(rowCount++);
            createCell(row, 0, objects[0] , dataStyle);
            createCell(row, 1, objects[1] , dataStyle);
        }

        //безспесередньо створення діаграм
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 10, 25);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(chartTitle);
        chart.setTitleOverlay(false);

// легенда
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        // Джерела даних
        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(
                sheet, new CellRangeAddress(1, rowCount - 1, 0, 0)
        );
        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(
                sheet, new CellRangeAddress(1, rowCount - 1, 1, 1)
        );

        // Створюємо PieChartData через chart.createData
        XDDFPieChartData data = (XDDFPieChartData) chart.createData(ChartTypes.PIE, null, null);
        data.setVaryColors(true);

        // Додаємо серію
        XDDFPieChartData.Series series = (XDDFPieChartData.Series) data.addSeries(categories, values);

        // Створюємо Data Labels через CTDLbls
        CTDLbls ctDLbls = series.getCTPieSer().addNewDLbls();
        ctDLbls.addNewShowPercent().setVal(true);     // Відсотки
        ctDLbls.addNewShowVal().setVal(false);        // Не показуємо абсолютне значення
        ctDLbls.addNewShowCatName().setVal(true);     // Показуємо ім’я категорії
        ctDLbls.addNewShowLeaderLines().setVal(true); // Лінії до підписів

        // Відображаємо діаграму
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

        /* //chart with analytic chart by ticketTypess
        writeHeaderForAnalyticChart(analyticSheet, "TicketType", "Amount", "TicketTypeAnalyticChart");
        int lastRow = writeDataLinesForAnalyticChart();
        analyticDiagram(lastRow, analyticSheet,  "Ticket Type Analytic");

        //chart with anallytic chart by gender
        writeHeaderForAnalyticChart();*/


        List<Object[]> ticketData = gymSeasonTicketRepository.findAll().stream()
                .map(ticket -> new Object[]{
                        ticket.getTicketType() != null ? ticket.getTicketType() : "NO TICKET TYPE",
                        (double) gymPersonRepository.countBySeasonTicket_TicketType(ticket.getTicketType())
                })
                .toList();
        createAnalyticDiagram("analyticTicketTypeChart", "TicketType", "Amount", ticketData, "TicketTypeTOAmount");

        List<Object[]> genderData = List.of(
                new Object[]{"Male", (double) gymPersonRepository.countByGender(Gender.Male)},
                new Object[]{"Female", (double) gymPersonRepository.countByGender(Gender.Female)},
                new Object[]{"Other", (double) gymPersonRepository.countByGender(Gender.Other)}
        );

        createAnalyticDiagram("analyticGenderChart", "Gender", "Amount", genderData, "GenderTOAmount");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
