import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        try {
            FileInputStream fis=new FileInputStream(new File("C:\\Workspace\\sample.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            List<DataVO>futures = new ArrayList<>();
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            for(int row=0; row<=100; row++){
                DataVO data = new DataVO();
                Future<DataVO> future = executorService.submit(new ReadExcel(workbook, row, data));
                futures.add(future.get());
            }

            executorService.shutdown();
            for(int i=0; i<futures.size(); i++) {
                System.out.println("Order Id: "+ futures.get(i).orderID+" "+"Item name: "+
                        futures.get(i).itemName+" "+"Quantity: "+futures.get(i).quantity+" "+"Price: "+
                        futures.get(i).price+" "/*+ "Errors: "+futures.get(i).invalidOrderId+futures.get(i).invalidItemName+
                        futures.get(i).invalidQuantity+futures.get(i).invalidPrice*/);
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}