package com.px.coinage.sys.req.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DictBatchUpdateVO {

    @ApiModelProperty(value = "id")
//    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id值错误")
    private Integer id;

    @ApiModelProperty(value = "排序")
    private Integer sorted;

    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String name;

    @ApiModelProperty(value = "字典值")
    @NotBlank(message = "字典值不能为空")
    private String val;

    @ApiModelProperty(value = "状态 1可用 2禁用")
    @NotNull(message = "状态不能为空")
    @Range(min = 1, max = 2, message = "状态值错误")
    private Integer status;

}
