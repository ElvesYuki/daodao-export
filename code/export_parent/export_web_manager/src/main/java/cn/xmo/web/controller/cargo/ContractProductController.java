package cn.xmo.web.controller.cargo;

import cn.xmo.domain.cargo.ContractProduct;
import cn.xmo.domain.cargo.ContractProductExample;
import cn.xmo.domain.cargo.FactoryExample;
import cn.xmo.service.cargo.ContractProductService;
import cn.xmo.service.cargo.FactoryService;
import cn.xmo.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 14:31 2019/8/5
 * @Modified By:
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(String  contractId,
                       @RequestParam(defaultValue = "1")int page,
                       @RequestParam(defaultValue = "5")int size) {
        //(1)构造货物列表
        //1.创建example
        ContractProductExample example = new ContractProductExample();
        //2.创建criteria
        ContractProductExample.Criteria criteria = example.createCriteria();
        //3.添加查询条件
        criteria.andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(page, size, example);
        request.setAttribute("page",pageInfo);

        //(2) 查询所有的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);
        //(3) 将购销合同id存入request
        request.setAttribute("contractId",contractId);
        return "/cargo/product/product-list";
    }

    @RequestMapping(value="/toUpdate")
    public String toUpdate(String id) {
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProduct);

        //查询所有的厂家列表
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);
        return "/cargo/product/product-update";
    }


    @RequestMapping(value="/edit")
    public String edit(ContractProduct contractProduct) {
        contractProduct.setCompanyId(getLoginCompanyId());
        contractProduct.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(contractProduct.getId())) {
            contractProductService.save(contractProduct);
        }else{
            contractProductService.update(contractProduct);
        }
        //页面跳转
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();
    }

    @RequestMapping(value="/delete")
    public String delete(String id, String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }

    /**
     * 进入到货物上传的页面
     *      参数 : 合同的id
     */
    @RequestMapping("toImport")
    public String toImport(String contractId){
        request.setAttribute("contractId",contractId);
        return "cargo/product/product-import";
    }

    /**
     * 进入到货物上传的页面
     *      参数1 : 合同的id
     *      参数2 : MultipartFile , excel文件
     *
     *  MultipartFile :
     *      getBytes  : 获取文件的byte数组
     *      getOriginalFilename : 获取文件的名称
     *      getInputStream : 文件的输入流
     */
    @RequestMapping("/import")
    public String excelImport(String contractId, MultipartFile file) throws Exception {
        List<ContractProduct> list = new ArrayList<>();
        /*
         * 解析excel : 得到一个货物的集合
         *  1.根据excel获取一个工作簿
         *  2.获取第一页
         *  3.循环获取每一行
         *  4.循环获取每一个单元格
         *  5.配置一个解析cell获取内容的方法
         */
        //1.根据excel获取一个工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        //2.获取第一页
        Sheet sheet = wb.getSheetAt(0);
        //3.循环获取每一行
        for(int i=1; i<= sheet.getLastRowNum() ; i++) {
            Row row = sheet.getRow(i);
            //在循环每一个单元格的时候,将所有内容保存到一个object数组中
            Object [] objs = new Object[10];
            for(int j=1 ; j< row.getLastCellNum() ; j ++) {
                //4.循环获取每一个单元格
                Cell cell = row.getCell(j);
                objs[j] = getCellValue(cell);
            }
            ContractProduct cp = new ContractProduct(objs,getLoginCompanyId(),getLoginCompanyName());
            //合同id
            cp.setContractId(contractId);
            list.add(cp);
        }

        //调用service批量保存到数据库中
        contractProductService.saveAll(list);
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }

    /**
     * 处理单元格获取的数据
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell){
        //1.获取单元格的数据类型
        //2.根据不同单元格的类型调用不同的方法获取数据
        CellType cellType = cell.getCellType();
        Object obj = null;
        switch (cellType) {
            case STRING:{
                //字符串类型
                obj = cell.getStringCellValue();
                break;
            }
            case NUMERIC:{
                //数字类型: 数字和日期
                if(DateUtil.isCellDateFormatted(cell)) {
                    //是否为日期
                    obj = cell.getDateCellValue();
                }else{
                    obj = cell.getNumericCellValue();
                }
                break;
            }
            case BOOLEAN:{
                //boolean
                obj = cell.getBooleanCellValue();
                break;
            }
            default:{
                break;
            }
        }
        return obj;
    }
}
