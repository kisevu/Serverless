package com.ameda.kevin.news_letter.model;

import lombok.*;

/**
 * Author: kev.Ameda
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Subscriber {
    private Integer id;
    private String email;
}
