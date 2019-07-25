package com.bjsxt.controller;

import com.bjsxt.pojo.Flower;
import com.bjsxt.service.FlowerService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Controller
public class FlowerController {
    @RequestMapping("/findAll.do")
    @ResponseBody
    public Object findAll(HttpServletRequest request){
        //调用业务层
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        FlowerService flowerSevice = wac.getBean("flowerService", FlowerService.class);
        return flowerSevice.findAll();
    }

    @RequestMapping("/findProduction.do")
    @ResponseBody
    public Object findProduction(HttpServletRequest request){
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        FlowerService flowerService = wac.getBean("flowerService", FlowerService.class);
        return flowerService.findProduction();
    }

    @RequestMapping("/addFlower.do")
    public String addFlower(Flower flower, MultipartFile image,HttpServletRequest request){
        //文件上传
        try {
            image.transferTo(new File("/Test/images",image.getOriginalFilename()));
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            FlowerService flowerService = wac.getBean("flowerService", FlowerService.class);
            flower.setFlowerImage(UUID.randomUUID()+image.getOriginalFilename().substring(image.getOriginalFilename().indexOf(".")));
            flower.setRealName(image.getOriginalFilename());
            flowerService.addFlower(flower);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/index.jsp";
    }

    @RequestMapping("/findImageById.do")
    public ResponseEntity<byte[]> findImageById(int id,HttpServletRequest request){


        try {
            //通过id找到对应的花卉，获得花卉图片的真实名称
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            FlowerService flowerService = wac.getBean("flowerService", FlowerService.class);
            Flower flower=flowerService.findFlowerById(id);
            //下载图片
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDispositionFormData("attachment", new String(flower.getRealName().getBytes("utf-8"),"iso-8859-1"));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File("d:/Test/images/"+flower.getRealName())),httpHeaders, HttpStatus.CREATED);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("selectByCon.do")
    @ResponseBody
    public Object selectByCon(@RequestBody Flower flower, HttpServletRequest request){
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        FlowerService flowerService = wac.getBean("flowerService", FlowerService.class);
        List<Flower> list=flowerService.selectByCon(flower.getName(),flower.getProductionId());
        return list;
    }
}
