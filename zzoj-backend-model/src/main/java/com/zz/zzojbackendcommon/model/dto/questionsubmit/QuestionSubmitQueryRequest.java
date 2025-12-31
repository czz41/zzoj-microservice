package com.zz.zzojbackendcommon.model.dto.questionsubmit;


import com.zz.zzojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/lizz">程序员鱼皮</a>
 * @from <a href="https://zz.icu">编程导航知识星球</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
/**
 * 这个注解是Lombok 框架提供的，用于自动生成equals()和hashCode()方法的核心注解，解决了手动编写这两个方法的繁琐性（尤其是属性多的时候）。
 * 关键参数：callSuper = true
 * 默认值：callSuper = false（如果不写这个参数，默认不会考虑父类的属性 / 方法）。
 * 作用：当设置为true时，生成的equals()和hashCode()方法会包含父类的equals()和hashCode()的计算逻辑；如果为false，则只考虑当前类的属性，完全忽略父类。
 */
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {


    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 用户 id
     */
    private Long userId;
    private static final long serialVersionUID = 1L;
}