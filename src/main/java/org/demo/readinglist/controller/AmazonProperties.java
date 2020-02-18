package org.demo.readinglist.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties("amazon")
public class AmazonProperties {
    private String associateId;
}


