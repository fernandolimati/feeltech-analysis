import com.feeltech.analysis.controller.FileAnalysisController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

public class FileAnalysisTest {

    @Test
    public void testProcessing() throws IOException {
        FileAnalysisController.getInstance().processInputFiles();
        List<String> lines = readFile("file-teste-junit.done.dat");
        assertEquals(lines.get(0),"Quantidade de clientes: 2");
        assertEquals(lines.get(1),"Quantidade de vendedores: 2");
        assertEquals(lines.get(2),"ID venda de maior valor: 1");
        assertEquals(lines.get(3),"Pior Vendedor: Paulo");
    }

    @BeforeAll
    public static void beforeTest(){
        String homePathEnv;
        String dataInFolderPath;
        String winHomeDrive = System.getenv("HOMEDRIVE");
        if(!winHomeDrive.isEmpty()) homePathEnv = new StringBuilder().append(winHomeDrive).append(System.getenv ("HOMEPATH")).toString();
        else homePathEnv = System.getenv ("HOMEPATH");
        dataInFolderPath = new StringBuilder()
                .append(homePathEnv)
                .append(File.separator).append("data")
                .append(File.separator).append("in")
                .append(File.separator)
                .toString();

        StringBuilder inputText = new StringBuilder()
                .append("001ç1234567891234çPedroç50000")
                .append("\n")
                .append("001ç3245678865434çPauloç40000.99")
                .append("\n")
                .append("002ç2345675434544345çJose da SilvaçRural")
                .append("\n")
                .append("002ç2345675433444345çEduardo PereiraçRural")
                .append("\n")
                .append("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro")
                .append("\n")
                .append("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        File file = new File(dataInFolderPath);
        file.mkdirs();

       String fileFullPath = new StringBuilder()
                .append(dataInFolderPath)
                .append(File.separator)
                .append("file-teste-junit")
                .append(".dat")
                .toString();

        try {
            FileOutputStream outputStream = new FileOutputStream(fileFullPath);
            outputStream.write(inputText.toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    private List<String> readFile(String fileName) throws IOException {
        String homePathEnv;
        String dataOutFolderPath;
        String winHomeDrive = System.getenv("HOMEDRIVE");
        if(!winHomeDrive.isEmpty()) homePathEnv = new StringBuilder().append(winHomeDrive).append(System.getenv ("HOMEPATH")).toString();
        else homePathEnv = System.getenv ("HOMEPATH");
        dataOutFolderPath = new StringBuilder()
                .append(homePathEnv)
                .append(File.separator).append("data")
                .append(File.separator).append("out")
                .append(File.separator)
                .toString();
        List<String> lines = Files.readAllLines(Paths.get(new StringBuilder()
                .append(dataOutFolderPath)
                .append(fileName)
                .toString()));
        return lines;
    }
}
