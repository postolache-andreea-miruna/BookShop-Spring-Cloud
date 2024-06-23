package com.awbd.bookshopspringcloud.services;

import com.awbd.bookshopspringcloud.models.SalesOff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "salesOff")
public interface SalesOffServiceProxy {
    @GetMapping("/salesOff")
   // SalesOff getingSalesOff();

    //for zipkin
    ResponseEntity<SalesOff> getSalesOff(@RequestHeader("awbd-id")
                                          String correlationId);
}
