package com.feeltech.analysis.controller;

import com.feeltech.analysis.model.Client;
import com.feeltech.analysis.model.Sale;
import com.feeltech.analysis.model.Vendor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private final static String FILE_IN_EXTENSION = ".dat";
    private final static String FILE_OUT_EXTENSION = ".done.dat";
    private final static String COLUMN_SEPARATOR = "\\u00E7";
    private String dataInFolderPath;
    private String dataOutFolderPath;
    private static String homePathEnv;
    private static FileAnalysisController instance;

    private FileAnalysisController() {
        String winHomeDrive = System.getenv("HOMEDRIVE");
        if(!winHomeDrive.isEmpty()) homePathEnv = new StringBuilder().append(winHomeDrive).append(System.getenv ("HOMEPATH")).toString();
        else homePathEnv = System.getenv ("HOMEPATH");
        dataInFolderPath = new StringBuilder()
                .append(homePathEnv)
                .append(File.separator).append("data")
                .append(File.separator).append("in")
                .append(File.separator)
                .toString();
        dataOutFolderPath = new StringBuilder()
                .append(homePathEnv)
                .append(File.separator).append("data")
                .append(File.separator).append("out")
                .append(File.separator)
                .toString();
    }

    public static synchronized FileAnalysisController getInstance() {
        if (instance == null) instance = new FileAnalysisController();
        return instance;
    }

    public void processInputFiles(){
        List<File> inputFiles = null;
        try {
            inputFiles = readAllInputFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(File file:inputFiles){
            processFile(file);
        }

    }

    private void processFile(File file){
        List<Vendor> vendors = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        List<Sale> sales = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
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
                }
            }
        } catch (IOException e) {
        }

        List<String> reportText = new ArrayList<>();
        reportText.add(new StringBuilder().append("Quantidade de clientes: ").append(clients.size()).toString());
        reportText.add(new StringBuilder().append("Quantidade de vendedores: ").append(vendors.size()).toString());
        sales.sort((o1, o2) -> Double.compare(o2.getBiggerSaleValue(), o1.getBiggerSaleValue()));
        reportText.add(new StringBuilder().append("ID venda de maior valor: ").append(sales.get(0).getBiggerSaleID()).toString());
        sales.sort((o1, o2) -> Double.compare(o2.getTotalSaleValue(), o1.getTotalSaleValue()));
        reportText.add(new StringBuilder().append("Pior Vendedor: ").append(sales.get(sales.size()-1).getSalesmanName()).toString());

        if(writeOutputFile(file.getName(), reportText)) if(file.exists())file.delete();

    }

    private boolean writeOutputFile(String fileName, List<String> reportText){
        String fileOutputPath = new StringBuilder().append(dataOutFolderPath).append(fileName.replace(FILE_IN_EXTENSION,FILE_OUT_EXTENSION)).toString();
        try {
            FileOutputStream outputStream = new FileOutputStream(fileOutputPath);
            for(String line:reportText){
                outputStream.write(line.getBytes());
                outputStream.write(new String("\n").getBytes());
            }
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private List<File> readAllInputFiles() throws FileNotFoundException {
        File folderIn = new File(dataInFolderPath);
        File folderOut = new File(dataOutFolderPath);

        if(!folderIn.exists()) throw new FileNotFoundException(new StringBuilder().append("Folder not found, path: ").append(dataInFolderPath).toString());
        if(!folderOut.exists()) folderOut.mkdirs();

        return listFilesFromFolder(folderIn);
    }

    private List<File> listFilesFromFolder(final File folder) {
        List<File> filesInFolder = null;
        try {
            filesInFolder = Files.walk(Paths.get(folder.getPath()))
                    .filter(Files::isRegularFile)
                    .filter(f -> f.toString().endsWith(FILE_IN_EXTENSION))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (Exception e){
        } finally {
            return filesInFolder;
        }
    }

}
