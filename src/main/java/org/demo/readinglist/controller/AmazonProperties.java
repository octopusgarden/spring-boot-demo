package org.demo.readinglist.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:amazon.yaml")
@Data
public class AmazonProperties {
    @Value("${amazon.associateId}")
    private String associateId;
}


