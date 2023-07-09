package com.db.tradestore.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Trade {
    @Id
    private String tradeId;

    private int version;
    private String counterPartyID;

    private String bookId;

    private LocalDate maturityDate;

    private LocalDate createdDate;

    private char expired;

}
