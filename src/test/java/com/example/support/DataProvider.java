package com.example.support;

import com.example.model.dto.AddPostRq;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataProvider {
    public static AddPostRq.AddPostRqBuilder prepareAddPostRq() {
        return AddPostRq.builder()
            .description("desc 1")
            .name("name 1");
    }
}
