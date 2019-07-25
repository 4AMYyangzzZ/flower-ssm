package com.bjsxt.mapper;

import com.bjsxt.pojo.Flower;

import java.util.List;
import java.util.Map;

public interface FlowerMapper {
    public List<Flower> selectAll();

    public void add(Flower flower);

    public Flower findById(int id);

    List<Flower> selectByCon(Map<String, Object> map);
}
