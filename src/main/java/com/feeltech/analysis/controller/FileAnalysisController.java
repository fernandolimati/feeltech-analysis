package com.feeltech.analysis.controller;

import com.feeltech.analysis.model.Client;
import com.feeltech.analysis.model.Sale;
import com.feeltech.analysis.model.Vendor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/


public class FileAnalysisController {

    private static final String FILE_IN_EXTENSION = ".dat";
    private static final String FILE_OUT_EXTENSION = ".done.dat";
    private static final String COLUMN_SEPARATOR = "\\u00E7";
    private final String dataInFolderPath;
    private final String dataOutFolderPath;
    private static FileAnalysisController instance;

    private FileAnalysisController() {
        String homePathEnv;
        String winHomeDrive = System.getenv("HOMEDRIVE");
        if(!winHomeDrive.isEmpty()) homePathEnv = winHomeDrive.concat(System.getenv("HOMEPATH"));
        else homePathEnv = System.getenv ("HOMEPATH");
        String dataPath = homePathEnv
                .concat(File.separator)
                .concat("data")
                .concat(File.separator);

        dataInFolderPath = dataPath
                .concat("in")
                .concat(File.separator);

        dataOutFolderPath = dataPath
                .concat("out")
                .concat(File.separator);
    }

    public static synchronized FileAnalysisController getInstance() {
        if (instance == null) instance = new FileAnalysisController();
        return instance;
    }

    public void generateReportFromAllFiles() throws IOException {
        File folderIn = new File(dataInFolderPath);

        if(!folderIn.exists()) throw new FileNotFoundException("Input folder not found, path: ".concat(dataInFolderPath));

        List<File> inputFiles;
        inputFiles = readAllInputFilesFromFolder(folderIn);

        for(File fileInput:inputFiles) {
            if(!isInputFileAlreadyDone(fileInput))
                generateFileReport(fileInput);
        }

    }

    private boolean isInputFileAlreadyDone(File fileInput){
        return new File(dataOutFolderPath.concat(fileInput.getName().replace(".dat",".done.dat"))).exists();
    }

    private void generateFileReport(File file) throws IOException {
        List<Vendor> vendors = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        List<Sale> sales = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
        for(String line:lines){
            String[] column = line.split(COLUMN_SEPARATOR);
            switch (column[0]){
                case "001":
                    Vendor v = new Vendor(column[1],column[2],Double.parseDouble(column[3]));
                    vendors.add(v);
                    break;
                case "002":
                    Client c = new Client(column[1],column[2],column[3]);
                    clients.add(c);
                    break;
                case "003":
                    Sale s = new Sale(column[1],column[2],column[3]);
                    sales.add(s);
                    break;
                default:
                    break;
            }
        }

        sales.sort((o1, o2) -> Double.compare(o2.getBiggerSaleValue(), o1.getBiggerSaleValue()));
        String biggerSaleID = sales.get(0).getBiggerSaleID();
        sales.sort((o1, o2) -> Double.compare(o2.getTotalSaleValue(), o1.getTotalSaleValue()));
        String worstSalesmanName = sales.get(sales.size()-1).getSalesmanName();

        List<String> reportText = new ArrayList<>();
        reportText.add("Quantidade de clientes: ".concat(String.valueOf(clients.size())));
        reportText.add("Quantidade de vendedores: ".concat(String.valueOf(vendors.size())));
        reportText.add("ID venda de maior valor: ".concat(biggerSaleID));
        reportText.add("Pior Vendedor: ".concat(worstSalesmanName));

        writeOutputFile(file.getName(), reportText);
    }

    private List<File> readAllInputFilesFromFolder(final File folder) throws IOException {
        List<File> filesInFolder;
        filesInFolder = Files.walk(Paths.get(folder.getPath()))
                .filter(Files::isRegularFile)
                .filter(f -> f.toString().endsWith(FILE_IN_EXTENSION))
                .map(Path::toFile)
                .collect(Collectors.toList());
        return filesInFolder;
    }

    private void writeOutputFile(String fileName, List<String> reportText) throws IOException {
        String fileOutputPath = dataOutFolderPath.concat(fileName.replace(FILE_IN_EXTENSION, FILE_OUT_EXTENSION));
        new File(dataOutFolderPath).mkdirs();
        FileOutputStream outputStream = new FileOutputStream(fileOutputPath);

        for(String line:reportText) outputStream.write(line.concat("\n").getBytes(Charset.forName("UTF-8")));

        outputStream.close();
    }

}
