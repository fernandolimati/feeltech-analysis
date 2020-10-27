package com.feeltech.analysis;

import com.feeltech.analysis.controller.FileAnalysisController;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

@Slf4j
public class MainApplication {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Starting application...");
        while(true){
            log.info("Starting generate report application...");
            FileAnalysisController.getInstance().generateReportFromAllFiles();
            log.info("All files done.");
            log.info("Wating 10 seconds to restart...");
            log.info("");
            log.info("");
            Thread.sleep(10000);
        }

    }

}
