package org.jlab.cnm.business.session;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jlab.cnm.persistence.entity.SectorCode;
import org.jlab.cnm.persistence.entity.SystemCode;
import org.jlab.cnm.persistence.entity.TypeCode;

/**
 * @author ryans
 */
@Stateless
public class ExcelCodeListFacade {

  @PermitAll
  public void export(
      OutputStream out,
      List<SystemCode> systemList,
      List<TypeCode> typeList,
      List<SectorCode> sectorList)
      throws IOException {
    Workbook wb = new XSSFWorkbook();

    // START Sheet 1
    Sheet sheet1 = wb.createSheet("System Codes");

    int rownum = 0;

    for (SystemCode code : systemList) {
      Row row = sheet1.createRow(rownum++);

      row.createCell(0).setCellValue(String.valueOf(code.getSCode()));
      row.createCell(1).setCellValue(code.getDescription());
    }

    sheet1.autoSizeColumn(0);
    sheet1.autoSizeColumn(1);

    // END Sheet 1
    // START Sheet 2

    Sheet sheet2 = wb.createSheet("Type Codes");

    rownum = 0;

    for (TypeCode code : typeList) {
      Row row = sheet2.createRow(rownum++);

      row.createCell(0).setCellValue(String.valueOf(code.getSystemCode().getSCode()));
      row.createCell(1).setCellValue(code.getVvCode());
      row.createCell(2).setCellValue(code.getDescription());
      row.createCell(3).setCellValue(code.getGrouping());
    }

    sheet2.autoSizeColumn(0);
    sheet2.autoSizeColumn(1);
    sheet2.autoSizeColumn(2);
    sheet2.autoSizeColumn(3);

    // END Sheet 2
    // START Sheet 3

    Sheet sheet3 = wb.createSheet("Sector Codes");

    rownum = 0;

    for (SectorCode code : sectorList) {
      Row row = sheet3.createRow(rownum++);

      row.createCell(0).setCellValue(code.getXxCode());
      row.createCell(1).setCellValue(code.getDescription());
      row.createCell(2).setCellValue(code.getGrouping());
    }

    sheet3.autoSizeColumn(0);
    sheet3.autoSizeColumn(1);
    sheet3.autoSizeColumn(2);

    // END Sheet 3

    wb.write(out);
  }
}
