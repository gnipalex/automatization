/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Alex
 */
public class XlsParserTest {
    
    public static final String FILE_PATH = "D:\\STUDYING\\JAVA\\AUTOMATIZATION\\0032--all--2013.01.07.xls";
    
    public static void main(String[] args){
        FileInputStream ifile = null;
        try{
            ifile = new FileInputStream(FILE_PATH);
        } catch (FileNotFoundException ex){
            System.out.println("Error occured\n" + ex.getMessage());
            return;
        }
        //файл открыт
        try {
            HSSFWorkbook wbook = new HSSFWorkbook(ifile);
            HSSFSheet wsheet = wbook.getSheet("Лист1");
            if (wsheet == null){
                System.out.println("Sheet doesn't exist");
            } else {
                //открыли книгу и открыли "Лист1"
                int rows_count = wsheet.getLastRowNum();
                System.out.println("Rows count : " + rows_count);
                //пройдемся по строкам
                for (int i=0; i<10 && i<rows_count; i++){
                    HSSFRow wrow = wsheet.getRow(i);
                    int cols_count = wrow.getLastCellNum();
                    System.out.print(i + "# ");
                    //пройдемся по ячейкам строки
                    for (int j=0; j<5 && j<cols_count; j++){
                        HSSFCell wcell = wrow.getCell(j);
                        System.out.print(wcell.getStringCellValue() + "\t");
                    }
                    System.out.println();
                }
            }
            ifile.close();
        } catch (IOException ioex){
            System.out.println("Error occured\n" + ioex.getMessage());
            return;
        }
    }
}
