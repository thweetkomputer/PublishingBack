package com.publishing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("favorReaderPassage")
@NoArgsConstructor
@AllArgsConstructor
public class FavorReaderPassage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long readerId;

    private Long passageId;


}
