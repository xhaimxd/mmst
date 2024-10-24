package com.roger.mmst.obj.dto.character;

import com.roger.mmst.constants.job.Job;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCharacterDTO {
    @NotBlank(message = "角色名不能为空")
    @Size(max = 20, message = "角色名长度不能超过20")
    private String name;
    @NotNull(message = "职业不能为空")
    private Job job;
}
