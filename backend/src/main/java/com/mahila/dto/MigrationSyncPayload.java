package com.mahila.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MigrationSyncPayload {
    private String email;
    private String name;
    private Integer age;
    private List<PeriodDTO> periods;
    private List<SymptomDTO> symptoms;
    private List<ContactDTO> contacts;
}
