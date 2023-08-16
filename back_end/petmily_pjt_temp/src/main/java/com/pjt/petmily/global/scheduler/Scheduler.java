package com.pjt.petmily.global.scheduler;

import com.pjt.petmily.domain.curation.controller.CurationController;
import com.pjt.petmily.domain.product.controller.ProductController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Scheduler {
    private final CurationController curationController;
    private final ProductController productController;

    public Scheduler(CurationController curationController, ProductController productController) {
        this.curationController = curationController;
        this.productController = productController;
    }

    @Scheduled(cron = "0 20 * * *")
    public void performDataCrawl() throws IOException, InterruptedException {
        curationController.DataCrawl();
        curationController.DataCrawl2();
        productController.ProductReset();
    }

    @Scheduled(cron = "1 20 * * *")
    public void performDataCrawl2() throws IOException, InterruptedException {
        curationController.DataCrawl();
        curationController.DataCrawl2();
        productController.getProductData();
    }
}
