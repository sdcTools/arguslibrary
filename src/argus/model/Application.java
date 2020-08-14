/*
 * Argus Open Source
 * Software to apply Statistical Disclosure Control techniques
 *
 * Copyright 2014 Statistics Netherlands
 *
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the European Union Public Licence 
 * (EUPL) version 1.1, as published by the European Commission.
 *
 * You can find the text of the EUPL v1.1 on
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * This software is distributed on an "AS IS" basis without 
 * warranties or conditions of any kind, either express or implied.
 */
package argus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
//import tauargus.extern.dataengine.TauArgus;
//import tauargus.extern.tauhitas.HiTaSCtrl;
//import tauargus.extern.rounder.Rounder;
//import tauargus.gui.FrameInfo;
//import tauargus.gui.FrameMain;
//import tauargus.service.TableService;
import argus.utils.SystemUtils;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    // Version info 
    public static final int MAJOR = 4;
    public static final int MINOR = 0;
    public static final String REVISION = "3";
    public static final int BUILD = 1;
    
    // Error codes returned by functions in TauArgusJava dll
    public static final int ERR_CODENOTINCODELIST = 1017;
    public static final int ERR_CELLALREADYFILLED = 1022;
    public static final int ERR_TABLENOTADDITIVE = 1024;
    public static final int ERR_CODEDOESNOTEXIST = 1027;
    
    public static final int BATCH_FROMMENU = 1;
    public static final int BATCH_COMMANDLINE = 2;
    public static final int BATCH_NOBATCH = 0;

    public static final int SOLVER_NO = 0;  // not used any more
    public static final int SOLVER_XPRESS = 1;
    public static final int SOLVER_CPLEX = 2;
    public static final int SOLVER_SOPLEX = 3;

    // for interfacing with C++ dll
    static {
        System.loadLibrary("libtauhitas");   
        System.loadLibrary("XXRoundCom");
        System.loadLibrary("TauArgusJava");
 }
//    private static TauArgus tauArgus = new TauArgus();
//    private static HiTaSCtrl tauHitas = new HiTaSCtrl();
//    private static Rounder rounder = new Rounder();
// Anco 1.6    
//    private static ArrayList<Variable> variables = new ArrayList<>();
//    private static ArrayList<Metadata> metadatas = new ArrayList<>();
    private static ArrayList<Variable> variables = new ArrayList<Variable>();
    private static ArrayList<Metadata> metadatas = new ArrayList<Metadata>();
    private static boolean anco = false;
    private static int batch;
    private static boolean protectCoverTable = false;
    public static int solverSelected = SystemUtils.getRegInteger("optimal", "solverused", SOLVER_NO);
    public static int generalMaxHitasTime = SystemUtils.getRegInteger("optimal", "maxhitastime", 1);
    public static boolean SaveDebugHiTaS = false;
//    public static FrameInfo windowInfo; 
    public static boolean windowInfoIsOpen = false;

    private static Variable freqVar = new Variable(null);
    static {
        freqVar.name = "<freq>";
        freqVar.type = Type.FREQUENCY;
        freqVar.varLen = 4;
        freqVar.bPos = 1;
    }
    
    // On Windows java.io.tmpdir gives the value of the TMP, TEMP, USERPROFILE 
    // or windir environment variable. 
    private static String tempDir;
    static {
        setTempDir(System.getProperty("java.io.tmpdir"));
    }

    
//    public static String getFullVersion() {
//        return "" + MAJOR + "." + MINOR + "." + REVISION;
//    }
    
//    public static String getSolverName( int solver){
//        switch (solver) {
//          case SOLVER_NO:  return "no solver";
//          case SOLVER_XPRESS:  return "Xpress";
//          case SOLVER_CPLEX :  return "CPlex"; 
//          case SOLVER_SOPLEX:  return "SCIP";
//          default: return "unknown";    
//        }                
//    }
    
//    public static TauArgus getTauArgusDll() {
//        return tauArgus;
//    }
//
//    public static HiTaSCtrl getTauHitasDll() {
//        return tauHitas;
//    }
//    
//    public static Rounder getRounder() {
//        return rounder;
//    }

    public static List<Metadata> getMetadatas() {
        return metadatas;
    }
    
    public static void addVariable(Variable variable) {
        variable.index = variables.size();
        variables.add(variable);
    }
    
    public static void clearEverythingGui(){
    //    TableService.clearTables();
        clearMetadatas();
        clearVariables();
    }
  
    private static void clearVariables() {
        variables.clear();
    }

    public static int numberOfVariables() {
        return variables.size();
    }
    
    public static Variable getVariable(int index) {
        return variables.get(index);
    }
    
    public static int indexOfVariable(Variable variable) {
        return variable == null ? -1 : variable.index;
    }
    
    public static void setVariables() {
        clearVariables();
        for (Metadata metadata : metadatas) {
            for (Variable variable : metadata.variables) {
                addVariable(variable);
                if (metadata.dataOrigin == Metadata.DATA_ORIGIN_MICRO && metadata.dataFileType == Metadata.DATA_FILE_TYPE_FREE) {
                    variable.bPos = variable.index + 1;
                }
            }
        }
    }
    
