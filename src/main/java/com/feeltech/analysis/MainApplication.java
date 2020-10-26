package com.feeltech.analysis;

import com.feeltech.analysis.util.FileUtil;

import java.io.FileNotFoundException;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

public class MainApplication {

    public static void main(String[] args) throws FileNotFoundException {
        FileUtil.getInstance().processInputFiles();
    }

}
