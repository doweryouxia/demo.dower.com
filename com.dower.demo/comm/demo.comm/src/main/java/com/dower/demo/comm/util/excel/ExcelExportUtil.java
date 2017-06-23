package com.dower.demo.comm.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 生成Excel表格
 * @author NiuXueJun
 * 2015-8-4 下午5:05:42
 */
public class ExcelExportUtil {
    /**
     * 构造器
     * 
     */
    public ExcelExportUtil() {

    }

    /**
     * 生成具有一定格式excel
     * 
     * @param sheetName
     *            sheet名称,默认为sheet1
     * @param nf
     *            数字类型的格式 如:jxl.write.NumberFormat nf = new
     *            jxl.write.NumberFormat("#.##");默认无格式
     * @param content
     *            二维数组,要生成excel的数据来源
     * @param 合并项
     *            每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并
     * @param os
     *            excel输出流
     * @param row
     *            需要水平居中的行,默认居左。以逗号分隔的字符串
     * @param col
     *            需要水平居中的列,默认居左。以逗号分隔的字符串
     */
    public static void export(String sheetName, NumberFormat nf, String[][] content,
            String[] mergeInfo, OutputStream os, String row, String col) {
        
        if (VerifyUtil.isNullObject(content, os) || VerifyUtil.isNull2DArray(content)) {
            return;
        }
        // 默认名称
        if (VerifyUtil.isNullObject(sheetName)) {
            sheetName = "sheet1";
        }
        Set<Integer> rows = getInfo(row);
        Set<Integer> cols = getInfo(col);
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            for (int i = 0; i < content.length; i++) {
                for (int j = 0; j < content[i].length; j++) {
                    if (content[i][j] == null) {
                        content[i][j] = "";
                    }
                    if (isNumber(content[i][j]) && !rows.contains(i)
                            && !cols.contains(j)) {// 处理数字
                        Number number = null;
                        if (VerifyUtil.isNullObject(nf)) {// 数字无格式
                            number = new Number(j, i,
                                    Double.valueOf(content[i][j]));
                        } else {// 如果有格式,按格式生成
                            jxl.write.WritableCellFormat wcfn = new jxl.write.WritableCellFormat(
                                    nf);
                            number = new Number(j, i,
                                    Double.valueOf(content[i][j]), wcfn);
                        }
                        sheet.addCell(number);
                    } else {// 处理非数字
                        WritableCellFormat format = new WritableCellFormat();
                        if (rows.contains(i) || cols.contains(j)) {
                            format.setAlignment(jxl.format.Alignment.CENTRE);
                        } else {
                            format.setAlignment(jxl.format.Alignment.LEFT);
                        }
                        format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                        Label label = new Label(j, i, content[i][j], format);
                        sheet.addCell(label);
                    }
                }
            }
            merge(sheet, mergeInfo);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            try {
                workbook.close();
                os.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成固定格式的excel,表格都为文本,水平居左,垂直居中
     * 
     * @param sheetName
     *            sheet名称,默认为sheet1
     * @param content
     *            二维数组,要生成excel的数据来源
     * @param os
     *            excel输出流
     */
    public static void exportFormatExcel(String[][] content, String sheetName,
            OutputStream os) {
        if (VerifyUtil.isNullObject(content, os) || VerifyUtil.isNull2DArray(content)) {
            return;
        }
        // 默认名称
        if (VerifyUtil.isNullObject(sheetName)) {
            sheetName = "sheet1";
        }
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);

            for (int i = 0; i < content.length; i++) {
                for (int j = 0; j < content[i].length; j++) {
                    if (content[i][j] == null) {
                        content[i][j] = "";
                    }
                    WritableCellFormat format = new WritableCellFormat();
                    format.setAlignment(jxl.format.Alignment.LEFT);
                    format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                    Label label = new Label(j, i, content[i][j], format);
                    sheet.addCell(label);
                }
            }

            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            try {
                workbook.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成固定格式的excel,表格都为文本,水平居左,垂直居中
     * 
     * @param sheetName
     *            sheet名称,默认为sheet1
     * @param content
     *            Map,要生成excel的数据来源
     * @param os
     *            excel输出流
     */
    public static void exportFormatExcel(Map<String, String[][]> content,
            String[] salary_name_array, String sheetName, OutputStream os)
             {
        if (VerifyUtil.isNullObject(content, os) || content.size() == 0) {
            return;
        }
        // 默认名称
        if (VerifyUtil.isNullObject(sheetName)) {
            sheetName = "sheet1";
        }
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            int index = 0;
            for (int k = 0; k < salary_name_array.length; k++) {
                String[][] value = (String[][]) content
                        .get(salary_name_array[k]);
                if (value != null && value.length > 0) {
                    if (index != 0) {
                        index++;
                    }
                    WritableCellFormat format1 = new WritableCellFormat();
                    format1.setAlignment(jxl.format.Alignment.LEFT);
                    format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                    Label label1 = new Label(0, index, salary_name_array[k],
                            format1);
                    sheet.addCell(label1);
                    for (int i = 0; i < value.length; i++) {
                        index++;
                        for (int j = 0; j < value[i].length; j++) {
                            if (value[i][j] == null) {
                                value[i][j] = "";
                            }
                            WritableCellFormat format = new WritableCellFormat();
                            format.setAlignment(jxl.format.Alignment.LEFT);
                            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

                            Label label = new Label(j, index, value[i][j],
                                    format);
                            sheet.addCell(label);
                        }
                    }
                }
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并表格
     * @param sheet
     *            工作表
     * @param mergeInfo
     *            要合并的表格的信息
     * @throws RowsExceededException
     * @throws NumberFormatException
     * @throws WriteException
     */
    private static void merge(WritableSheet sheet, String[] mergeInfo)
            throws RowsExceededException, NumberFormatException, WriteException {
        if (VerifyUtil.isNullObject(sheet) || VerifyUtil.isNull1DArray(mergeInfo)) {
            return;
        } else if (!isMergeInfo(mergeInfo)) {
            return;
        } else {
            for (String str : mergeInfo) {
                String[] temp = str.split(",");
                sheet.mergeCells(Integer.parseInt(temp[1]),
                        Integer.parseInt(temp[0]), Integer.parseInt(temp[3]),
                        Integer.parseInt(temp[2]));
            }
        }
    }

    /**
     * 处理要居中的行或列的数据
     * 
     * @param indexes
     *            行标或列标
     * @return 行坐标或列坐标组成的集合
     */
    private static Set<Integer> getInfo(String indexes) {
        Set<Integer> set = new HashSet<Integer>();
        if (VerifyUtil.isNullObject(indexes)) {
            return set;
        }
        String[] temp = indexes.split(",", 0);
        for (String str : temp) {
            if (isNumeric(str)) {
                set.add(Integer.parseInt(str));
            }
        }
        return set;
    }

    /**
     * 判断字符串是否由纯数字组成
     * 
     * @param str
     *            源字符串
     * @return true是，false否
     */
    private static boolean isNumeric(String str) {
        if (VerifyUtil.isNullObject(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否是数字
     * 
     * @param str
     *            源字符串
     * @return true是，false否
     */
    private static boolean isNumber(String number) {
        // 判断参数
        if (VerifyUtil.isNullObject(number)) {
            return false;
        }
        // 查看是否有小数点
        int index = number.indexOf(".");
        if (index < 0) {
            return isNumeric(number);
        } else {
            // 如果有多个".",则不是数字
            if (number.indexOf(".") != number.lastIndexOf(".")) {
                return false;
            }
            String num1 = number.substring(0, index);
            String num2 = number.substring(index + 1);
            return isNumeric(num1) && isNumeric(num2);
        }
    }

    /**
     * 判断合并项内容是否合法
     * 
     * @param mergeInfo
     *            合并项 每一项的数据格式为0,1,0,2即把(0,1)和(0,2)合并
     * @return true合法,false非法
     */
    private static boolean isMergeInfo(String[] mergeInfo) {
        if (VerifyUtil.isNull1DArray(mergeInfo)) {
            return false;
        } else {
            for (String str : mergeInfo) {
                String[] temp = str.split(",");
                if (VerifyUtil.isNull1DArray(temp) || temp.length != 4) {
                    return false;
                } else {
                    for (String s : temp) {
                        if (!isNumeric(s)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
