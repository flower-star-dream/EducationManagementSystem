package com.example.educationmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatchDeleteRequest {
    private List<String> sids;
    private List<String> tids;
    private List<Integer> cids;
}
