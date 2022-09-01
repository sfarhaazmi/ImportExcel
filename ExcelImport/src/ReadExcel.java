import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.concurrent.Callable;

public class ReadExcel implements Callable<DataVO> {
    int row;
    //int col;
    XSSFWorkbook workbook;
    DataVO data;

    public ReadExcel(XSSFWorkbook workbook, int row, DataVO data) {
        this.row = row;
        //this.col=col;
        this.workbook = workbook;
        this.data = data;
    }
    DataFormatter dataFormatter = new DataFormatter();
    @Override
    public DataVO call() {
        System.out.println(Thread.currentThread().getName()+ "[Row Inhand] row = "+row);
        boolean testVar = true;
        for (int col = 0; col < 4; col++){
        String formattedData = dataFormatter.formatCellValue(workbook.getSheetAt(0).getRow(row).getCell(col));
        //System.out.println("ROW: " + row + "COL: " + col + "DATA: " + formattedData);
        if (row == 0) {
            if (headerInvalid(formattedData, col)) {
                data.errMsg = "Invalid file headers";
            } else {
                data.validHeaders=true;
                data.errMsg = "All headers are valid";
            }
        } else {
            switch (col) {
                case 0: {
                    if (testVar) {
                        data.orderID = Integer.parseInt(formattedData);
                    }else{
                        data.invalidOrderId ="Invalid Order ID";
                    }
                    break;
                }
                case 1: {
                    if (testVar) {
                        data.itemName = formattedData;
                    }else{
                        data.invalidItemName ="Invalid Item name";
                    }
                    break;
                }
                case 2: {
                    if (testVar) {
                        data.quantity = Integer.parseInt(formattedData);
                    }else{
                        data.invalidQuantity ="Invalid Quantity";
                    }
                    break;
                }
                case 3: {
                    if (testVar) {
                        data.price = Integer.parseInt(formattedData);
                    }else{
                        data.invalidPrice ="Invalid Price";
                    }
                    break;
                }
            }
        }
        //System.out.println(data.orderID + data.itemName + data.quantity + data.price);
    }
       return data;
}
    private boolean headerInvalid(String formattedData, int col) {
        if(col==0 && !formattedData.equalsIgnoreCase("ORDER ID")) return true;
           else if(col==1 && !formattedData.equalsIgnoreCase("ITEM NAME")) return true;
               else if(col==2 && !formattedData.equalsIgnoreCase("QUANTITY")) return true;
                  else if(col==3 && !formattedData.equalsIgnoreCase("PRICE")) return true;
                     else return false;
    }
}