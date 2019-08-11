package cn.xmo.service.cargo;

import cn.xmo.domain.cargo.Shipping;
import cn.xmo.domain.cargo.ShippingExample;
import com.github.pagehelper.PageInfo;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 15:56 2019/8/10
 * @Modified By:
 */
public interface ShippingService {

    PageInfo findAll(ShippingExample example, int page, int size );

    void save( Shipping shipping );

    void update( Shipping shipping );

    void delete( String id );

    Shipping findById( String id );

}