//    public static void openInfoWindow(boolean openen){
//        if (openen) {
//         windowInfo = new FrameInfo();    
//         windowInfo.setVisible(true);
//         windowInfoIsOpen = true; 
//        } else { 
//         windowInfo.setVisible(false);
//         windowInfoIsOpen = false; 
//        
//       }
//    }

    public static int numberOfMetadatas() {
        return metadatas.size();
    }
    
    public static Metadata getMetadata(int index) {
        return metadatas.get(index);
    }
    
    public static void addMetadata(Metadata metadata) {
        metadata.index = metadatas.size();
        metadatas.add(metadata);
    }

    public static Metadata removeMetadata(int index) {
        Metadata metadata = metadatas.get(index);
        metadatas.remove(index);
        for (int i = index; i < metadatas.size(); i++) {
            metadatas.get(i).index--;
        }
        return metadata;
    }

    public static void replaceMetadata(Metadata oldMetadata, Metadata metadata) {
        metadatas.set(oldMetadata.index, metadata);
        metadata.index = oldMetadata.index;
    }

    public static void clearMetadatas() {
        metadatas.clear();
    }
    
    public static boolean isBatch() {
        return batch != BATCH_NOBATCH;
    }

    public static int batchType() {
        return batch;
    }

    public static void setBatch(int b) {
        batch = b;
    }
//
//    public static boolean isAnco() {
//        return anco;
//    }
//
//    public static void setAnco(boolean anco) {
//        Application.anco = anco;
//        Preferences p = Preferences.userRoot().node("tauargus/general");
////        Preferences p = Preferences.userNodeForPackage(Application.class);
//        p.putBoolean("anco", anco);
////        Application.anco = p.getBoolean("anco", false);
//    }
//    
//    public static void getAnco() {
//        Boolean b = false;
//        Preferences p = Preferences.userRoot().node("tauargus/general");
//        Application.anco = p.getBoolean("anco", b);
////        Application.anco = b;
//    }
//
//    public static boolean isProtectCoverTable() {
//        return protectCoverTable;
//    }
//
//    public static void setProtectCoverTable(boolean protectCoverTable) {
//        Application.protectCoverTable = protectCoverTable;
//    }
//
//    public static Variable getFreqVar() {
//        return freqVar;
//    }
//
    public static String getTempDir() {
        return tempDir;
    }

    public static void setTempDir(String tempDir) {
        Application.tempDir = FilenameUtils.normalizeNoEndSeparator(tempDir);
    }
    
    public static String getTempFile(String fileName) {
        return FilenameUtils.concat(tempDir, fileName);
    }
    
//    private static void sleepThread(int milliSecs) {
//        try {
//            Thread.sleep(milliSecs);
//        }
//        catch (InterruptedException ex) {
//            // Do something, if there is a exception
//            System.out.println(ex.toString());
//        }
//    }
//
//    public static void showBuildInfoInSplashScreen() {
//       final SplashScreen splash = SplashScreen.getSplashScreen();
//       if (splash == null) {
//           System.out.println("SplashScreen.getSplashScreen() returned null");
//       } else {
//           Graphics2D g = splash.createGraphics();
//           if (g == null) {
//               System.out.println("g is null");
//           } else {
//               g.setPaintMode();
//               g.setColor(new Color(0, 0, 200));
//               Font font = g.getFont().deriveFont(Font.BOLD, 16.0f);
//               g.setFont(font);
////             Even geen gevogel aan het splash screen               
////               g.drawString("Version " + Application.getFullVersion() + " (Build " + Application.BUILD + ")", 160, 105 /*230*/);        
////               splash.update();
//               // Sleep for 1/2 second, so people can see it
//               sleepThread(500); 
//           }
//       }
//    }
//
//    /**
//    * @param args the command line arguments
//    */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                    
//                }
//            }
//// Anco 1.6            
////        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//        } catch (ClassNotFoundException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);}
//          catch (InstantiationException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);}
//          catch (IllegalAccessException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);}
//          catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        
//        //Locale.setDefault(Locale.US);
//        
//        batch = BATCH_NOBATCH;
//        getAnco();
//        SystemUtils.setLogbook(SystemUtils.getRegString("general", "logbook", getTempFile("logbook.txt")));
//        SystemUtils.writeLogbook(" ");
//        SystemUtils.writeLogbook("Start of TauArgus run");
//        SystemUtils.writeLogbook("Version "+Application.getFullVersion()+" build "+Application.BUILD);
//        SystemUtils.writeLogbook("--------------------------");
//        generalMaxHitasTime = SystemUtils.getRegInteger("optimal", "maxhitastime", 10);
//        anco = SystemUtils.getRegBoolean("general", "anco", false);
//        if (args.length > 0 && !args[0].equalsIgnoreCase("X")) {
//            //an "X" temporarily disables the command-line parameters set in the IDE
//            // Batch processing...
//            setBatch(BATCH_COMMANDLINE);
//                        //TODO Declare global
//            //the "/v" parameter is only used if the batch is called for linked tables
//            //tau will then show some progress info
//            for (int i=0;i<args.length;i++){
//              if(args[i].equals("/v")){setBatch(BATCH_FROMMENU);}  
//            }
//            if (args.length > 1) {
//              if(!args[1].equals("/v")){SystemUtils.setLogbook(args[1]);}
//            }
//
//            if (args.length > 2) {
//                if(!args[2].equals("/v")){setTempDir(args[2]);}
//            }
//
//            boolean interActive = true;
//            int exitCode = 0;
//            
//            // Do a lot of stuff...
//            exitCode = tauargus.model.batch.runBatchProcess(args[0]);
//            interActive = (exitCode == 1); 
//            if (!interActive) {
//                SystemUtils.writeLogbook("End of TauArgus run");
//                System.exit(exitCode);
//            }
//        }
//        else {
//        // Interactive mode...
//        showBuildInfoInSplashScreen();
//        }
//        
//        // Showing the GUI will remove the splashscreen (see file manifest.mf)        
//        /* Create and display the form */
//        Application.setBatch(BATCH_NOBATCH);
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//              new FrameMain().setVisible(true);
////              SystemUtils.writeLogbook("End of TauArgus run");
//            }
//        });
//    }
}
