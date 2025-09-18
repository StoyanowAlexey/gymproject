package org.example;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DTOs.GymPersonDTO;
import org.example.entities.GymPerson;
import org.example.entities.GymSeasonTicket;
import org.example.mappers.GymPersonMapper;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<GymPerson> gymPersonList;
    private GymPersonMapper gymPersonMapper;

    public UserExcelExporter(List<GymPerson> listUsers) {
        this.gymPersonList = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Phone Number", style);
        createCell(row, 4, "Telegram account", style);
        createCell(row, 5, "Age", style);
        createCell(row, 6, "Gender", style);
        createCell(row, 7, "Ticket Type", style);


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (GymPerson gymPerson : gymPersonList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, gymPerson.getId(), style);
            createCell(row, columnCount++, gymPerson.getGmail(), style);
            createCell(row, columnCount++, gymPerson.getName(), style);
            createCell(row, columnCount++, gymPerson.getPhoneNumber(), style);
            createCell(row, columnCount++, gymPerson.getTelegramAccount(), style);
            createCell(row, columnCount++, gymPerson.getAge(), style);
            createCell(row, columnCount++, gymPerson.getGender().toString(), style);
            createCell(row, columnCount++, gymPerson.getSeasonTicket().getTicketType(), style);
        }


    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
