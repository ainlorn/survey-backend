package com.midgetspinner31.survey;

import com.google.common.net.HttpHeaders;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Component
public class FileExporter {

    public void exportRespondentDetailsToCsv(List<AdditionalRespondentDetails> detailsList,
                                             String[] fields,
                                             HttpServletResponse response,
                                             String surveyId) throws IOException {


        String fileName = surveyId + "_" + "respondent_details.csv";
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + fileName);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);

        csvWriter.writeHeader(fields);

        for (AdditionalRespondentDetails details : detailsList) {
            csvWriter.write(details, fields);
        }
        csvWriter.close();
    }

    public void exportRespondentDetailsToXlsx(List<AdditionalRespondentDetails> detailsList,
                                              String[] fields,
                                              HttpServletResponse response,
                                              String surveyId) throws IOException {


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bos);
//        BufferedWriter bufferedWriter = new BufferedWriter(b)

        ICsvBeanWriter csvWriter = new CsvBeanWriter(outputStreamWriter, CsvPreference.EXCEL_PREFERENCE);


        csvWriter.writeHeader(fields);

        for (AdditionalRespondentDetails details : detailsList) {
            csvWriter.write(details, fields);
        }

        csvWriter.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(bis);

        Sheet sheet = xssfWorkbook.createSheet("Информация");


        //TODO : read from csv and put to excel
        System.out.println(bos);
    }
}
