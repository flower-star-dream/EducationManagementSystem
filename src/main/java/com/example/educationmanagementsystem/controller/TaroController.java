package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.FacultyDto;
import com.example.educationmanagementsystem.dto.TaroDto;
import com.example.educationmanagementsystem.service.TaroService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taro")
public class TaroController {

    @Resource
    private TaroService taroService;

    @GetMapping("/search")
    public PlainResult<IPage<TaroDto>> getTaroInfo(@RequestParam(required = false) Integer pageNum,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) Integer taroId,
                                                      @RequestParam(required = false) String taroName,
                                                      @RequestParam(required = false) String facultyName){
        IPage<TaroDto> taro = taroService.getTaroInfo(pageNum, pageSize, taroId, taroName, facultyName);
        return PlainResult.success(taro);
    }

    @PostMapping
    public PlainResult<String> addTaro(@RequestBody TaroDto taroDto){
        taroService.addTaro(taroDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{id}")
    public PlainResult<String> updateTaro(@PathVariable Integer id, @RequestBody TaroDto taroDto){
        taroService.updateTaro(id, taroDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteTaro(@PathVariable Integer id){
        taroService.deleteTaro(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Integer> ids){
        taroService.batchDelete(ids);
        return PlainResult.success("success");
    }

    @GetMapping("/check")
    public PlainResult<Boolean> getTaroInfo(@RequestParam String facultyName){
        Boolean bool = taroService.getTaroCheck(facultyName);
        return PlainResult.success(bool);
    }
}
