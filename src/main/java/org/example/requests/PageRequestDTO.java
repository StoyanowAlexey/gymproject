package org.example.requests;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
public class PageRequestDTO {
    private Integer pageNo = 0;
    private Integer pageSize = 10;
    private String  sortField = "name";
    private String  direction = "asc";
}
