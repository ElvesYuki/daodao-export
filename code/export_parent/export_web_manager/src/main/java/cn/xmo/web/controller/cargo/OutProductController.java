package cn.xmo.web.controller.cargo;

import cn.xmo.com.utils.DownloadUtil;
import cn.xmo.service.cargo.ContractProductService;
import cn.xmo.vo.ContractProductVo;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 17:48 2019/8/5
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/contract")
public class OutProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @RequestMapping("/print")
    public String print() {
        return "cargo/print/contract-print";
    }

    /**
     * excel报表的打印:
     *      1.获取数据
     *      2.poi报表生成
     * void : 不需要返回值,获取数据,生成excel,下载excel
     */
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws IOException {
        //i.获取数据
        List<ContractProductVo> list = contractProductService.findContractProductByShipTime(inputDate);
        //ii.poi报表生成
        //1.创建工作簿
        //Workbook wb = new XSSFWorkbook();
        //处理百万数据报表打印
        Workbook wb = new SXSSFWorkbook();
        //2.创建sheet
        Sheet sheet = wb.createSheet();

        //列宽
        sheet.setColumnWidth(1,26 * 256); //列索引,宽度
        sheet.setColumnWidth(2,12 * 256); //列索引,宽度
        sheet.setColumnWidth(3,30 * 256); //列索引,宽度
        sheet.setColumnWidth(4,12 * 256); //列索引,宽度
        sheet.setColumnWidth(5,15 * 256); //列索引,宽度
        sheet.setColumnWidth(6,10 * 256); //列索引,宽度
        sheet.setColumnWidth(7,10 *  256); //列索引,宽度
        sheet.setColumnWidth(8,8 * 256); //列索引,宽度

        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));

        //3.设置公共的参数
        int rowIndex = 0;
        Row row = null;
        Cell cell = null;
        //4.设置大标题
        row = sheet.createRow(rowIndex++);
        row.setHeightInPoints(36);
        cell = row.createCell(1);
        cell.setCellStyle(bigTitle(wb));
        //2012-08  --> 2012-8  -->2012年8
        cell.setCellValue(inputDate.replaceAll("-0","-").replaceAll("-","年")+"月份出货表");
        //5.设置小标题
        String[] titles = new String[]{"", "客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        row = sheet.createRow(rowIndex++);
        row.setHeightInPoints(26);
        for (int i=1;i<titles.length;i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(title(wb));
        }
        //6.循环构造数据单元格
        for (ContractProductVo vo : list) {
            for (int i=0;i<5000;i++) {
                row = sheet.createRow(rowIndex++);
                row.setHeightInPoints(24);
                //客户
                cell = row.createCell(1);
                //cell.setCellStyle(text(wb));
                cell.setCellValue(vo.getCustomName());
                //订单号
                cell = row.createCell(2);
                //cell.setCellStyle(text(wb));
                cell.setCellValue(vo.getContractNo());
                //货号
                cell = row.createCell(3);
                //cell.setCellStyle(text(wb));
                cell.setCellValue(vo.getProductNo());
                //数量
                cell = row.createCell(4);
                //cell.setCellStyle(text(wb));
                cell.setCellValue(vo.getCnumber());
                //工厂
                cell = row.createCell(5);
                //cell.setCellStyle(text(wb));
                cell.setCellValue(vo.getFactoryName());
                //工厂交期
                cell = row.createCell(6);
                //cell.setCellStyle(text(wb));
                if(vo.getDeliveryPeriod() != null) {
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
                }

                //船期
                cell = row.createCell(7);
                //cell.setCellStyle(text(wb));
                if(vo.getShipTime() != null) {
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
                }
                //贸易条款
                cell = row.createCell(8);
                //cell.setCellStyle(text(wb));
                cell.setCellValue(vo.getTradeTerms());
            }
        }
        //iii.下载 ,今日资料中有一个下载的工具类
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        new DownloadUtil().download(bos,response,"出货表.xlsx");
    }

    /**
     * 模板打印
     */
    @RequestMapping("/printExcelTemplate")
    public void printExcelTemplate(String inputDate) throws IOException {
        //i.获取数据
        List<ContractProductVo> list =contractProductService.findContractProductByShipTime(inputDate);
        //ii.poi报表生成
        //1.根据模板加载workbook工作簿
        //找到系统中的模板的路径
        String path = session.getServletContext().getRealPath("/")+"/make/xlsprint/tOUTPRODUCT.xlsx";
        Workbook wb = new XSSFWorkbook(path);

        //2.获取第一页
        Sheet sheet = wb.getSheetAt(0);
        //3.设置大标题
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        //2012-08  --> 2012-8  -->2012年8
        cell.setCellValue(inputDate.replaceAll("-0","-").replaceAll("-","年")+"月份出货表");
        //4.设置小标题

        //5.循环设置数据的单元格
        //5.1 获取模板中数据的样式
        CellStyle css [] = new CellStyle[9];
        row = sheet.getRow(2);
        for(int i=1;i<=8;i++) {
            //获取每一个单元格
            cell = row.getCell(i);
            //获取每一个单元格的样式
            css[i] = cell.getCellStyle();
        }
        //5.2 设置所有的数据
        int i = 2;
        for (ContractProductVo vo : list) {
            for(int j=0;j<5000;j++) {
                row = sheet.createRow(i);

                //客户
                cell = row.createCell(1);
                cell.setCellStyle(css[1]);
                cell.setCellValue(vo.getCustomName());
                //订单号
                cell = row.createCell(2);
                cell.setCellStyle(css[2]);
                cell.setCellValue(vo.getContractNo());
                //货号
                cell = row.createCell(3);
                cell.setCellStyle(css[3]);
                cell.setCellValue(vo.getProductNo());
                //数量
                cell = row.createCell(4);
                cell.setCellStyle(css[4]);
                cell.setCellValue(vo.getCnumber());
                //工厂
                cell = row.createCell(5);
                cell.setCellStyle(css[5]);
                cell.setCellValue(vo.getFactoryName());
                //工厂交期
                cell = row.createCell(6);
                cell.setCellStyle(css[6]);
                if (vo.getDeliveryPeriod() != null) {
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
                }
                //船期
                cell = row.createCell(7);
                cell.setCellStyle(css[7]);
                if (vo.getShipTime() != null) {
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
                }
                //贸易条款
                cell = row.createCell(8);
                cell.setCellStyle(css[8]);
                cell.setCellValue(vo.getTradeTerms());

                i++;
            }
        }

        //iii.下载 ,今日资料中有一个下载的工具类
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        new DownloadUtil().download(bos,response,"出货表.xlsx");
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        //字体加粗
        font.setBold(true);
        style.setFont(font);
        //横向居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //纵向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }

}
