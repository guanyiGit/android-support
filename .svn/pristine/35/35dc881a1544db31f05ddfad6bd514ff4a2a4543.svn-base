package com.shl.importexcel.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtile {

    // 正则表达式 用于匹配属性的第一个字母
    private static final String REGEX = "[a-zA-Z]";

    public static boolean raedExcel(InputStream is) throws Exception {
        Workbook wk = null;
        if (is == null) {
            throw new Exception("InputStream is null");
        }
        wk = WorkbookFactory.create(is);
        for (Iterator<Sheet> it = wk.sheetIterator(); it.hasNext(); ) {
            Sheet sheet = it.next();
            System.err.println("--------------------- [sheetName: " + sheet.getSheetName() + " ]---------------------");
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if (i == 2) {
                    System.out.println(0);
                }
                Row row = sheet.getRow(i);
                for (int j = 0; row != null && j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    System.out.print(cell + "[Type:" + cell.getCellStyle() + ",value:" + getCellValue(cell)
                            + ",CellStyle:" + cell.getCellStyle().getDataFormatString() + "]\t");
                }
                System.out.println();
            }
        }

        return false;
    }

    /**
     * @param is            :Excel表的所在路径
     * @param startRow      ：从第几行开始
     * @param endRow：到第几行结束 (0表示所有行; 正数表示到第几行结束;负数表示到倒数第几行结束)
     * @param clazz         ：要返回的对象集合的类型
     * @return
     * @throws IOException
     * @Description (Excel数据导入到数据库 参数)
     */
    public static <T> List<T> raedExcelToJavaBean(InputStream is, int startRow, int endRow, Class<T> clazz)
            throws Exception {
        if (is == null) {
            throw new Exception("InputStream is null");
        }
        Workbook wb = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
// 去读Excel
            wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
// 获取最后行号
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum > 0) { // 如果>0，表示有数据
                System.out.println("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
            }
            Row row = null;
// 循环读取
            for (int i = startRow; i <= lastRowNum + endRow; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rowList.add(row);
                    System.out.print("第" + (i + 1) + "行：\t");
                    // 获取每一单元格的值
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        String value = getCellValue(row.getCell(j));
                        if (!value.equals("")) {
                            System.out.print(value + " | ");
                        }
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            wb.close();
        }
        return returnObjectList(rowList, clazz);
    }

    /**
     * @param filePath 完整路径
     * @return Map<sheetName callValue>>>
     * @throws Exception
     * @Description (读excel文件)
     */
    public static Map<String, Map<Integer, Map<Integer, String>>> raedExcel(String filePath) throws Exception {
        Workbook wb = createExcel(filePath);
        return raedExcel(wb, -1, -1, -1, -1, false, false, false);
    }

    public static Workbook createExcel(InputStream is) throws Exception {
        if (is == null) {
            throw new Exception("InputStream is null");
        }
        return WorkbookFactory.create(is);
    }

    public static Workbook createExcel(String filePath) throws Exception {
        if (filePath == null) {
            throw new Exception("filePath is null");
        }
        return createExcel(new File(filePath));
    }

    public static Workbook createExcel(File file) throws Exception {
        if (file == null || !file.exists()) {
            throw new Exception("file does not exist");
        }
        return WorkbookFactory.create(file);
    }

    /**
     * @param workbook
     * @param startRowNum         开始行号,负数不限制
     * @param endRowNum           读到最大行号,负数不限制
     * @param startCallNum        开始列号,负数不限制
     * @param endCallNum          读到最大列号,负数不限制
     * @param isExcludeCallEmpty  是否去掉单元格空值 callValue>>>
     * @param isExcludeRowEmpty   是否去掉空列 callRow
     * @param isExcludeSheetEmpty 是否去掉空sheet
     * @Description (读excel文件)
     */
    public static Map<String, Map<Integer, Map<Integer, String>>> raedExcel(Workbook workbook, int startRowNum,
                                                                            int endRowNum, int startCallNum, int endCallNum, boolean isExcludeCallEmpty, boolean isExcludeRowEmpty, boolean isExcludeSheetEmpty) {
        try {
            if (workbook == null) {
                throw new Exception("workbook is not exist");
            }
            Map<String, Map<Integer, Map<Integer, String>>> excelMap = new LinkedHashMap<String, Map<Integer, Map<Integer, String>>>();// excel
            for (Iterator<Sheet> it = workbook.sheetIterator(); it.hasNext(); ) {
                Sheet sheet = it.next();
                if (sheet == null) {
                    continue;
                }
                String sheetName = sheet.getSheetName().trim();// sheet名称

                Map<Integer, Map<Integer, String>> sheetMap = new LinkedHashMap<Integer, Map<Integer, String>>();// sheet

                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }

                    if ((startRowNum > 0 && i < startRowNum) || (endRowNum > 0 && i >= endRowNum)) { // 行号[startRowNum,endRowNum}
                        continue;
                    }

                    Map<Integer, String> callMap = new LinkedHashMap<Integer, String>();// row -call
                    int rowNum = row.getRowNum();

                    for (int j = 0; row != null && j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            if (!isExcludeCallEmpty) {
                                callMap.put(j, null);
                            }
                            continue;
                        }

                        if ((startCallNum > 0 && j < startCallNum) || (endCallNum > 0 && j >= endCallNum)) { // 行号[startCallNum,endcallNum}
                            continue;
                        }

                        String cellValue = getCellValue(cell);
                        if (isExcludeCallEmpty && (cellValue == null || cellValue.trim().length() == 0)) {// 去空
                            continue;
                        }

                        int columnIndex = cell.getColumnIndex();
                        callMap.put(columnIndex, cellValue != null ? cellValue.trim() : cellValue); // callNum callValue
                    }
                    boolean rowFalg = false;
                    Collection<String> row_values = callMap.values();
                    for (String value : row_values) {
                        if (value != null && value.trim().length() > 0) {
                            rowFalg = true;
                            break;
                        }
                    }
                    if (!isExcludeRowEmpty || rowFalg) {
                        sheetMap.put(rowNum, callMap);// rowNum callMap
                    }
                }

                boolean sheetFalg = false;
                Collection<Map<Integer, String>> sheet_Rows = sheetMap.values();
                for (Map<Integer, String> value : sheet_Rows) {
                    if (value != null && !value.isEmpty() && value.size() > 0) {
                        sheetFalg = true;
                        break;
                    }
                }
                if (!isExcludeSheetEmpty || sheetFalg) {
                    excelMap.put(sheetName, sheetMap);// 添加sheet
                }
            }
            return excelMap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    /**
     * @param filePath
     * @return
     * @Description (写excel)
     */
    public static boolean writeExcel(String filePath) {
        Workbook wk = null;
        FileOutputStream fos = null;
        try {
            int lastIndex = filePath.lastIndexOf("/") != -1 ? filePath.lastIndexOf("/")
                    : filePath.lastIndexOf("\\") != -1 ? filePath.lastIndexOf("\\") : -1;
            if (lastIndex == -1) {
                throw new Exception("Path error !");
            }
            if (!new File(filePath.subSequence(0, lastIndex + 1).toString()).isDirectory()) {
                throw new Exception("Path error !");
            }
            if (filePath.endsWith(".xls")) {
                // 2007-13的文档
                wk = new HSSFWorkbook();
            } else if (filePath.endsWith(".xlsx")) {
                // 2007-13之后的文档
                wk = new XSSFWorkbook();
            } else {
                throw new Exception("File suffix name error !");
            }
            fos = new FileOutputStream(filePath);
// 创建工作表
            Sheet sheet = wk.createSheet("工作表demo");
            Row row = sheet.createRow(0);
// 设置行高
            row.setHeightInPoints(25);
            Cell cell = row.createCell(0);
// 设置样式
            CellStyle cs = wk.createCellStyle();
            cs.setAlignment(HorizontalAlignment.CENTER);
            cs.setBorderBottom(BorderStyle.THIN);
            cs.setFillBackgroundColor(FillPatternType.DIAMONDS.getCode());
            cell.setCellValue("单元格");
            wk.write(fos);
            wk.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    /**
     * @param cell
     * @return
     * @Description (获取单元格的值)
     */
    @SuppressWarnings("deprecation")
    private static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = getValueOfNumericCell(cell);
//                    result = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    // 获取数字类型的cell值
    private static Object getValueOfNumericCell(Cell cell) {
        Boolean isDate = DateUtil.isCellDateFormatted(cell);
        Double d = cell.getNumericCellValue();
        Object o = null;
        if (isDate) {
            o = DateFormat.getDateTimeInstance()
                    .format(cell.getDateCellValue());
        } else {
            o = getRealStringValueOfDouble(d);
        }
        return o;
    }

    // 处理科学计数法与普通计数法的字符串显示，尽最大努力保持精度
    private static String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            // 小数部分
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint
                    + BigInteger.ONE.intValue(), indexOfE));
            // 指数
            int pow = Integer.valueOf(doubleStr.substring(indexOfE
                    + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            doubleStr = String.format("%." + scale + "f", d);
        } else {
            Pattern p = Pattern.compile(".0$");
            Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }

    public static Object getJavaValue(XSSFCell cell) {
        if (null != cell) {
            switch (cell.getCellTypeEnum()) {
                case NUMERIC: // 数字
                    DecimalFormat df = new DecimalFormat("0");// 处理科学计数法
                    String val = df.format(cell.getNumericCellValue());
                    return val;
                case STRING: // 字符串
                    return cell.getStringCellValue() + "";
                case BOOLEAN: // Boolean
                    return cell.getBooleanCellValue() + "";
                case FORMULA: // 公式
                    return cell.getCellFormula() + "";
                case BLANK: // 空值
                    return "";
                case ERROR: // 故障
                    return "";
                default:
                    return "未知类型";
            }
        } else {
            return "";
        }
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        // 声明列对象
        HSSFCell cell = null;

        // 创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * @param sheetName      工作簿中的一张工作表的名称
     * @param titleName      表格的标题名称
     * @param headers        表格每一列的列名(循序按照数据顺序)
     * @param beanCollection 要导出的数据源
     * @param pattern        时间类型数据的格式
     * @Description (javabean导出到excel)
     */
    public static Workbook javaBeanWriteWorkbook(String sheetName, String titleName, String[] headers,
                                                 Collection<?> beanCollection, String pattern) {
        try {
            Workbook workbook = new HSSFWorkbook();
            // 生成一个工作表
            Sheet sheet0 = workbook.createSheet(sheetName);
            // 设置工作表默认列宽度为20个字节
            sheet0.setDefaultColumnWidth((short) 18);
            // sheet0.setDefaultRowHeight((short)25);//默认行高25
            sheet0.setDefaultRowHeightInPoints((short) 25);
            // 在工作表中合并首行并居中
            CellRangeAddress titileAddress = new CellRangeAddress(1, 1, 0, headers.length - 1);
            // 边框
            RegionUtil.setBorderBottom(BorderStyle.THIN, titileAddress, sheet0);
            RegionUtil.setBorderLeft(BorderStyle.THIN, titileAddress, sheet0);
            RegionUtil.setBorderRight(BorderStyle.THIN, titileAddress, sheet0);
            RegionUtil.setBorderTop(BorderStyle.THIN, titileAddress, sheet0);
            sheet0.addMergedRegion(titileAddress);

            // 创建[标题]样式
            CellStyle titleStyle = workbook.createCellStyle();
            // 设置[标题]样式 薰衣草颜色
            // titleStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LAVENDER.getIndex());
            // 设置图案样式
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // titleStyle.setBorderBottom(BorderStyle.THICK);
            // titleStyle.setBorderLeft(BorderStyle.THICK);
            // titleStyle.setBorderRight(BorderStyle.THICK);
            // titleStyle.setBorderTop(BorderStyle.THICK);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            // 对齐方式
            titleStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            // 创建[标题]字体
            Font titleFont = workbook.createFont();
            // 设置[标题]字体 黑色
            titleFont.setColor(HSSFColorPredefined.BLACK.getIndex());
            //// 设置字号
            titleFont.setFontHeightInPoints((short) 18);
            // 设置字体是否加粗
            titleFont.setBold(Boolean.TRUE);
            // 把[标题字体]应用到[标题样式]
            titleStyle.setFont(titleFont);

            // 创建[列首]样式
            CellStyle headersStyle = workbook.createCellStyle();
            // 设置[列首]样式
            // 设置背景色
            // headersStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
            headersStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//// 设置图案样式
            headersStyle.setBorderBottom(BorderStyle.THIN);// 边框
            headersStyle.setBorderLeft(BorderStyle.THIN);
            headersStyle.setBorderRight(BorderStyle.THIN);
            headersStyle.setBorderTop(BorderStyle.THIN);
            headersStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            headersStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            // 创建[列首]字体
            Font headersFont = workbook.createFont();
            // 设置[列首]字体
            headersFont.setColor(HSSFColorPredefined.BLACK.getIndex());
            headersFont.setFontHeightInPoints((short) 12);
            headersFont.setBold(Boolean.TRUE);
            // 把[列首字体]应用到[列首样式]
            headersStyle.setFont(headersFont);

            // 创建[表中数据]样式
            CellStyle dataSetStyle = workbook.createCellStyle();
            // 设置[表中数据]样式
            // dataSetStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
            dataSetStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 设置图案样式
            dataSetStyle.setBorderBottom(BorderStyle.THIN);
            dataSetStyle.setBorderLeft(BorderStyle.THIN);
            dataSetStyle.setBorderRight(BorderStyle.THIN);
            dataSetStyle.setBorderTop(BorderStyle.THIN);
            dataSetStyle.setAlignment(HorizontalAlignment.CENTER);
            dataSetStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            dataSetStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            // 创建[表中数据]字体
            Font dataSetFont = workbook.createFont();
            // 设置[表中数据]字体
            // dataSetFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            dataSetFont.setBold(Boolean.FALSE);
            dataSetFont.setColor(HSSFColorPredefined.BLACK.getIndex());
            // 把[表中数据字体]应用到[表中数据样式]
            dataSetStyle.setFont(dataSetFont);

            // 创建标题行-增加样式-赋值
            int indexRowNum = 0;// row号
            sheet0.createRow(indexRowNum++);
            Row titleRow = sheet0.createRow(indexRowNum++);
            Cell titleCell = titleRow.createCell(0);// 标题行只有一个列
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(titleName);
            titleRow.setHeightInPoints((short) 35);
            sheet0.createRow(indexRowNum++);

            // 创建列首-增加样式-赋值  列头
            Row row = sheet0.createRow(3);
            row.setHeightInPoints((short) 25);
            for (short i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headersStyle);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            // 创建表中数据行-增加样式-赋值
            Iterator<?> it = beanCollection.iterator();
            int index = indexRowNum;// 内容起始行号
            while (it.hasNext()) {
                index++;
                row = sheet0.createRow(index);
                row.setHeightInPoints((short) 22);
                Object t = it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(dataSetStyle);
                    Field field = fields[i];
                    String getMethodName = convertToMethodName(field.getName(), t.getClass(), Boolean.FALSE);
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName);

                    Object value = getMethod.invoke(t, new Object[]{});
                    if (value == null) {
                        continue;
                    }
                    // 如果是时间类型,按照格式转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }

                    cell.setCellValue(textValue);
//                    // 利用正则表达式判断textValue是否全部由数字组成
//                    if (textValue != null) {
//                        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
//                        Matcher matcher = p.matcher(textValue);
//                        if (matcher.matches()) {
//                            // 是数字当作double处理
//                            cell.setCellValue(Double.parseDouble(textValue));
//                        } else {
//                            // 不是数字做普通处理
//                            cell.setCellValue(textValue);
//                        }
//                    }

                }
            }
            return workbook;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param sheetName      工作簿中的一张工作表的名称
     * @param titleName      表格的标题名称
     * @param headers        表格每一列的列名(循序按照数据顺序)
     * @param beanCollection 要导出的数据源
     * @param resultUrl      导出的excel文件地址
     * @param pattern        时间类型数据的格式
     * @Description (javabean导出到excel)
     */
    public static void javaBeanWriteExcel(String sheetName, String titleName, String[] headers,
                                          Collection<?> beanCollection, String resultUrl, String pattern) {
        Workbook workbook = null;
        FileOutputStream fos = null;
        try {
            int lastIndex = resultUrl.lastIndexOf("/") != -1 ? resultUrl.lastIndexOf("/")
                    : resultUrl.lastIndexOf("\\") != -1 ? resultUrl.lastIndexOf("\\") : -1;
            if (lastIndex == -1) {
                throw new Exception("Path error !");
            }
            if (!new File(resultUrl.subSequence(0, lastIndex + 1).toString()).isDirectory()) {
                throw new Exception("Path error !");
            }
            if (resultUrl.endsWith(".xls") || resultUrl.endsWith(".xlsx")) {
                // 2007-13的文档
                workbook = new HSSFWorkbook();
            } else if (resultUrl.endsWith(" ")) {
                // TODO
                // 2007-13之后的文档
                workbook = new XSSFWorkbook();
            } else {
                throw new Exception("File suffix name error !");
            }
            fos = new FileOutputStream(resultUrl);
// 生成一个工作表
            Sheet sheet0 = workbook.createSheet(sheetName);
// 设置工作表默认列宽度为20个字节
            sheet0.setDefaultColumnWidth((short) 20);
// 在工作表中合并首行并居中
            sheet0.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

// 创建[标题]样式
            CellStyle titleStyle = workbook.createCellStyle();
// 设置[标题]样式
            titleStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_BLUE.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
// 创建[标题]字体
            Font titleFont = workbook.createFont();
// 设置[标题]字体
            titleFont.setColor(HSSFColorPredefined.WHITE.getIndex());
            titleFont.setFontHeightInPoints((short) 24);
            titleFont.setBold(Boolean.TRUE);// 设置字体是否加粗
// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
// 把[标题字体]应用到[标题样式]
            titleStyle.setFont(titleFont);

// 创建[列首]样式
            CellStyle headersStyle = workbook.createCellStyle();
// 设置[列首]样式
            headersStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_ORANGE.getIndex());
            headersStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headersStyle.setBorderBottom(BorderStyle.THIN);
            headersStyle.setBorderLeft(BorderStyle.THIN);
            headersStyle.setBorderRight(BorderStyle.THIN);
            headersStyle.setBorderTop(BorderStyle.THIN);
            headersStyle.setAlignment(HorizontalAlignment.CENTER);
// 创建[列首]字体
            Font headersFont = workbook.createFont();
// 设置[列首]字体
            headersFont.setColor(HSSFColorPredefined.VIOLET.getIndex());
            headersFont.setFontHeightInPoints((short) 12);
// headersFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headersFont.setBold(Boolean.TRUE);
// 把[列首字体]应用到[列首样式]
            headersStyle.setFont(headersFont);

// 创建[表中数据]样式
            CellStyle dataSetStyle = workbook.createCellStyle();
// 设置[表中数据]样式
            dataSetStyle.setFillForegroundColor(HSSFColorPredefined.GOLD.getIndex());
            dataSetStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            dataSetStyle.setBorderBottom(BorderStyle.THIN);
            dataSetStyle.setBorderLeft(BorderStyle.THIN);
            dataSetStyle.setBorderRight(BorderStyle.THIN);
            dataSetStyle.setBorderTop(BorderStyle.THIN);
            dataSetStyle.setAlignment(HorizontalAlignment.CENTER);
            dataSetStyle.setVerticalAlignment(VerticalAlignment.CENTER);
// 创建[表中数据]字体
            Font dataSetFont = workbook.createFont();
// 设置[表中数据]字体
// dataSetFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            dataSetFont.setBold(Boolean.FALSE);
            dataSetFont.setColor(HSSFColorPredefined.BLUE.getIndex());
// 把[表中数据字体]应用到[表中数据样式]
            dataSetStyle.setFont(dataSetFont);

// 创建标题行-增加样式-赋值
            Row titleRow = sheet0.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(titleName);

// 创建列首-增加样式-赋值
            Row row = sheet0.createRow(1);
            for (short i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headersStyle);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);

            }

// 创建表中数据行-增加样式-赋值
            Iterator<?> it = beanCollection.iterator();
            int index = 1;
            while (it.hasNext()) {
                index++;
                row = sheet0.createRow(index);
                Object t = it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(dataSetStyle);
                    Field field = fields[i];
                    String getMethodName = convertToMethodName(field.getName(), t.getClass(), Boolean.FALSE);
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    if (value == null) {
                        continue;
                    }
                    // 如果是时间类型,按照格式转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
// 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }

                    // 利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            // 不是数字做普通处理
                            cell.setCellValue(textValue);
                        }
                    }

                    OutputStream out = null;
                    out = new FileOutputStream(resultUrl);
                    workbook.write(out);
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * @param originUrl :Excel表的所在路径
     * @param startRow  ：从第几行开始
     * @param endRow    ：到第几行结束 (0表示所有行; 正数表示到第几行结束;负数表示到倒数第几行结束)
     * @param clazz     ：要返回的对象集合的类型
     * @return
     * @throws IOException
     * @Description (Excel数据导入到数据库 参数)
     */
    public static <E> List<E> raedExcelToJavaBean(String originUrl, int startRow, int endRow, Class<E> clazz)
            throws IOException {
        // 判断文件是否存在
        File file = new File(originUrl);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }
        HSSFWorkbook wb = null;
        FileInputStream fis = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
            fis = new FileInputStream(file);
