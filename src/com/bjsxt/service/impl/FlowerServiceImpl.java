package com.bjsxt.service.impl;

import com.bjsxt.mapper.FlowerMapper;
import com.bjsxt.mapper.ProductionMapper;
import com.bjsxt.pojo.Flower;
import com.bjsxt.pojo.Production;
import com.bjsxt.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("flowerService")
public class FlowerServiceImpl implements FlowerService {
    @Autowired
    private FlowerMapper flowerMapper;
    @Autowired
    private ProductionMapper productionMapper;

    public ProductionMapper getProductionMapper() {
        return productionMapper;
    }

    public void setProductionMapper(ProductionMapper productionMapper) {
        this.productionMapper = productionMapper;
    }

    public FlowerMapper getFlowerMapper() {
        return flowerMapper;
    }

    public void setFlowerMapper(FlowerMapper flowerMapper) {
        this.flowerMapper = flowerMapper;
    }

    @Override
    public List<Flower> findAll() {
        return flowerMapper.selectAll();
    }

    @Override
    public List<Production> findProduction() {
        return productionMapper.findAll();
    }

    @Override
    public void addFlower(Flower flower) {
        flowerMapper.add(flower);
    }

    @Override
    public Flower findFlowerById(int id) {
        return flowerMapper.findById(id);
    }

    @Override
    public List<Flower> selectByCon(String name, int productionId) {
        Map<String,Object>map=new HashMap<>();
        map.put("fname",name );
        map.put("pro", productionId);
        List<Flower> list = flowerMapper.selectByCon(map);
        return list;
    }
}
