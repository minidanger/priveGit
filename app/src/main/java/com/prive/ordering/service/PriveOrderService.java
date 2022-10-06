package com.prive.ordering.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.prive.ordering.model.PriveOrder;

import java.util.List;

/**
 * @Author: Brooke Li
 * @E-mail: Halloworld1992@outlook.com
 * @Date: Created in $[TIME] $[DATE]
 * @Description:
 * @Modified by:
 */
public interface PriveOrderService extends IService<PriveOrder> {
    List<PriveOrder> mygetList();
}