// 去读Excel
            wb = new HSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);
// 获取最后行号
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum > 0) { // 如果>0，表示有数据
                System.out.println("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
            }
            Row row = null;
// 循环读取
            for (int i = startRow; i <= lastRowNum + endRow; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rowList.add(row);
                    System.out.print("第" + (i + 1) + "行：");
                    // 获取每一单元格的值
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        String value = getCellValue(row.getCell(j));
                        if (!value.equals("")) {
                            System.out.print(value + " | ");
                        }
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            wb.close();
        }
        return returnObjectList(rowList, clazz);
    }

    /**
     * 功能:返回指定的对象集合
     */
    private static <E> List<E> returnObjectList(List<Row> rowList, Class<E> clazz) {
        List<E> objectList = null;
        E obj = null;
        String attribute = null;
        String value = null;
        int j = 0;
        try {
            objectList = new ArrayList<E>();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Row row : rowList) {
                j = 0;
                obj = clazz.newInstance();
                for (Field field : declaredFields) {
                    attribute = field.getName().toString();
                    value = getCellValue(row.getCell(j));
                    setAttrributeValue(obj, attribute, value);
                    j++;
                }
                objectList.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectList;
    }

    /**
     * @param obj       对象
     * @param attribute 属性名称
     * @param value     属性值
     * @Description (功能 : 给指定对象的指定属性赋值)
     */
    private static void setAttrributeValue(Object obj, String attribute, String value) {
        if (value == null || value.equals("")) {
            return;
        }
        // 得到该属性的set方法名
        String method_name = convertToMethodName(attribute, obj.getClass(), true);
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
/**
 * 因为这里只是调用bean中属性的set方法，属性名称不能重复 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法 （注：在java中，锁定一个方法的条件是方法名及参数）
 */
            if (method.getName().equals(method_name)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                try {
                    /**
                     * 如果是(整型,浮点型,布尔型,字节型,时间类型), 按照各自的规则把value值转换成各自的类型 否则一律按类型强制转换(比如:String类型)
                     */
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (parameterTypes[0] == int.class || parameterTypes[i] == Integer.class) {
                            value = value.substring(0, value.lastIndexOf("."));
                            method.invoke(obj, Integer.valueOf(value));
                        } else if (parameterTypes[i] == float.class || parameterTypes[i] == Float.class) {
                            method.invoke(obj, Float.valueOf(value));
                        } else if (parameterTypes[i] == double.class || parameterTypes[i] == Double.class) {
                            method.invoke(obj, Double.valueOf(value));
                        } else if (parameterTypes[i] == byte.class || parameterTypes[i] == Byte.class) {
                            method.invoke(obj, Byte.valueOf(value));
                        } else if (parameterTypes[i] == boolean.class || parameterTypes[i] == Boolean.class) {
                            method.invoke(obj, Boolean.valueOf(value));
                            break;
                        } else if (parameterTypes[i] == Date.class) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = null;
                            try {
                                date = sdf.parse(value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            method.invoke(obj, date);
                        } else {
                            method.invoke(obj, parameterTypes[0].cast(value));
                        }
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param fieldName :字段名称
     * @param objClass
     * @param isSet     :ture[setMethod]/false[getmehtod]
     * @Description (根据属性生成对应的set / get方法)
     * @return:mehtodName
     */
    private static String convertToMethodName(String fieldName, Class<?> objClass, boolean isSet) {
        /** 通过正则表达式来匹配第一个字符 **/
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(fieldName);
        StringBuilder sb = new StringBuilder();
        /** 如果是set方法名称 **/
        if (isSet) {
            sb.append("set");
        } else {
            /** get方法名称 **/
            try {
                Field attributeField = objClass.getDeclaredField(fieldName);
                /** 如果类型为boolean **/
                if (attributeField.getType() == boolean.class || attributeField.getType() == Boolean.class) {
                    sb.append("is");
                } else {
                    sb.append("get");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        /** 针对以下划线开头的属性 **/
        if (fieldName.charAt(0) != '_' && m.find()) {
            sb.append(m.replaceFirst(m.group().toUpperCase()));// 字段名首字母变大写
        } else {
            sb.append(fieldName);
        }
        return sb.toString();
    }

    /**
     * 创建excel并填入数据
     *
     * @param head 数据头
     * @param body 主体数据
     * @return HSSFWorkbook
     * @author LiQuanhui
     * @date 2017年11月24日 下午5:25:13
     */
    public static HSSFWorkbook expExcel(String[] head, LinkedHashMap<Integer, LinkedHashMap<Integer, String>> body, String sheetName) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);

        int startRowNum = 0;
        int startCellNum = 0;

        HSSFRow row = sheet.createRow(startRowNum++);
        HSSFCell cell = null;

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        setBorderStyle(cellStyle, BorderStyle.THIN);
        cellStyle.setFont(setFontStyle(workbook, "黑体", (short) 14));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);


        for (int i = startCellNum; i < head.length + startCellNum; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(cellStyle);
        }

        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        setBorderStyle(cellStyle2, BorderStyle.THIN);
        cellStyle2.setFont(setFontStyle(workbook, "宋体", (short) 12));
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);

        for (Iterator<Map.Entry<Integer, LinkedHashMap<Integer, String>>> rowIt = body.entrySet().iterator(); rowIt.hasNext(); ) {
            Map.Entry<Integer, LinkedHashMap<Integer, String>> row_map = rowIt.next();
            if (row_map != null && row_map.getValue() != null) {
                row = sheet.createRow(startRowNum++);
                Map<Integer, String> _colls = row_map.getValue();
                int _startCellNum = startCellNum;
                for (Iterator<Map.Entry<Integer, String>> collIt = _colls.entrySet().iterator(); collIt.hasNext(); ) {
                    Map.Entry<Integer, String> cell_map = collIt.next();
                    cell = row.createCell(_startCellNum++);
                    cell.setCellValue(cell_map.getValue());
                    cell.setCellStyle(cellStyle2);
                }
            }
        }

        for (int i = 0, isize = head.length; i < isize; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }

    /**
     * 设置单元格样式
     *
     * @param border border样式
     * @author LiQuanhui
     * @date 2017年11月24日 下午3:26:24
     */
    private static void setBorderStyle(HSSFCellStyle cellStyle, BorderStyle border) {
        cellStyle.setBorderBottom(border); // 下边框
        cellStyle.setBorderLeft(border);// 左边框
        cellStyle.setBorderTop(border);// 上边框
        cellStyle.setBorderRight(border);// 右边框
    }

    /**
     * 设置字体样式
     *
     * @param workbook 工作簿
     * @param name     字体类型
     * @param height   字体大小
     * @return HSSFFont
     * @author LiQuanhui
     * @date 2017年11月24日 下午3:27:03
     */
    private static HSSFFont setFontStyle(HSSFWorkbook workbook, String name, short height) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(height);
        font.setFontName(name);
        return font;
    }
}
