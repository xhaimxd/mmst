package com.roger.mmst.constants.converter;

import com.roger.mmst.constants.job.JobType;
import jakarta.persistence.Converter;

/**
 * @author Roger Liu
 * @date 2024/03/08
 */
@Converter(autoApply = true)
public class JobTypeConverter extends CodeEnumConverter<JobType, Integer> {
    @Override
    Class<JobType> getEnumClass() {
        return JobType.class;
    }
}
