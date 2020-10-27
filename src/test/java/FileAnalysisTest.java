import com.feeltech.analysis.controller.FileAnalysisController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

class FileAnalysisTest {

    private static String fileName;

    @Test
    void testProcessing() throws IOException {
        createTestFileInput();
        FileAnalysisController.getInstance().generateReportFromAllFiles();
        List<String> lines = readFileOutput(fileName.concat(".done.dat"));
        assertEquals("Quantidade de clientes: 2", lines.get(0));
        assertEquals("Quantidade de vendedores: 2", lines.get(1));
        assertEquals("ID venda de maior valor: 1", lines.get(2));
        assertEquals("Pior Vendedor: Paulo", lines.get(3));
    }

    private void createTestFileInput() throws IOException {
        fileName = "file-teste-junit-".concat(String.valueOf(new Date().getTime()));
        String homePathEnv;
        String dataInFolderPath;
        String winHomeDrive = System.getenv("HOMEDRIVE");
        if(!winHomeDrive.isEmpty()) homePathEnv = winHomeDrive.concat(System.getenv("HOMEPATH"));
        else homePathEnv = System.getenv ("HOMEPATH");
        dataInFolderPath = homePathEnv
                .concat(File.separator)
                .concat("data")
                .concat(File.separator)
                .concat("in")
                .concat(File.separator);

        String inputText = ("001ç1234567891234çPedroç50000\n")
                .concat("001ç3245678865434çPauloç40000.99\n")
                .concat("002ç2345675434544345çJose da SilvaçRural\n")
                .concat("002ç2345675433444345çEduardo PereiraçRural\n")
                .concat("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro\n")
                .concat("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        File file = new File(dataInFolderPath);
        file.mkdirs();

        String fileFullPath = (dataInFolderPath)
                .concat(fileName)
                .concat(".dat");

        FileOutputStream outputStream = new FileOutputStream(fileFullPath);
        outputStream.write(inputText.getBytes(Charset.forName("UTF-8")));
        outputStream.close();

    }
    private List<String> readFileOutput(String fileName) throws IOException {
        String homePathEnv;
        String dataOutFolderPath;
        String winHomeDrive = System.getenv("HOMEDRIVE");
        if(!winHomeDrive.isEmpty()) homePathEnv = winHomeDrive.concat(System.getenv("HOMEPATH"));
        else homePathEnv = System.getenv ("HOMEPATH");
        dataOutFolderPath = (homePathEnv)
                .concat(File.separator).concat("data")
                .concat(File.separator).concat("out")
                .concat(File.separator);
        return Files.readAllLines(Paths.get(dataOutFolderPath.concat(fileName)));
    }
}
