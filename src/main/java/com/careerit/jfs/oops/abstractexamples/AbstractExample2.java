package com.careerit.jfs.oops.abstractexamples;


abstract class GenerateReport{

    public abstract void generateReport();



}

class PDFReport extends GenerateReport{

    public void generateReport(){
        System.out.println("PDF report");
    }

}
class EXCELReport extends GenerateReport{

    public void generateReport(){
        System.out.println("EXCEL report");
    }

}
class CSVReport extends GenerateReport{

    public void generateReport(){
        System.out.println("CSV report");
    }

}

class ImageReport extends GenerateReport{
    public void generateReport(){
        System.out.println("Image report");
    }

}
public class AbstractExample2 {
    public static void main(String[] args) {
        GenerateReport gr = getReport(ReportType.EXCEL);
        gr.generateReport();;
    }

    public static GenerateReport getReport(ReportType rt) {
        return switch (rt) {
            case EXCEL -> new EXCELReport();
            case PDF -> new PDFReport();
            case CSV -> new CSVReport();
            case IMAGE -> new ImageReport();

        };

    }
}