package com.feeltech.analysis;

import com.feeltech.analysis.controller.FileAnalysisController;

import java.io.FileNotFoundException;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

public class MainApplication {

    public static void main(String[] args) throws FileNotFoundException {
        while(true){
            FileAnalysisController.getInstance().processInputFiles();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

    }

}
