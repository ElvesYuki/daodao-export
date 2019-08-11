package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Finance;
import cn.xmo.domain.cargo.FinanceExample;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 8:48 2019/8/11
 * @Modified By:
 */
public interface FinanceService {

    PageInfo findAll (int page, int size, FinanceExample financeExample );

    void save ( Finance finance );

    void update ( Finance finance );

    void delete ( String id );

    Finance findById ( String id );
}
