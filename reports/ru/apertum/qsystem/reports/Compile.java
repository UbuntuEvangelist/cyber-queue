/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.apertum.qsystem.reports;

import java.io.File;
import java.util.Scanner;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 *
 * @author Evgeniy Egorov
 */
public class Compile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (1 == 11) {
            String key = " jn 7 =asdf 3 asdf    455";
            final Scanner scan = new Scanner(key).useDelimiter("\\D+");
            while (scan.hasNextInt()) {
                final int ind = scan.nextInt();
                System.out.println(">" + ind + "<");
            }
            return;
        }
        System.out.println("Start...");
        // TODO code application logic here
        //args = new String[]{"E:\\WORK\\apertum-qsystem.PLUGINS\\JRTicketPlugin\\jrt_template\\ticket.jrxml"};
        if (args.length == 0) {
            System.out.println("Need parameter. Specify a file *.jrxml for compile.");
            return;
        }
        System.out.println("Compile file \"" + args[0] + "\"");
        final File f = new File(args[0]);
        if (!f.exists()) {
            System.out.println("File \"" + args[0] + "\" not exists.");
            return;
        }
        final String destFileName = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + ".jasper";
        System.out.println("Destination file \"" + destFileName + "\"");
        try {
            JasperCompileManager.compileReportToFile(args[0], destFileName);
            System.out.println("Compilation was successful.");
        } catch (JRException ex) {
            System.err.println("Compilation failed.");
            System.err.println(ex);
        }

    }

}
