package com.prive.ordering.service.Impl;

/**
 * @Author: Brooke Li
 * @E-mail: Halloworld1992@outlook.com
 * @Date: Created in $[TIME] $[DATE]
 * @Description:
 * @Modified by:
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.prive.ordering.mapper.PriveOrderMapper;
import com.prive.ordering.model.PriveOrder;
import com.prive.ordering.service.PriveOrderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriveOrderServiceImpl extends ServiceImpl<PriveOrderMapper, PriveOrder> implements PriveOrderService {

    @Override
    public List<PriveOrder> mygetList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type","limit");
        return baseMapper.selectByMap(map);
    }


}
