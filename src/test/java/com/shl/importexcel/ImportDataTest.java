package com.shl.importexcel;

import com.shl.importexcel.utils.ExcelUtile;
import com.soholy.AndroidSupportApplication;
import com.soholy.exception.RspException;
import com.soholy.service.TransfromServer;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/*
truncate table t_dictionary;
truncate table t_dog_owner;
truncate table t_dog_owner_card;
truncate table t_dog_info;
truncate table t_immune_card;
truncate table t_vaccine_injection;

SELECT
    dw.dog_owner_id,dw.dog_owner_name,dw.dog_owner_phone,
    d.dog_id,d.dog_name,
    dc.dog_owner_card_id,dc.card_num,
    ic.seq_id,ic.immune_card_num,
    vi.vaccine_injection_id,vi.vac_org
from t_dog_owner dw
LEFT JOIN t_dog_info d ON dw.dog_owner_id = d.dog_owner_id
LEFT JOIN t_dog_owner_card dc ON dc.dog_owner_id = dw.dog_owner_id
LEFT JOIN t_immune_card ic ON ic.dog_id = d.dog_id AND ic.dog_owner_id = dw.dog_owner_id
LEFT JOIN t_vaccine_injection vi ON vi.dog_id = d.dog_id
WHERE dw.dog_owner_id < 10

 */
@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AndroidSupportApplication.class)
public class ImportDataTest {

    private String outFile = "src/test/resources/excels/out/outFile.csv";
    private String outExcelPath = "src/test/resources/excels/out";

    private String inPath = "src/test/resources/excels/in";

    @Autowired
    private TransfromServer transfromServer;

    @Test
    public void import_7_8() {
        List<String> inPaths = Arrays.asList(inPath);
        File file = new File(inPath);
        if (file.exists() && file.isDirectory()) {
            inPaths = Arrays.stream(file.list()).map(x -> inPath + "/" + x).collect(Collectors.toList());
        }

        String[] err_head = new String[]{"犬主电话", "犬主姓名", "犬主身份证号", "社区", "犬主地址", "犬名", "犬品种", "犬龄（月）", "免疫证号", "疫苗名称", "疫苗生产单位", "免疫医生", "免疫日期", "宠物医院id", "毛色", "性别"};
        inPaths.forEach(filePath -> {
            try {
                Workbook workbook = ExcelUtile.createExcel(filePath);
                Map<String, Map<Integer, Map<Integer, String>>> excelMap = ExcelUtile.raedExcel(workbook, 1, -1, -1, -1, false, true, true);
                LinkedHashMap<Integer, LinkedHashMap<Integer, String>> err_rows = new LinkedHashMap<>();

                excelMap.entrySet().stream()
                        .filter(Objects::nonNull)
                        .forEach(x -> {
                            String key = x.getKey();
                            System.out.println("sheetName: " + key);
                            Map<Integer, Map<Integer, String>> values = x.getValue();
                            values.entrySet().stream()
                                    .filter(Objects::nonNull)
                                    .forEach(y -> {
                                        Integer rowNum = y.getKey();
                                        Map<Integer, String> rowMap = y.getValue();
                                        try {
                                            transfromServer.transfrom(rowMap, false);
                                        } catch (Exception e) {
                                            err_rows.put(rowNum, (LinkedHashMap<Integer, String>) rowMap);
                                            rowMap.put(err_head.length, e.getMessage());
                                            if (!(e instanceof RspException)) e.printStackTrace();
                                            log.warning("一行记录保存失败，将写入文件，行信息:" + y + ", 异常信息：" + e.getMessage());
                                            String outStr = rowMap.entrySet().stream()
                                                    .map(Map.Entry::getValue)
                                                    .reduce("", (z1, z2) -> z1 + "," + z2);
                                            outStr = StringUtils.isNoneBlank(outStr) && StringUtils.startsWith(outStr, ",") ? StringUtils.substring(outStr, 1) : outStr;
                                            outStr = outStr + "," + e.getMessage() + "\n";
                                            try {
                                                FileUtils.writeStringToFile(new File(outFile), outStr, Charset.forName("UTF-8"), true);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                log.warning("文件写入异常" + e.getMessage());
                                            }
                                        }
                                    });
                        });

                if (err_rows.size() > 0) {
                    String err_sheetName = "err_record";
                    HSSFWorkbook expExcel = ExcelUtile.expExcel(err_head, err_rows, err_sheetName);
                    expExcel.write(new File(outExcelPath + "/" + StringUtils.substringAfterLast(filePath, "/")));
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.warning("文件解析失败，异常信息：" + e.getMessage() + " path:" + filePath);
            }
        });

    }


}
