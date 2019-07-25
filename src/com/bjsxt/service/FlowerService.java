package com.bjsxt.service;

import com.bjsxt.pojo.Flower;
import com.bjsxt.pojo.Production;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FlowerService {
    public List<Flower> findAll();

    public List<Production> findProduction();

    public void addFlower(Flower flower);

    public Flower findFlowerById(int id);

    List<Flower> selectByCon(String name, int productionId);
}
