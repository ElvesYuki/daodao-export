package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.ContractProduct;
import cn.xmo.domain.cargo.ContractProductExample;
import cn.xmo.vo.ContractProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 14:33 2019/8/5
 * @Modified By:
 */
public interface ContractProductService {
    /**
     * 分页查询
     */
    PageInfo findAll(int page, int size, ContractProductExample example);

    /**
     * 保存
     */
    void save(ContractProduct contractProduct);

    /**
     * 更新
     */
    void update(ContractProduct contractProduct);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    ContractProduct findById(String id);


    /**
     * 批量保存
     * @param list
     */
    void saveAll(List<ContractProduct> list);

    /**
     * 根据船期查询出货表数据
     * @param inputDate
     * @return
     */
    List<ContractProductVo> findContractProductByShipTime(String inputDate);

}
