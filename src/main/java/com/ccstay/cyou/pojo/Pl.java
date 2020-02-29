package com.ccstay.cyou.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 问题和标签的关系表
 */
@Data
@Entity
@Table(name="tb_pl")
public class Pl implements Serializable {
    @Id
    private String problemid;//问题ID
    @Id
    private String labelid;//标签ID
}
