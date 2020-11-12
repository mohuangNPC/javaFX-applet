package com;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/10/17 10:34
 */
public class wordOperation {
    private static String tarPath = "";
    private static String fileName = "";
    private static String template = "";
    public static void genWord2003ByTemplet(Map<String, String> data, int count) throws IOException {
        FileInputStream in = new FileInputStream(template);//载入文档
        XWPFDocument doc = new XWPFDocument(in);
        // 替换段落中的指定文字
        Iterator<XWPFParagraph> itPara = doc.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
            List<XWPFRun> runs = paragraph.getRuns();
            String beforeOneparaString = "";
            for (int i = 0; runs != null && i < runs.size(); i++) {
//                System.err.println(runs.get(i).getText(runs.get(i).getTextPosition()));
            }
        }
        // 替换表格中的指定文字
        Iterator<XWPFTable> itTable = doc.getTablesIterator();
        while (itTable.hasNext()) {
            XWPFTable table = (XWPFTable) itTable.next();
            int rcount = table.getNumberOfRows();
            for (int i = 0; i < rcount; i++) {
                XWPFTableRow row = table.getRow(i);
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    //表格中处理段落（回车）
                    List<XWPFParagraph> cellParList = cell.getParagraphs();
                    for (int p = 0; cellParList != null && p < cellParList.size(); p++) { //每个格子循环
                        List<XWPFRun> runs = cellParList.get(p).getRuns(); //每个格子的内容都要单独处理
                        String beforeOneparaString = "";
                        for (int q = 0; runs != null && q < runs.size(); q++) {
                            String value = runs.get(q).getText(runs.get(q).getTextPosition());
                            if (value != null && value.contains("$")) {
                                String valueRes = data.get(value.replace("$", ""));
                                runs.get(q).setText(valueRes, 0);
                            }
                        }
                    }
                }
            }
        }
        OutputStream os = new FileOutputStream(tarPath+"/"+fileName+".docx");
        doc.write(os);
        os.close();
        in.close();
    }
    public static void main(String[] args) throws IOException, InvalidFormatException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Map map = new HashMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println("模板文件路径");
        template = scanner.nextLine();
        System.out.println("人员名单路径");
        String user = scanner.nextLine();
        System.out.println("生成文件路径");
        tarPath = scanner.nextLine();
        File excelFile = new File(user); //创建文件对象
        FileInputStream is = new FileInputStream(excelFile); //文件流
        Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
        int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
        for (int s = 0; s < sheetCount; s++) {
            Sheet sheet = workbook.getSheetAt(s);
            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
            for (int r = 0; r < rowCount; r++) {
                Row row = sheet.getRow(r);
                int cellCount = row.getPhysicalNumberOfCells(); //获取总列数
                for (int c = 0; c < cellCount; c++) {
                    Cell cell = row.getCell(c);
                    int cellType = cell.getCellType();
                    String cellValue = null;
                    //在读取单元格内容前,设置所有单元格中内容都是字符串类型
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    //按照字符串类型读取单元格内数据
                    cellValue = cell.getStringCellValue();
                    if(c == 0){
                        fileName = cellValue;
                        map.put("name",cellValue);
                    }else if(c == 1){
                        map.put("keys",cellValue);
                    }else if(c == 2){
                        map.put("keyNum",cellValue);
                    }else if(c == 3){
                        map.put("num",cellValue);
                    }
                }
                genWord2003ByTemplet(map,r);
            }
        }
    }

    public static void write(){

    }
}
