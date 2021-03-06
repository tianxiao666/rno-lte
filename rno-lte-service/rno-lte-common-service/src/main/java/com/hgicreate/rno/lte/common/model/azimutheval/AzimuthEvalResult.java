package com.hgicreate.rno.lte.common.model.azimutheval;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "RNO_LTE_AZIMUTH_EVAL_RES")
@SequenceGenerator(name = "seq_Rno_Lte_Azimuth_Eval_Res", sequenceName = "seq_Rno_Lte_Azimuth_Eval_Res", allocationSize = 1)
public class AzimuthEvalResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Rno_Lte_Azimuth_Eval_Res")
    private Long id;
    private Long jobId;
    private String cellId;
    private Integer azimuth;
    private Integer azimuth1;
    private Integer azimuth2;
    private Integer diff1;
    private Integer diff2;
}
