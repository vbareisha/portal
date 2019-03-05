package com.bareisha.portal.core.dto;

import lombok.Data;

import java.util.Date;

/**
 * Описание обьявления для новостей
 */

@Data
public class NewsDto {

    private String author;
    private Date date;
    private String text;
    private String imageLink;

}
