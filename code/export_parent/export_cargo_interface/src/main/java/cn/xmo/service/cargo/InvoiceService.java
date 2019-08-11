package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Invoice;
import cn.xmo.domain.cargo.InvoiceExample;
import com.github.pagehelper.PageInfo;
/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 8:50 2019/8/10
 * @Modified By:
 */
public interface InvoiceService {

    PageInfo findAll(int page, int size, InvoiceExample invoiceExample);

    void save(Invoice invoice);

    void update(Invoice invoice);

    void delete(String id);

    Invoice findById(String id);
}
