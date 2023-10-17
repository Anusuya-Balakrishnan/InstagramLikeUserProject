package LikePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExample {
	
	public UserDetailsClass readUserDetails() {
		String currentDirectory = System.getProperty("user.dir");
        String fileName = currentDirectory + File.separator + "userDetails.xlsx";
//		String fileName="userDetails.xlsx";
		String username=null;
		String password = null;
		String sendMessage=null;
		ArrayList<String> postUrl=new ArrayList<String>();
			try (FileInputStream fileInputStream = new FileInputStream(fileName);
	                Workbook workbook = new XSSFWorkbook(fileInputStream)) {
					 // Specify the sheet name where your map data is located
		               Sheet sheet = workbook.getSheet("Data");
		               
		               Iterator<Row> rowIterator = sheet.iterator();
		               while (rowIterator.hasNext()) {
		                   Row row = rowIterator.next();
		                   Cell keyCell = row.getCell(0);//postName
		                   if(keyCell.getStringCellValue().equals("username")) {
		                	   username=row.getCell(1).getStringCellValue();
		                   }
		                   else if(keyCell.getStringCellValue().equals("password")) {
		                	   password=row.getCell(1).getStringCellValue();
		                   }
		                   else if(keyCell.getStringCellValue().equals("postUrl")) {
		                	   int cellNo=1;
		                	   while(row.getCell(cellNo)!=null && !(row.getCell(cellNo).getStringCellValue().isEmpty())) {
		                		   postUrl.add(row.getCell(cellNo).getStringCellValue());
		                		   cellNo++;
		                	   }
		                   }
		                   else if(keyCell.getStringCellValue().equals("sendMessage")) {
		                	   sendMessage=row.getCell(1).getStringCellValue();
		                   }
		                   
		                   else if(keyCell.getStringCellValue().equals("linkMessage")) {
		                	   
		                	   
		                	   if(row.getCell(1)!=null && !(row.getCell(1).getStringCellValue().isEmpty())) {
				            	   sendMessage=sendMessage.concat(" ").concat(row.getCell(1).getStringCellValue());
				               }
		                	   


		                   }

		               }

			

		}catch (IOException e) {
	        e.printStackTrace();
	    }
			System.out.println(sendMessage);
		return new UserDetailsClass(username, password, sendMessage, postUrl);

	}
	public ArrayList<InstagramPerson> readData() {
		String currentDirectory = System.getProperty("user.dir");
        String existingFilePath = currentDirectory + File.separator + "data.xlsx";
//		String existingFilePath="data.xlsx";
		ArrayList<InstagramPerson> existingPerson=new ArrayList<InstagramPerson>();
		try (FileInputStream fileInputStream = new FileInputStream(existingFilePath);
                Workbook workbook = new XSSFWorkbook(fileInputStream)) {

               // Specify the sheet name where your map data is located
               Sheet sheet = workbook.getSheet("Data");
               
               Iterator<Row> rowIterator = sheet.iterator();
               while (rowIterator.hasNext()) {
                   Row row = rowIterator.next();
                   Cell keyCell = row.getCell(0);//postName
                   Cell valueCell1 = row.getCell(1);//personName
                   Cell valueCell2 = row.getCell(2);//personURl
                   Cell valueCell3 = row.getCell(3);//response

                   if (keyCell != null && valueCell1 != null) {
                       String postName = keyCell.getStringCellValue();//postName
                       String personName = valueCell1.getStringCellValue();//personName
                       String personUrl = valueCell2.getStringCellValue();//personName
                       boolean response=valueCell3.getBooleanCellValue();//response
                       existingPerson.add(new InstagramPerson(postName,personName, personUrl, response));
                   }
               }

      
           } catch (IOException e) {
               e.printStackTrace();
           }

		return existingPerson;
	}
	
	
	
	public  void writeData(ArrayList<InstagramPerson> personList) {
		// Specify the existing file you want to append data to
		String currentDirectory = System.getProperty("user.dir");
        String existingFilePath = currentDirectory + File.separator + "data.xlsx";
//        String existingFilePath = "data.xlsx";
        try (FileInputStream existingFileInputStream = new FileInputStream(existingFilePath);
             Workbook existingWorkbook = new XSSFWorkbook(existingFileInputStream)) {

            Sheet sheet = existingWorkbook.getSheet("Data");

            // Calculate the next available row
            int rowNum = sheet.getLastRowNum() + 1;

            
            // Add the new data to the existing sheet
            for (InstagramPerson eachPerson:personList) {
                Row row = sheet.createRow(rowNum++);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(eachPerson.postName);

                Cell cell2 = row.createCell(1);
                cell2.setCellValue(eachPerson.personName);
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(eachPerson.personUrl);
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(eachPerson.response);
            }

            try (FileOutputStream outputStream = new FileOutputStream(existingFilePath)) {
                existingWorkbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public  void writeProgramCount() {
		// Specify the existing file you want to append data to
		String currentDirectory = System.getProperty("user.dir");
        String existingFilePath = currentDirectory + File.separator + "data.xlsx";
//        String existingFilePath = "data.xlsx";
        try (FileInputStream existingFileInputStream = new FileInputStream(existingFilePath);
             Workbook existingWorkbook = new XSSFWorkbook(existingFileInputStream)) {

            Sheet sheet = existingWorkbook.getSheet("Data");

            Iterator<Row> rowIterator = sheet.iterator();
            // Calculate the next available row
            int startRow=0;
            int rowLast = sheet.getLastRowNum();

            while (startRow<=rowLast) {
                Row row = rowIterator.next();
                Cell keyCell = row.getCell(0);//postName
                Cell valueCell1 = row.getCell(1);//personName
                Cell valueCell2 = row.getCell(2);//personURl
                Cell valueCell3 = row.getCell(3);//response

                startRow++;
            }

            // Add the new data to the existing sheet
//            for (InstagramPerson eachPerson:personList) {
//                Row row = sheet.createRow(rowNum++);
//                Cell cell1 = row.createCell(0);
//                cell1.setCellValue(eachPerson.postName);
//
//                Cell cell2 = row.createCell(1);
//                cell2.setCellValue(eachPerson.personName);
//                Cell cell3 = row.createCell(2);
//                cell3.setCellValue(eachPerson.personUrl);
//                Cell cell4 = row.createCell(3);
//                cell4.setCellValue(eachPerson.response);
//            }

            try (FileOutputStream outputStream = new FileOutputStream(existingFilePath)) {
                existingWorkbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		ExcelExample e=new ExcelExample();
		e.readUserDetails();
	}
}
